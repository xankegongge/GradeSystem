package com.example.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;

import com.githang.statusbar.StatusBarCompat;

import com.example.administrator.gradesystem.R;
import com.example.data.utils.BitmapTools;

//ѡ�����ͼƬʱԤ��
public class ActivityReViewImg extends Activity {
	
	TouchImageView m_imgView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m_imgView = new TouchImageView(this);
		LayoutParams lp = m_imgView.getLayoutParams();
		if (lp == null) {
			lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		}
		m_imgView.setLayoutParams(lp);
		Intent intent = getIntent();
		setContentView(m_imgView);
		StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bluetips), true);
		if (intent != null ) {
			String imgPath = intent.getStringExtra("imgpath");
			if (imgPath != null) {

				Bitmap bm = BitmapTools.readImgByPath(imgPath, 640, 960);
				if (bm != null ) {
					m_imgView.setImageBitmap(bm); 
				}
			}
		}
		
	}
	
}
