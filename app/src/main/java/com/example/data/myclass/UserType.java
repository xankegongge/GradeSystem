package com.example.data.myclass;


public enum UserType {
	      Administrator(0), Judge(1),Student(2),Assistant(3);
	      private int type;

	      public static UserType getUserTypeByCode(int code) {
	          for (UserType type : UserType.values()) {
	              if (type.getType() == code) {
	                  return type;
	              }
	          }
	          return null;
	      }
	      private UserType(int type) {
	          this.type = type;
	      }
//	      public static   byte[] GetTypeBytes(EUserType status)
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
		public static String getUserTypeName(UserType userType) {
			// TODO Auto-generated method stub
		    
		       // ChannelBuffer body = new DynamicChannelBuffer(ByteOrder.LITTLE_ENDIAN, Consts.DYNAMIC_BUFFER_LEN);

		        String name="";
		        switch (userType)
		        {
		            case  Administrator:
		            name="管理员";
		            break;
					case Judge:
		                name="教师";
		                break;
					case  Student:
		                name="客户";
		                break;

		            default:
		                name="";
		                break;
		        }

		        return  name;
		    
		}
	  
}
