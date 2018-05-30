package com.boyaa.push.lib.service;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Iterator;

import android.os.SystemClock;
import android.util.Log;

import com.example.administrator.gradesystem.GradeApplication;
import com.example.data.myclass.GlobalData;

/**
 *
 * @author Administrator
 *
 */
public class NioClient {

	private final int STATE_OPEN=1;//socket打开
	private final int STATE_CLOSE=1<<1;//socket关闭
	private final int STATE_CONNECT_START=1<<2;//开始连接server
	private final int STATE_CONNECT_SUCCESS=1<<3;//连接成功
	private final int STATE_CONNECT_FAILED=1<<4;//连接失败
	private final int STATE_CONNECT_WAIT=1<<5;//等待连接
	private  boolean canReConnect=true;//能否重连;//如果是服务器主动踢走，则不能重连;
	private String IP= GlobalData.ServiceIP;
	private int PORT=GlobalData.iRemotePort;
	private int state=STATE_CONNECT_START;

	Selector selector;
	ByteBuffer readBuffer = ByteBuffer.allocate(8192);
	SocketChannel socketChannel;

	private Thread conn=null;
	private Thread reConncetThread =null;
    private final int  WatchTime=100;//10S监听是否Socket断开
	//private Context context;
	private ISocketResponse respListener;
	private ArrayList<Packet> requestQueen=new ArrayList<Packet>();
	private final Object lock=new Object();
	private final String TAG="NioClient";
	private GradeApplication app;
	private int connectCount=1;
    private volatile boolean conExit = false;
    private volatile boolean reConnectExit = false;
    private static   NioClient client=null;
    private long lastConnTime=0;
    /**
     * 发送Packet数据
     * @param in
     * @return
     */
	public boolean send(Packet in)
	{
		boolean result=false;
		try {
			synchronized (lock)
            {
                requestQueen.add(in);
            }
			this.selector.wakeup();
			result=true;
		} catch (Exception e) {
			e.printStackTrace();
			result=false;
		}

		return result;
	}

    /**
     * 发送字符串
     * @param msg，待发送的字符串
     * @return 是否发送成功
     */
	public boolean send (String msg){
		Packet in =new Packet();
		in.pack(msg);
		return send(in);
	}

    /**
     * 移除待发送缓存区中的数据流
     * @param reqId
     */
	public void removeData(int reqId)
	{
		Iterator<Packet> mIterator=requestQueen.iterator();
		while (mIterator.hasNext())
		{
			Packet packet=mIterator.next();
			if(packet.getId()==reqId)
			{
				mIterator.remove();
			}
		}
	}

    /**
     * 单例模式，只有一个客户端长连接;
     * @return
     */
	public  static NioClient getInstance(){
		if(client==null) {
			synchronized (NioClient.class) {
				client = new NioClient();
			}
		}
		return client;
	}
	private NioClient(){

	}

    /**
     * 初始化Socket环境，包含了回调函数;
     * @param context
     * @param respListener
     */
	public void initSocket(GradeApplication context,ISocketResponse respListener){//初始化参数列表;

        this.app=context;
        this.respListener=respListener;

	}

    /**
     * 是否还保持连接状态;
     * @return
     */
	public boolean isSocketConnected()
	{
		return ((state==STATE_CONNECT_SUCCESS)&&socketChannel.isConnected());
	}

    /**
     * 设置回调函数;
     * @param response
     */
	public void setSocketHandler(ISocketResponse response){
		this.respListener=response;
	}

    /**
     * 打开Socket，并连接服务器
     * @param host
     * @param port
     */
	public void open(String host,int port)
	{
		this.IP=host;
		this.PORT=port;
		connectServer();
	}


    /**
     * 连接服务器
     */
	public synchronized void connectServer()//连接服务器；
	{
		if(System.currentTimeMillis()-lastConnTime<2000)
		{
			return;
		}
		lastConnTime=System.currentTimeMillis();

		close();//每次连接的时候需要清除之前的连接内容;
		state=STATE_OPEN;
        conExit=false;
        reConnectExit=false;
		conn=new Thread(new Conn());
		conn.start();

	}

    /**
     * 清除连接内容
     */
	public synchronized void close()
	{
		try {
			if(state!=STATE_CLOSE)
			{
				try {
					if(null!=conn&&conn.isAlive())
					{
                        conExit=true;
						conn.interrupt();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					conn=null;
				}

				try {
					if(null!= reConncetThread && reConncetThread.isAlive())
					{
                        reConnectExit=true;
                       
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					reConncetThread =null;
				}
                if(socketChannel.socket()!=null){
                    socketChannel.socket().close();
                }
				state=STATE_CLOSE;
			}
			requestQueen.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    /**
     * 连接线程
     */
    private class Conn implements Runnable
	{
		public void run() {
			Log.v(TAG,"Conn :Start");

			try {
				state=STATE_CONNECT_START;

				selector=SelectorProvider.provider().openSelector();
				socketChannel = SocketChannel.open();
				socketChannel.configureBlocking(false);

				InetSocketAddress address=new InetSocketAddress(IP, PORT);
				socketChannel.connect(address);
				socketChannel.register(selector, SelectionKey.OP_CONNECT);

				while(state!=STATE_CLOSE&&!conExit)
				{
					Log.v(TAG,"Conn :-------");
					selector.select();
					Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
					while (selectedKeys.hasNext())
					{
						SelectionKey key =  selectedKeys.next();
						selectedKeys.remove();

						if (!key.isValid())
						{
							continue;
						}

						if (key.isConnectable())
						{
							finishConnection(key);
						}

						else  if (key.isReadable())
						{
							read(key);
						}
						else if (key.isWritable())
						{
							write(key);
						}
					}

					synchronized(lock)
					{
						if(requestQueen.size()>0)
						{
							SelectionKey  key=socketChannel.keyFor(selector);
							key.interestOps(SelectionKey.OP_WRITE);
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
               // conExit=true;
			}finally{
				if(null!=socketChannel){
					try {
						socketChannel.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(selector!=null){
					try {
						selector.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			Log.v(TAG,"Conn :End");
		}

        /**
         * 连接完成时调用。
         * @param key
         * @return
         * @throws IOException
         */
		private boolean finishConnection(SelectionKey key) throws IOException
		{
			boolean result=false;
			SocketChannel socketChannel = (SocketChannel) key.channel();
			result= socketChannel.finishConnect();//没有网络的时候也返回true

			if(result)
			{
                if(connectCount>1)//表示重连成功
                {

                    reConnectExit=true;//停止重连线程
                    if(reConncetThread!=null&&reConncetThread.isAlive())
                        reConncetThread.interrupt();
                    app.showMessage("重连服务器成功");
                }else
                {
                    app.showMessage("已连接服务器");
                }
                connectCount=1;
				key.interestOps(SelectionKey.OP_READ);
				state=STATE_CONNECT_SUCCESS;
			}
			Log.v(TAG,"finishConnection :"+result);
			return result;
		}

        /**
         * 读取通道里面的内容
         * @param key
         * @throws IOException
         */
		private void read(SelectionKey key) throws IOException
		{
			SocketChannel socketChannel = (SocketChannel) key.channel();
			readBuffer.clear();
			int numRead;
			numRead = socketChannel.read(readBuffer);
			if (numRead == -1)//这个地方表示与服务器断开连接
			{
				key.channel().close();
				key.cancel();
				close();//关闭socket;
				if(canReConnect) {
					reConnectExit = false;//启动重连线程，
					reConncetThread = new Thread(new ReconnectServerThread());//如果连接成功，则开始开启监听线程，监听是否还连接
					reConncetThread.start();
				}else {
					app.showMessage("与服务器断开连接");
				}

				return;
			}else {
				byte[] bresult = readBuffer.array();
				 if (numRead == 1)//如果服务器只是发送了一个字节
				{
					canReConnect = bresult[0] == 0x02 ? false : true;
				} else if (numRead >1) {
					 if(respListener!=null)
						 respListener.onSocketResponse(bresult);
				}
			}

		}

        /**
         * 往通道里面进行写入打包字节流;
         * @param key
         * @throws IOException
         */
		private void write(SelectionKey key) throws IOException
		{
			SocketChannel socketChannel = (SocketChannel) key.channel();
			synchronized (lock)
			{
				Packet item;
				Iterator<Packet> iter=requestQueen.iterator();
				while(iter.hasNext())
				{
					item=iter.next();
					ByteBuffer buf=ByteBuffer.wrap(item.getPacket());
					socketChannel.write(buf);
					iter.remove();
				}
				item=null;
			}
			key.interestOps(SelectionKey.OP_READ);
		}
	}
    /**
     * 重连接：断开连接时回调该方法
     */

    private void disConReConnect(){
        if(client.isSocketConnected())
            return;
        //随着连接次数增加，重连接时间间隔拉长
        if(connectCount <= 3){
            doConnect(3 * 1000);
        }else if(connectCount <= 4){
            doConnect(7 * 1000);
        }else {
            //连接次数超过5次，认为网络已经断开，主动放弃连接
          app.showMessage("哥哥尽力了，还是无法连接服务器，主动放弃");
            reConnectExit=true;
            if(reConncetThread!=null&&reConncetThread.isAlive())
                reConncetThread.interrupt();
            return;
        }
        app.showMessage(String.format("连接已断开,正在尝试第%d次连接",connectCount));
        connectCount ++;
    }

    private void doConnect(int connectPeriod){
        SystemClock.sleep(connectPeriod);
        if(client != null)
            client.connectServer();
    }

    /***
     * 重连服务器线程，当服务器主动断开时触发
     */
    private class ReconnectServerThread implements Runnable {

        @Override
        public void run() {
            while(!reConnectExit){
                try {

                  Thread.sleep(WatchTime);
                    if(!isSocketConnected())//如果没有连接成功的话。
                    {
                       // app.showMessage("连接已断开,正在重连");
						disConReConnect();//重连服务器
                    }
                } catch (Exception e) {
                    //reConnectExit=true;
                    e.printStackTrace();
                }
            }
        }
    }
}
