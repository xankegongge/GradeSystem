package com.example.administrator.gradesystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.gradesystem.GradeApplication;
import com.example.data.myclass.GlobalData;


public class HelpActivity extends BaseSwipeActivity implements OnClickListener ,GradeApplication.AdviceListener{
	
	private FrameLayout BackLayout;
	private RelativeLayout HelpLayout;
	private TextView tv_title;
	private Button btnSubmit;
	private EditText edtAdvices;
	private GradeApplication app;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_myinfo);
		
		app = GradeApplication.getInstance();
		initView();
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		app.addActivity(this);
		app.addAdviceListener(this);
	}
	private void initView() {
		// TODO Auto-generated method stub

		setMyTitle("帮助与反馈");
		HelpLayout=(RelativeLayout)findViewById(R.id.helpinfo);
		HelpLayout.setOnClickListener(this);
		btnSubmit=(Button)findViewById(R.id.btn_feedbacksumbit);
		btnSubmit.setOnClickListener(this);
		edtAdvices=(EditText)findViewById(R.id.edt_feedback);
		
	}
	InputMethodManager manager ;
	private ProgressDialog pd;
	private Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			handler.sendEmptyMessage(GlobalData.CANCELDPD);
		}
	};
	private Handler handler = new Handler() {  
		
		@Override  
        public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法  
        	
        	switch (msg.what) {
			case GlobalData.CANCELDPD://超时
				app.setTimeOut(true);
				if(pd!=null&&pd.isShowing()){
					pd.dismiss();
					app.showMessage("提交超时，可能提交失败!");
					handler.removeCallbacks(runnable);
				}
				
				break;
			case GlobalData.CANCELOK://正常取消
				if(msg.arg1==0x05){//提交失败
					app.showMessage("提交失败!您再试一次！");
				}else if(msg.arg1==0x00){
					 app.showMessage("提交成功，谢谢您的反馈与支持!");
					 edtAdvices.setText("");
				}
				if(pd!=null&&pd.isShowing()){
					pd.dismiss();	
					handler.removeCallbacks(runnable);	
				}
				
				break;
			default:
				break;
			}
        	
        }  
    };  ;  
	
	@Override  
	 public boolean onTouchEvent(MotionEvent event) {  
	  // TODO Auto-generated method stub  
	  if(event.getAction() == MotionEvent.ACTION_DOWN){  
	     if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){  
	       manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
	     }  
	  }  
	  return super.onTouchEvent(event);  
	 }  
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {

		case R.id.helpinfo:
//			Intent intent=new Intent(HelpActivity.this,HelpTabActivity.class);
//			startActivity(intent);
			break;
		case R.id.btn_feedbacksumbit:
//			if(!app.getisConnected()){
//        		app.showMessage("您已掉线!");
//        		return;
//        	}
			String adviseString=edtAdvices.getText().toString();
//			if(TextUtils.isEmpty(adviseString)){
//				app.showMessage("意见不能为空");
//				return;
//			}else{
//
//				byte[] userBytes = null;
//	            try {
//	                    	//传递readingPictureID;
//	                        userBytes = app.getMyUserInfo().getUserID().getBytes("UTF8");
//	             } catch (Exception ee) {
//
//	             }
//
//            app.getEngine().sendMessage(null,InformationTypes.Advice, userBytes,adviseString,1024);
//            pd = ProgressDialog.show(this, null, "正在提交中，请稍后……");
//            handler.postDelayed(runnable, GlobalData.SubMitTime);
//		}
         break;
		default:
			break;
		}
	}
	@Override
	public void adviceResult(boolean isok) {
		// TODO Auto-generated method stub
		Message msg=new Message();
		msg.what=GlobalData.CANCELOK;
		if(isok){
			
			msg.arg1=0x00;//表示提交ok;
			
		}else {
			msg.arg1=0x05;//表示提交失败;
		}
		handler.sendMessage(msg);
	}
}
