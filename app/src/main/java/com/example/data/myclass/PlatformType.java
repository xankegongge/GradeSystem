package com.example.data.myclass;


public enum PlatformType {
	   PC(0),WebQQ(1),IPhone(2),Android(3);
	      private int type;

	      public static PlatformType getUserTypeByCode(int code) {
	          for (PlatformType type : PlatformType.values()) {
	              if (type.getType() == code) {
	                  return type;
	              }
	          }
	          return null;
	      }
	      private PlatformType(int type) {
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
