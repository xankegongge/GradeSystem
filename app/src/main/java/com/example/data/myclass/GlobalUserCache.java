package com.example.data.myclass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalUserCache implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int getRestPasswdCount() {
		return RestPasswdCount;
	}
	public void setRestPasswdCount(int restPasswdCount) {
		RestPasswdCount = restPasswdCount;
	}
	private   int RestPasswdCount=3;//每天只有三次的重置机会;
	private SystemSettings ss;
	public SystemSettings getSystemSettings(){
		return this.ss;
	}
	public void setSystemSettings(SystemSettings s){
		ss=s;
	}

	public GlobalUserCache() {
		if(this.ss==null){
			ss=new SystemSettings();
		}
	}
	
}
