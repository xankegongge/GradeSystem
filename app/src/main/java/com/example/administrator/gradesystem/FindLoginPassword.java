package com.example.administrator.gradesystem;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;

import com.example.data.utils.NetworkAvailableUtils;
import com.example.data.utils.ValidUtils;

/**
 * @author Administrator
 *
 */

public class FindLoginPassword extends BaseSwipeActivity{
	private TextView confirm;
	private EditText editTextEmail;
	private FrameLayout backLayout;
	private TextView tv_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
		  setContentView(R.layout.activity_findpassword);
		StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bluetips), true);
		setMyTitle("找回密码");
		  confirm=(TextView)findViewById(R.id.btn_findpasswd_confirm);
		  editTextEmail=(EditText)findViewById(R.id.et_findpasswd_email);
		  confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!NetworkAvailableUtils.isNetworkAvailable(FindLoginPassword.this)){
					GradeApplication.getInstance().showMessage("无网络");
					return ;
				}
				final String emailString=editTextEmail.getText().toString();
				if(emailString.equals("")){
					GradeApplication.getInstance().showMessage("不能为空");
					return ;
				}
				if(!ValidUtils.isValidEmailAddress(emailString)){
					GradeApplication.getInstance().showMessage("格式不正确");
					return ;
				}
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
//						ClientRPC clientRPC=new ClientRPC();
//						if(clientRPC.ResetPassword(emailString))
						{
							//confirm.setEnabled(false);
							GradeApplication.getInstance().showMessage("密码重置成功，请去邮箱查看新密码登录！");
							FindLoginPassword.this.finish();
							
						}
//						else
							{
							GradeApplication.getInstance().showMessage("服务器无反应或邮箱不存在");
								return ;
						}
					}
				}).start();
				
				
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}


	

}
