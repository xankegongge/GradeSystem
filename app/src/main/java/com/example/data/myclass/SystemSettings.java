package com.example.data.myclass;

import java.io.Serializable;

public class SystemSettings implements Serializable {
	   public static String SystemSettingsDir =  "\\GradeSystemdat\\" ;
       private static String SystemSettingsFilePath = SystemSettingsDir + "Settings.dat";
       private static SystemSettings singleton;
       
       public SystemSettings() { }
//       public static SystemSettings getInstance()
//       {
//           
//               if (SystemSettings.singleton == null)
//               {
//                   SystemSettings.singleton = SystemSettings.load();
//                   if (SystemSettings.singleton == null)
//                   {
//                       SystemSettings.singleton = new SystemSettings();
//                   }
//               }
//
//               return SystemSettings.singleton;
//                   
//       }
       public static void userChanged()
       {
           SystemSettings.singleton = new SystemSettings();
       }
       private String lastLoginUserID = "";
       private String lastLoginPwdMD5 = "";
       private boolean rememberPwd = false;
      
       private static SystemSettings load(){
    	   return null;
       }
   
    private boolean isVoice=true;
    private boolean isVibrate=true;
	public  void setVoice(boolean arg1) {
		// TODO Auto-generated method stub
		isVoice=arg1;
	}
	public boolean getVoice(){
		return isVoice;
	}
	public  void setVibrate(boolean arg1) {
		// TODO Auto-generated method stub
		isVibrate=arg1;
	}
	public boolean getVibrate(){
		return isVibrate;
	}
}
