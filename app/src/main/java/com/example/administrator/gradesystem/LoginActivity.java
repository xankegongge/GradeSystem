package com.example.administrator.gradesystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.data.myclass.InformationTypes;
import com.example.data.myclass.LogonResult;
import com.example.data.myclass.RespLogon;
import com.example.data.utils.SerializeHelper;
import com.oraycn.es.communicate.common.ClientType;
import com.oraycn.es.communicate.utils.BufferUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.boyaa.push.lib.service.ISocketResponse;
import com.boyaa.push.lib.service.NioClient;
import com.boyaa.push.lib.service.Packet;
import com.example.view.utils.ClearEditText;
import com.example.data.utils.NetworkAvailableUtils;
import com.example.view.utils.PasswordEditText;
import com.example.data.utils.StringHelper;
import com.example.administrator.gradesystem.R;


import com.example.data.myclass.GlobalData;

import io.netty.buffer.ByteBuf;


public class LoginActivity extends AppCompatActivity {


    TextView textFetchPassWord = null, textRegister = null;
    Button loginButton = null;
    ImageView currentUserImage = null;
    ClearEditText loginName = null;
	static PasswordEditText passwordEdit = null;


	private GradeApplication app;
    private NioClient client;


    private void displayToast(String s) {
		// TODO Auto-generated method stub
		 Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
        loginButton.setFocusable(true);
        loginName.setText("100001");
        passwordEdit.setText("1");

          app= GradeApplication.getInstance();//
          client=app.getClient();
          client.setSocketHandler(socketListener);
//          try {
//			DisplayMetrics metric = new DisplayMetrics();
//			    getWindowManager().getDefaultDisplay().getRealMetrics(metric);
//			    int width = metric.widthPixels; // 宽度（PX）
//			    int height = metric.heightPixels; // 高度（PX）
////			    app.setWidth(width);
////			    app.setHeight(height);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        loginName.setOnFocusChangeListener(new FocusChangeListener());
        passwordEdit.setOnFocusChangeListener(new FocusChangeListener());
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
            	if(!NetworkAvailableUtils.isNetworkAvailable(LoginActivity.this)){
            		app.showMessage("无网络连接!");
            		return ;
            	}
            	String userName=loginName.getText().toString();
                String passwd=StringHelper.md5(passwordEdit.getText().toString());

                if(client==null||!client.isSocketConnected()){
                    app.showMessage("亲，服务器无法访问");
                    return ;
                }

            	if(loginName.getText().toString().equals("")){
            		//app.showMessage();
                    loginName.setError("用户名不能为空");
            		return;
            	}
                if(passwordEdit.getText().toString().equals("")){
                //app.showMessage();
                    passwordEdit.setError("用户名不能为空");

                return;
            }
                ClientType clientType=ClientType.ANDROID;//
                Packet data=new Packet();
                int len=0;
                ByteBuf body=BufferUtils.newBuffer();
                body.writeInt(len);
                len+=4;

                body.writeInt(InformationTypes.LOGIN);//写入Infomationtypes;
                len+=4;

                len+=4;
                if(userName==null){
                    body.writeInt(-1);
                }else{

                    try {
                        byte[] pro= userName.getBytes("UTF-8");
                        body.writeInt(pro.length);
                        body.writeBytes(pro);
                        len+=pro.length;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

                len+=4;
                if(passwd==null){
                    body.writeInt(-1);
                }else{

                    try {
                        byte[] pro = passwd.getBytes("UTF-8");
                        body.writeInt(pro.length);
                        body.writeBytes(pro);
                        len+=pro.length;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

                body.writeInt(clientType.getType());//这个是m_UserFrom
                len+=4;

                body.resetWriterIndex();//将指针移至顶端
                body.writeInt(len);//数据总长度;

                byte[] des=new byte[len];
                System.arraycopy(body.array(), 0, des, 0, len);


                data.pack(des);
                client.send(data);//发送测试
//                client.close();//主动关闭连接
               loginButton.setText("正在登陆...");
               loginButton.setEnabled(false);//不能再点击
            }
        });


        app.addActivity(this);
    }

    private void initView() {
        textFetchPassWord = (TextView) findViewById(R.id.fetchPassword);
        textFetchPassWord.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(LoginActivity.this,FindLoginPassword.class);
                startActivity(intent);
            }
        });
        textRegister = (TextView) findViewById(R.id.registQQ);
        textRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

//				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
//				startActivityForResult(intent, 1);
			}
		});
        loginButton = (Button) findViewById(R.id.qqLoginButton);
        currentUserImage = (ImageView) findViewById(R.id.myImage);
        loginName = (ClearEditText) findViewById(R.id.qqNum);
        passwordEdit = (PasswordEditText) findViewById(R.id.qqPassword);
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
    public class  FocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean b) {
            EditText et=(EditText)view;
            if (et.hasFocus()==false){
                if("".equals(et.getText().toString())){
                    et.setError("不能为空");
                }
            }else{
                et.setError(null, null);//焦点聚焦时去除错误图标
            }
        }
    }
    private ISocketResponse socketListener=new ISocketResponse() {

        @Override
        public void onSocketResponse(final byte []txt) {
            runOnUiThread(new Runnable() {
                public void run() {
                    RespLogon res=new RespLogon();
                    try {
                        res.deserialize(SerializeHelper.wrappedBuffer(txt));
                        if(res.get_LoginResult()!= LogonResult.Succeed){
                            app.showMessage(res.get_Failure());
                            return;
                        }
                        loginButton.setEnabled(true);//
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1://说明是从注册界面回传过来
			if(data==null){
				return ;
			}
			else {
				loginName.setText(data.getCharSequenceExtra("userid"));
				passwordEdit.setText(data.getCharSequenceExtra("passwd"));
				displayToast("注册成功，等待客服审核!审核结果会发送至您的邮箱！"); 
				
			}
			
			break;

		default:
			break;
		}
	}







}
