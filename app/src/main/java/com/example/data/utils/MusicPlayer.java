package com.example.data.utils;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;

import java.util.Map;
import java.util.TreeMap;

import com.example.administrator.gradesystem.R;
import com.example.data.myclass.GlobalData;

public class MusicPlayer {
	private Context mContext ;
	private static MusicPlayer sInstance ;
	Vibrator vibrator;
	private SoundPool mSp  ;
	private Map<Integer ,Integer> sSpMap ;
	private MusicPlayer(Context context){
		mContext = context ;
		vibrator=(Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE);
		sSpMap = new TreeMap<Integer ,Integer>() ;
		mSp = new SoundPool(10 ,AudioManager.STREAM_MUSIC ,100) ;
		sSpMap.put(GlobalData.CHATMSG, mSp.load(mContext, R.raw.chatmsg, 1)) ;
		sSpMap.put(GlobalData.READINGMSG, mSp.load(mContext, R.raw.readingmsg, 0)) ;
	}
	
	public static MusicPlayer getInstance(Context context){
		if(sInstance == null)
			sInstance = new MusicPlayer(context) ;
		return sInstance ;
	}
	/**
	 * 震动功能
	 * @param activit
	 * @param milliseconds 震动时长 单位为毫秒
	 */
	public  void vibrate(long milliseconds){
		
//		long [] pattern={100,400,100,400};//停止 开启  停止 开启
//		vibrator.vibrate(pattern, 2);//重复两次上面的panttern，如果只是震动一次，index的值设定为-1
		vibrator.vibrate(milliseconds);
	}
	public void play(int type){
		if(sSpMap.get(type) == null) return ;
		mSp.play(sSpMap.get(type), 1, 1, 0, 0, 1) ;
	}
}
