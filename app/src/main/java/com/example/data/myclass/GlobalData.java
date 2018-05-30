package com.example.data.myclass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.administrator.gradesystem.GradeApplication;
import com.example.administrator.gradesystem.R;
import com.example.data.utils.BitmapTools;

public class GlobalData {
public static final int RCPPort = 4532;
public static final int LOAD_MEDICALREADING = 0;
public static final int SACNIMG_REQCODE_EDITED = 1;
public static final int SACNIMG_REQCODE_CAMERA = 2;
public static final int SACNIMG_REQCODE_LOCAL = 3;
public static final int SubmitOK = 0x22;
public static final int ReceivedOK = 0x23;
public static final int RejectedOK = 0x24;
public static final int UpdateOK = 0;
public static final int CANCELDPD = 0x44;//取消ProgressDialog。超时了
public static final int CANCELOK = 0x33;
public static final int CHATMSG = 0x66;//聊天消息
public static final int READINGMSG=0x67;//阅片消息
public static final int HOSPITALCOUNT = 12;

public static final String HELP_LOGINURL = "http://ow365.cn/?i=11640&furl=http://139.224.26.131/login.doc";
public static final String HELP_PASSWD = "http://ow365.cn/?i=11640&furl=http://139.224.26.131/passwd.doc";
public static final String HELP_CHAT = "http://ow365.cn/?i=11640&furl=http://139.224.26.131/chat.doc";
public static final String HELP_CLIENT = "http://ow365.cn/?i=11640&furl=http://139.224.26.131/client.doc";
public static final String HELP_EXPERT = "http://ow365.cn/?i=11640&furl=http://139.224.26.131/expert.doc";
	public static final int DefaultImageHeaderCount = 10;
	public static final int HeaderImageWidth = 20;
	public static final int HeadImageHeight = 20;


	//public static String ServiceIP="10.227.251.209";
//  public static String ServiceIP="xanke.nat123.net";//局域网
//	public static String ServiceIP="125.89.69.237";//学校服务器
//	public static String ServiceIP="139.224.26.131";//阿里云服务器
//	public static String ServiceIP="127.0.0.1";//本地服务器测试
	public static String ServiceIP="192.168.1.101";//局域网
	public static short iRemotePort=6652;//TCP服务器的端口
	// public static  int iRemotePort=4530;//服务器端口
//	public static  int iRemotePort = 12472;
	public static int LOAD_CHECKUSER=0x76;
	public static long SubMitTime=70000;
	public static int currPage=1;//当前页

	public static int pageSize=10;//10条阅片大小;

	public static int[] getHeadImages() {
		return HeadImages;
	}


	private static  int[] HeadImages=new int[10];


	public static void setHeadImages(int[] headImages) {
		HeadImages = headImages;
	}

	public  static Bitmap getHeaderBitmap(LoginUser loginUser){
		return GlobalData.GetHeadImageByStatus(loginUser);
	}
	public static Bitmap GetHeadImageOnline(LoginUser loginUser)
	{
		if (loginUser.getHeadImageIndex() >= 0)
		{
			if (loginUser.getHeadImageIndex() < HeadImages.length)
			{
				int resID=HeadImages[loginUser.getHeadImageIndex()];
				return BitmapFactory.decodeResource(mContex.getResources(),resID);
			}

			return BitmapFactory.decodeResource(mContex.getResources(),HeadImages[0]);
		}
		else
		{
			return BitmapTools.readImgByByte(loginUser.getHeadImageData(), HeaderImageWidth, HeadImageHeight);
		}
	}

	/***
	 * 离线获取灰色头像，在线获取有色头像
	 * @param loginUser
	 * @param
     * @return
     */
	private static Bitmap GetHeadImageByStatus(LoginUser loginUser) {
		if(loginUser ==null){
			return BitmapFactory.decodeResource(mContex.getResources(), R.drawable.grade);
		}
		if (loginUser.getHeadImageIndex() >= 0) {
			if (loginUser.getHeadImageIndex() < HeadImages.length)
			{
				int resID = HeadImages[loginUser.getHeadImageIndex()];
				//if(user.getOnlineOrHide())
				{
					return BitmapFactory.decodeResource(mContex.getResources(), resID);
				}

			}
		}
		else {
			//if(user.getOnlineOrHide())
			{
				return BitmapTools.readImgByByte(loginUser.getHeadImageData());//原始高度宽度：
			}
		}
		return  null;
	}
private  static Context mContex=GradeApplication.getInstance().getApplicationContext();
	public static void preInitialize(){//进行预加载资源;默认头像，表情等;
		int idx=0;
		while(idx< DefaultImageHeaderCount) {
			String imgname = "i" + idx;
			int imgid = mContex.getResources().getIdentifier(imgname, "drawable", "gg.example.android_qqfix");
			HeadImages[idx++]=imgid;
		}

	}
}
