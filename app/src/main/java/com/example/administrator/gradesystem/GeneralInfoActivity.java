package com.example.administrator.gradesystem;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import com.example.administrator.gradesystem.GradeApplication;
import com.example.data.utils.BitmapTools;
import com.example.data.utils.FileUtils;

public class GeneralInfoActivity extends BaseSwipeActivity implements OnClickListener {
	private FrameLayout BackLayout;
	private RelativeLayout clearLayout;
	
	private CheckBox cbVoice;
	private CheckBox cbVibrate;
	
	private Button btnSubmit;
	private EditText edtAdvices;
	private GradeApplication app;
	private TextView tv_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.generalinfo);
		
		app = GradeApplication.getInstance();
		 initView();
		 app.addActivity(this);
		 
	}
	private void initView() {
		// TODO Auto-generated method stub


		setTitle("通用");
		clearLayout=(RelativeLayout)findViewById(R.id.rl_clearchatinfo);
		clearLayout.setOnClickListener(this);
		
		cbVoice=(CheckBox)findViewById(R.id.cbvoice);
		cbVibrate=(CheckBox)findViewById(R.id.cbvibarte);
			cbVibrate.setChecked(app.getGlobalUserCache().getSystemSettings().getVibrate());
			cbVoice.setChecked(app.getGlobalUserCache().getSystemSettings().getVoice());
			cbVibrate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					app.getGlobalUserCache().getSystemSettings().setVibrate(arg1);
				}
			});
			cbVoice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					//if(arg1){//如果选中声音；
						app.getGlobalUserCache().getSystemSettings().setVoice(arg1);
					
				}
			});
	}
	String remotereadingPath = Environment.getExternalStorageDirectory().getPath() +
			"/"+BitmapTools.APP_FILE+"/" ;//图片路径
	private ProgressDialog pd;
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {

		case R.id.rl_clearchatinfo:
			pd = ProgressDialog.show(GeneralInfoActivity.this,null,"正在清除，请稍后...");
			try {
				FileUtils.DeleteFile(new File(remotereadingPath));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				app.showMessage("清除失败："+e.getMessage());
			}
			if(pd!=null&&pd.isShowing()){
				pd.dismiss();
			}
			app.showMessage("清除成功");
			break;
		default:
			break;
		}
	}
}
