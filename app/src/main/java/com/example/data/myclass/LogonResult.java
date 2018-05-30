package com.example.data.myclass;


import java.nio.ByteOrder;

public enum LogonResult {
	Succeed(0),Failed(1),HadLoggedOn(2),VersionMismatched(3);
     private int type;

     public static LogonResult getLogonResultByCode(int code) {
         for (LogonResult type : LogonResult.values()) {
             if (type.getType() == code) {
                 return type;
             }
         }
         return null;
     }
     private LogonResult(int type) {
         this.type = type;
     }
//     public static   byte[] GetTypeBytes(LogonResult status)
//     {
//         ChannelBuffer body = new DynamicChannelBuffer(ByteOrder.LITTLE_ENDIAN, Consts.DYNAMIC_BUFFER_LEN);
//
//         body.writeInt(status.getType());
//
//         return  body.array();
//     }
    
     
     public int getType() {
         return type;
     }
}
