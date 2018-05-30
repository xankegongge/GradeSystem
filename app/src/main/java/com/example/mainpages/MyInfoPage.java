package com.example.mainpages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.administrator.gradesystem.ExitFromSettings;
import com.example.administrator.gradesystem.HelpActivity;
import com.example.administrator.gradesystem.R;


public class MyInfoPage extends Fragment implements OnClickListener {
	
	
	private View rootView;
	private Button btnExit;
	RelativeLayout PersonInfoLayout;
	RelativeLayout GeneralInfoLayout;

	RelativeLayout HelpLayout;
	RelativeLayout AboutInfoLayout;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("FragmentPage1===>>>",  "onCreateView");
		if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.main_tab_settings, null);
           
        }

	return rootView;
		
	}
	private void exitApplication() {
	
	Intent intent=new Intent(getActivity(),ExitFromSettings.class);
	startActivity(intent);
	
}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initView(view);
	}
	
	private void initView(View view) {
		// TODO Auto-generated method stub
		btnExit =(Button)view.findViewById(R.id.btn_exit_application);
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				exitApplication();
			}
		});
		PersonInfoLayout=(RelativeLayout)view.findViewById(R.id.personinfo);
		PersonInfoLayout.setOnClickListener(this);
		
		GeneralInfoLayout=(RelativeLayout)view.findViewById(R.id.generalinfo);
		GeneralInfoLayout.setOnClickListener(this);
		
		
		
		
		HelpLayout=(RelativeLayout)view.findViewById(R.id.help);
		HelpLayout.setOnClickListener(this);
		
		AboutInfoLayout=(RelativeLayout)view.findViewById(R.id.aboutinfo);
		AboutInfoLayout.setOnClickListener(this);
		
		
		
	}
	
	public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personinfo:
              //  getActivity().getApplication().(this, "点击事件的实现", Toast.LENGTH_SHORT).show();
         //      Toast.makeText(getActivity(), "点击事件的实现", Toast.LENGTH_SHORT).show();
            //	Intent intent=new Intent(getActivity(),MyInfoActivity.class);
			//	getActivity().startActivity(intent);
            	break;
            case R.id.generalinfo:
//            	Intent geintent=new Intent(getActivity(),GeneralInfoActivity.class);
//				getActivity().startActivity(geintent);
            	break;
            
            case R.id.help:
            	Intent helIntent=new Intent(getActivity(),HelpActivity.class);
				getActivity().startActivity(helIntent);
				//overridePendingTransition(R.anim.left_in,R.anim.left_out);
            	break;
            case R.id.aboutinfo:
//            	Intent aboutIntent=new Intent(getActivity(),AboutInfoActivity.class);
//				getActivity().startActivity(aboutIntent);
            	break;
            
        }
    }
}
