package com.example.administrator.gradesystem;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


import com.boyaa.push.lib.service.NioClient;
import com.example.data.myclass.UserType;
import com.example.data.myclass.LoginUser;
import com.example.data.myclass.GlobalData;
import com.example.data.myclass.GlobalUserCache;
import com.example.data.utils.BitmapTools;
import com.example.data.utils.FileUtils;
import com.example.data.utils.MusicPlayer;
import com.example.data.utils.NetworkAvailableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZN on 2015/9/1.
 */
public class GradeApplication extends Application {

    private static GradeApplication application=null;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private ArrayList<Activity> activityList=new ArrayList<Activity>();
    private ActivityManager activityManager;
    private NetBroadcastReceiver receiver;
    private boolean isNetDisCon =false;

    public  static GradeApplication getInstance(){
        if(application==null){
            synchronized(GradeApplication.class)
            {
                application=new GradeApplication();
            }
        }

        return application;
    }
    private Handler handler = new Handler();
    public void showMessage(String message) {
        handler.post(new UIRunnable(message));
    }
    private class UIRunnable implements Runnable {

        private String text;

        public UIRunnable(String info) {
            this.text = info;
        }

        public void run() {
            Toast.makeText(GradeApplication.this, text, Toast.LENGTH_LONG)
                    .show();
        }
    }
    /** 初始化通知栏 */
    private void initNotify(){
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
//				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消

                //.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                //	.setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.grade);

    }
    private GlobalUserCache globalUserCache =new GlobalUserCache();
    public GlobalUserCache getGlobalUserCache(){

        return this.globalUserCache;
    }
    MusicPlayer mPlayer;
    //客户端连接
    public NioClient getClient() {
        return client;
    }

    NioClient client=null;
    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        // Fresco.initialize(this);
        // loadContactMessageList();
        initNotify();//初始化通知栏;
        mPlayer= MusicPlayer.getInstance(getApplicationContext());
        client=NioClient.getInstance();
        client.initSocket(application,null);//null表示回到接口
        client.open(GlobalData.ServiceIP,GlobalData.iRemotePort);

        IntentFilter filter=new IntentFilter();//注册网络变化广播
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        receiver=new NetBroadcastReceiver();
        registerReceiver(receiver, filter);
    }




    //添加Activity到容器中
    public void addActivity(Activity activity)
    {
        if(!activityList.contains(activity))
            activityList.add(activity);
    }
    public class NetBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if(!NetworkAvailableUtils.isNetworkAvailable(GradeApplication.this)){
                showMessage("无网络连接,请您检查您的网络！");
                isNetDisCon =true;
                client.close();//断开连接;
                return;//结束线程;
            }else
            {
                if(isNetDisCon) {
                    isNetDisCon=false;
                    client.connectServer();//恢复网络重新连接
                }
            }
        }

    }
    //遍历所有Activity并finish
    public void exit()
    {
        if(client!=null)
            client.close();
        for(Activity activity:activityList)
        {
            activity.finish();
        }
        unregisterReceiver(receiver);
        System.exit(0);

    }
    private List<AdviceListener> listAdviceListeners=new ArrayList<AdviceListener>();
    public void addAdviceListener(AdviceListener lister){
        if(!listAdviceListeners.contains(lister)){
            listAdviceListeners.add(lister);
        }
    }
    public interface AdviceListener {
        void adviceResult(boolean isok);
        //informationtypes=ADD：表示添加新阅片，1：更新阅片状态，更新接收成功；2：更新阅片状态，更新拒绝理由；3：更新提交正在处理成功的相关信息（专家标签）
    }
    private boolean isTimeOut=false;
    public void setTimeOut(boolean b) {
        // TODO Auto-generated method stub
        isTimeOut=b;
    }
    public boolean getTimeOut(){
        return this.isTimeOut;
    }
    public void TimeoutDismiss(final Handler handler){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(15000);

                    handler.sendEmptyMessage(GlobalData.CANCELDPD);

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }//15秒钟后，如果没有取消pd,则取消

            }
        }).start();
    }
    private UserType usertype;
    public UserType getUsertype() {
        return usertype;
    }
    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }
    private LoginUser myLoginUserInfo;
    public LoginUser getMyLoginUserInfo() {
        return myLoginUserInfo;
    }

    public void setMyLoginUserInfo(LoginUser myLoginUserInfo) {
        this.myLoginUserInfo = myLoginUserInfo;
        this.usertype= this.myLoginUserInfo.getUserType();//实时获取用户类型;
    }
    public boolean isAppOnForeground() {
        List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {

            // 应用程序位于堆栈的顶层
            if (packageName.equals(tasksInfo.get(0).topActivity
                    .getPackageName())) {
                return true;
            }
        }
        return false;
    }
    public void setIsBackGroundRun(boolean b){
        this.isBackGroundRun=b;
    }
    // 释放设备电源锁
    public void releaseWakeLock() {
        if (null != wakeLock && wakeLock.isHeld()) {

            wakeLock.release();
            wakeLock = null;
        }
    }
    /**
     * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
     */
    public void acquireWakeLock() {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, getClass()
                    .getCanonicalName());
            if (null != wakeLock) {

                wakeLock.acquire();
            }
        }
    }
    private String packageName;
    int notifyId = 100;
    private boolean isBackGroundRun=false;//是否在后台运行;
    private PowerManager.WakeLock wakeLock;
    public void initWatchIsBackground(){
        activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        packageName = this.getPackageName();
        System.out.println("启动服务");
        notifyId=100;//初始化;
        new Thread() {
            public void run() {
                try {
                    //boolean isStop=false;
                    while (true) {
                        Thread.sleep(1000);

                        if (isAppOnForeground()) { //如果是前台的话
                            setIsBackGroundRun(false);
                            mNotificationManager.cancel(notifyId);
                            releaseWakeLock();
                        } else {
                            setIsBackGroundRun(true);
                            // showIntentActivityNotify(2,"你好","爱你","永不变");
                            // showNotification();//显示正在运行的通知
                            //showMessage("阅片系统已转入后台运行");
                            acquireWakeLock();
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    String FilePathDir = Environment.getExternalStorageDirectory().getPath() +"/"+ BitmapTools.APP_FILE+"/"+BitmapTools.APP_Cache+"/";//
    public void saveUserCache(){
        if(myLoginUserInfo !=null) {
            String filePathString = FilePathDir + getMyLoginUserInfo().getUserID();
            FileUtils.saveObjectToSD(filePathString, this.globalUserCache);
        }
    }


}