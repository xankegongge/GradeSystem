package com.example.administrator.gradesystem;


import com.boyaa.push.lib.service.ISocketResponse;
import com.boyaa.push.lib.service.NioClient;
import com.example.mainpages.*;
import com.example.popmenu.ActionItem;
import com.example.popmenu.TitlePopup;
import com.example.data.utils.BitmapTools;
import com.example.view.utils.CircleImage;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.example.view.utils.SelectPicPopupWindow;
import com.example.data.myclass.*;

import com.example.views.ActivityImgLoader;
import com.example.views.utils.BitmapUtils;
import qiu.niorgai.StatusBarCompat;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
 View.OnClickListener {


    private FragmentTabHost tabHost;
    private GradeApplication app;


    private TitlePopup titlePopup;


    //**底部分页标题
    protected static  String [] TAB_TITLE_STRING = {"打分","已录入","发现","我的"};

    //**底部标题对应每页的内容
    protected static Class[] TAB_PAGE_CLASS = { GradePage.class ,MessagePage.class ,FindPage.class,MyInfoPage.class};

    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    /** Notification的ID */
    int notifyId = 100;
    private ActivityManager activityManager;
    private String packageName;
    private SelectPicPopupWindow menuWindow;
    private SharedPreferences mySharedPreferences;
    private String urlpath;
    private TextView navRole;
    private UserType usertype;
    private NioClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bluetips));
        //StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bluetips));
        app = GradeApplication.getInstance();
        usertype=app.getUsertype();
       // engine=app.getEngine();
       // basicEngineListener();
        client=app.getClient();
        if(client==null||!client.isSocketConnected()){
            app.showMessage("无连接，小哥！");
        }
        client.setSocketHandler(socketListener);
        if (savedInstanceState == null) {
            restoreForFirstLauch();
        }
        initTabMenu();
       // FaceConversionUtil.getInstace().getFileText(MainActivity.this);//读取emtiom表情，加载至缓存中;
        UIUpdate();//根据身份来判断加载不同的UI

        app.addActivity(this);

//
       // app.AddFriendStatusChangedListener(this);
    }
    private ISocketResponse socketListener=new ISocketResponse() {

        @Override
        public void onSocketResponse(byte[] txt) {
            runOnUiThread(new Runnable() {
                public void run() {
                    //app.showMessage(txt);

                }
            });
        }
    };
    public void showPop(View v) {
        titlePopup.show(findViewById(R.id.iv_show_Pop));
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        app.initWatchIsBackground();//启动监听是否变成后台.如果变成后台，则会显示通知栏;
        //	app.acquireWakeLock();//申请wakelock锁
        UserHeadImage.setImageBitmap(GlobalData.getHeaderBitmap(app.getMyLoginUserInfo()));
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        app.releaseWakeLock();
    }

    /*
 *
 *
 */
    private void UIUpdate() {

        usertype=app.getUsertype();//获取当前用户身份；
        if(usertype==null){
            return ;
        }
        switch (usertype.getType()) {
            case 0://管理员
                //	 app.showMessage("尊敬的管理员，欢迎您！");

                break;
            case 1://客服
                app.showMessage("尊敬的客服，欢迎您！");
                break;
            case 2://普通用户
                app.showMessage("尊敬的用户，欢迎您！");
                break;
            case 3://专家
                app.showMessage("尊敬的专家，欢迎您！");
                break;

            default:

                break;
        }

    }
    private static final int REQUESTCODE_PICK = 0;
    private static final int REQUESTCODE_TAKE = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private CircleImage UserHeadImage;
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                //打开相机
                case R.id.btn_pop_selectimg_camera:
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                    break;
                //打开相册
                case R.id.btn_pop_selectimg_local:
                    Intent intent = new Intent(MainActivity.this,ActivityImgLoader.class);
                    //intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    MainActivity.this.startActivityForResult( intent, REQUESTCODE_PICK ) ;
                    break;
                default:
                    break;
            }
            menuWindow.dismiss();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:
                try {
                    //从相册选择照片后截取图像
                    String[] imgpath = (String[]) data.getCharSequenceArrayExtra("result");
                    File temp = new File(imgpath[0]);//选择的第一张图片进行裁剪;
                    startPhotoZoom(Uri.fromFile(temp));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            case REQUESTCODE_TAKE:
                //拍完照后截取图像
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    //截图成功，设置头像
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
        BitmapTools.HideLoading();//这句不能忘，要隐藏loading Dialog；
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView:
                menuWindow = new SelectPicPopupWindow(MainActivity.this, itemsOnClick);
//				menuWindow.getContentView().measure(0, 0);
//				int height = menuWindow.getContentView().getMeasuredHeight();
                menuWindow.showAtLocation(findViewById(R.id.drawer_layout),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //	app.releaseWakeLock();
    }


    private RadioGroup mTabRg;
    private TextView title;
    private RadioButton mTabRb;
    private TextView navName;
    protected int curindex;
    private TitlePopup.OnItemOnClickListener onitemClick = new TitlePopup.OnItemOnClickListener() {
        @Override
        public void onItemClick(ActionItem item, int position) {
            // mLoadingDialog.show();
            switch (position) {
                case 0:// 发起群聊
                    //Toast.makeText(MainActivity.this, "发起群聊", 1).show();
//                    Intent intent =new Intent(MainActivity.this,AddFriendActivity.class);
//                    startActivity(intent);
                    break;
//				case 1:// 添加朋友
//					Toast.makeText(MainActivity.this, " 添加朋友", 1).show();
//					break;
				case 2:// 扫一扫
					Toast.makeText(MainActivity.this, "扫一扫", Toast.LENGTH_LONG).show();
					break;
//				case 3:// 收钱
//					Toast.makeText(MainActivity.this, "收钱", 1).show();
//					break;
                default:
                    break;
            }
        }
    };
    //初始化底部Tab菜单
    @SuppressLint("NewApi")
    protected void initTabMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //设置自定义toolbar，不使用自带actionbar
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.addmenu);
        setTitle("");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_mainview);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        UserHeadImage = (CircleImage) headerView.findViewById(R.id.imageView);
        UserHeadImage.setOnClickListener(this);
        navName=(TextView)headerView.findViewById(R.id.nav_personname);

        navRole =(TextView)headerView.findViewById(R.id.nav_role);
        // 实例化标题栏弹窗
        titlePopup = new TitlePopup(this, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titlePopup.setItemOnClickListener(onitemClick);
        // 给标题栏弹窗添加子类
//		titlePopup.addAction(new ActionItem(this, R.string.groupchat,
//				R.drawable.icon_menu_group));
//        titlePopup.addAction(new ActionItem(this, R.string.addfriend,
//                R.drawable.icon_menu_addfriend));
		titlePopup.addAction(new ActionItem(this, R.string.qrcode,
				R.drawable.icon_menu_sao));
//		titlePopup.addAction(new ActionItem(this, R.string.money,
//				R.drawable.abv));

        tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabRb=(RadioButton)findViewById(R.id.tab_rb_3);
//        if(usertype.getType()==1){
//            mTabRb.setText("审核");
            //	Drawable drawable=this.getResources().getDrawable(R.drawable.tab_selector_check);
            //	drawable.setBounds(left, top, right, bottom)
            //	mTabRb.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
//        }
        tabHost.setup(this,getSupportFragmentManager()  ,R.id.contentLayout);  //装载内容容器
        tabHost.getTabWidget().setDividerDrawable(null);
//	        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
//				@Override
//				public void onTabChanged(String arg0) {
//					flushTabUnion();
//
//				}
//			});

        for (int i = 0; i < TAB_TITLE_STRING.length; i++) {
            //	View view = getTabUnionView(i);
            if (i < TAB_PAGE_CLASS.length ) {
                TabSpec tabSpec = tabHost.newTabSpec(i+"").setIndicator(i+"");//设置底部每个选项的View

//                if(i==2){//第三个选项根据身份会有所变化
//                    if(usertype.getType()==1){//如果是客服
//                      //  tabHost.addTab(tabSpec,CheckPage.class,null);
//                        continue;
//                    }
//                }
                tabHost.addTab(tabSpec,TAB_PAGE_CLASS[i],null);
            }
        }
        title=(TextView)findViewById(R.id.common_title);
        mTabRg = (RadioGroup) findViewById(R.id.tab_rg);

        mTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_rb_1:
                        curindex=0;//第0项被选中;
                        tabHost.setCurrentTab(0);
                        //title.setText(TAB_TITLE_STRING[0]+(app.getisConnected()==true?"":"(离线)"));
                        findViewById(R.id.iv_show_Pop).setVisibility(View.GONE);
                        break;
                    case R.id.tab_rb_2:
                        curindex=1;//第1项被选中;
                        tabHost.setCurrentTab(1);
                        findViewById(R.id.iv_show_Pop).setVisibility(View.VISIBLE);
                       // title.setText(TAB_TITLE_STRING[1]+(app.getisConnected()==true?"":"(离线)"));
                        break;
                    case R.id.tab_rb_3:
                        curindex=2;//第2项被选中;
                        tabHost.setCurrentTab(2);
//                        if(usertype.getType()==1){
//                            title.setText("审核");
//
//                        }else
                        //    title.setText(TAB_TITLE_STRING[2]+(app.getisConnected()==true?"":"(离线)"));
                        findViewById(R.id.iv_show_Pop).setVisibility(View.GONE);
                        break;
                    case R.id.tab_rb_4:
                        curindex=3;//第2项被选中;
                        tabHost.setCurrentTab(3);
//                        if(usertype.getType()==1){
//                            title.setText("审核");
//
//                        }else
                        //    title.setText(TAB_TITLE_STRING[2]+(app.getisConnected()==true?"":"(离线)"));
                        findViewById(R.id.iv_show_Pop).setVisibility(View.GONE);
                        break;

                    default:
                        break;
                }
            }
        });

        tabHost.setCurrentTab(0);


        UserHeadImage.setImageBitmap(GlobalData.getHeaderBitmap(app.getMyLoginUserInfo()));
        if(app.getMyLoginUserInfo()!=null) {
            navName.setText(app.getMyLoginUserInfo().getPersonName());
            //navRole.setText(app.getMyGradeUserInfo().getHospitalName());
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                if (!hasTask) {
                    tExit.schedule(task, 2000);
                }
            } else {
                app.saveUserCache();//
                app.exit();//退出系统;
            }
        }
        return false;
    }
    //实现 按两次 回退键退出
    private static Boolean isExit = false;
    private static Boolean hasTask = false;
    Timer tExit = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            isExit = false;
            hasTask = true;
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法

            if(msg.what==0x10076) {
                title.setText(TAB_TITLE_STRING[curindex]+"(离线)");
            }else if(msg.what==0x10078)
                title.setText(TAB_TITLE_STRING[curindex]);

        }
    };



    public void startPhotoZoom(Uri uri) {
        //截图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //设置宽高比
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片具体宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }


    protected void restoreForFirstLauch() {
        mySharedPreferences= getSharedPreferences("connectsoft", Activity.MODE_PRIVATE);
        urlpath=mySharedPreferences.getString("filepath", "");
    }
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            BitmapTools.checkMarkDir(BitmapTools.HeadImageDir);
            urlpath  = BitmapTools.HeadImageDir+app.getMyLoginUserInfo().getUserID()+".jpg";
            BitmapTools.saveImgByPath(photo, urlpath);
            UserHeadImage.setImageDrawable(drawable);
            mySharedPreferences= getSharedPreferences("connectsoft", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=mySharedPreferences.edit();
            editor.putString("filepath",urlpath);
            editor.commit();
            //更新本地缓存
            app.getMyLoginUserInfo().setHeadImageData(BitmapUtils.Bitmap2Bytes(photo));
            app.getMyLoginUserInfo().setHeadImageIndex(-1);
            LoginUser loginUser =app.getMyLoginUserInfo();
//            try {
//                byte[] info=user.serialize();
//                app.getEngine().sendMessage(null, InformationTypes.UpdateUserInfo, info,app.getMyUserInfo().getUserID(),1024);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Class fragmentClass;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_nav_help:
                Intent intenta=new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intenta);
                break;
            case R.id.menu_nav_setting:
                //fragmentClass = OffLineDataVisualFragment.class;
                Intent intent=new Intent(MainActivity.this, GeneralInfoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
