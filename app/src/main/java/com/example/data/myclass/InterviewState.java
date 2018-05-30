package com.example.data.myclass;


public enum InterviewState {
	   未面试(0),正在面试(1),已完成(2);
	      private int type;

	      public static InterviewState getUserTypeByCode(int code) {
	          for (InterviewState type : InterviewState.values()) {
	              if (type.getType() == code) {
	                  return type;
	              }
	          }
	          return null;
	      }
	      private InterviewState(int type) {
	          this.type = type;
	      }
//	      public static   byte[] GetTypeBytes(EPlatformType status)
//	      {
//	          ChannelBuffer body = new DynamicChannelBuffer(ByteOrder.LITTLE_ENDIAN, Consts.DYNAMIC_BUFFER_LEN);
//
//	          body.writeInt(status.getType());
//
//	          return  body.array();
//	      }
	     
	      
	      public int getType() {
	          return type;
	      }
}
