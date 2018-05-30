package com.example.data.myclass;

import android.media.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.data.utils.DateUtil;
import com.example.data.utils.SerializeHelper;
import com.oraycn.es.communicate.utils.BufferUtils;

import io.netty.buffer.ByteBuf;


public class LoginUser implements Comparable
{
		public static final String _UserID = "UserID";
		public static final String _UserType = "UserType";
		public static final String _PasswordMD5 = "PasswordMD5";
		public static final String _Name = "Name";
		public static final String _Friends = "Friends";
		public static final String _Signature = "Signature";
		public static final String _HeadImageIndex = "HeadImageIndex";
		public static final String _HeadImageData = "HeadImageData";
		public static final String _Groups = "Groups";
		public static final String _CreateTime = "CreateTime";
		public static final String _LastLoginTime = "LastLoginTime";
		public static final String _LastPasswordChangeTime = "LastPasswordChangeTime";
		public static final String _OnlineState = "OnlineState";
		public static final String _ClientIP = "ClientIP";
		public static final String _LoginRegionCode = "LoginRegionCode";
		public static final String _ClientSystem = "ClientSystem";
		public static final String _DefaultFriendCatalog = "DefaultFriendCatalog";
		public static final String _Version = "Version";
		public static final String _User_ContactOID = "User_ContactOID";
		public static final String _IsActivited = "IsActivited";

		public LoginUser()
		{
		}

		public LoginUser(String id, String pwd, int headIndex, UserType usertype, boolean isacted,
                         PersonInfo us)
		{
			this.setUserID(id);
			this.passwordMD5 = pwd;
			this.setHeadImageIndex(headIndex);
			this.m_UserType = usertype;//可能是学生、教师和管理员身份
			this.m_IsActivited = isacted;
			this.personInfo = us;
		}
		private String passwordMD5 = "";
				/** 
				 登录密码(MD5加密)〿
				 
				*/
				public final String getPasswordMD5()
				{
					return passwordMD5;
				}
				public final void setPasswordMD5(String value)
				{
					passwordMD5 = value;
				}
				private String userID = "";
				/** 
				 用户登录帐号〿
				*/
				public final String getUserID()
				{
					return userID;
				}
				public final void setUserID(String value)
				{
					userID = value;
				}
				private UserType m_UserType = UserType.Judge; //默认是教师
				public final UserType getUserType()
				{
					return this.m_UserType;
				}
				public final void setUserType(UserType value)
				{
					this.m_UserType = value;
				}
				private boolean m_IsActivited = false; //默认是没有激洿
				public final boolean getIsActivited()
				{
					return this.m_IsActivited;
				}
				public final void setIsActivited(boolean value)
				{
					this.m_IsActivited = value;
				}


				private int headImageIndex = -1;
				/**
				 头像图片的索引。如果为-1，表示自定义头像〿

				*/
				public final int getHeadImageIndex()
				{
					return headImageIndex;
				}
				public final void setHeadImageIndex(int value)
				{
					headImageIndex = value;
				//	this.headIcon = null;
				}

				private byte[] headImageData = null;
				public final byte[] getHeadImageData()
				{
					return headImageData;
				}
				public final void setHeadImageData(byte[] value)
				{
					headImageData = value;
					this.headImage = null;
					this.headImageGrey = null;
					//this.headIcon = null;
				}

				private String m_LastLoginTime;
				public final String getLastLoginTime()
				{
					return this.m_LastLoginTime;
				}
				public final void setLastLoginTime(String value)
				{
					this.m_LastLoginTime = value;
				}


				private String m_LastPasswordChangeTime ;
				public final String getLastPasswordChangeTime()
				{
					return this.m_LastPasswordChangeTime;
				}
				public final void setLastPasswordChangeTime(String value)
				{
					this.m_LastPasswordChangeTime = value;
				}

					///#endregion
				//传入数据库中的状怿

				private String m_ClientIP = "";
				public final String getClientIP()
				{
					return this.m_ClientIP;
				}
				public final void setClientIP(String value)
				{
					this.m_ClientIP = value;
				}
				private String m_LoginRegionCode = "";
				public final String getLoginRegionCode()
				{
					return this.m_LoginRegionCode;
				}
				public final void setLoginRegionCode(String value)
				{
					this.m_LoginRegionCode = value;
				}

				private int version = 0;
				public final int getVersion()
				{
					return version;
				}
				public final void setVersion(int value)
				{
					version = value;
				}
				private String createtime;
			   public final void setCreateTime(String timeDate){
				   this.createtime=timeDate;
			   }
			   public String getCreateTime(){
				   return this.createtime;
			   }
    private UserStatus m_OnlineState = UserStatus.OffLine;
    public final UserStatus getOnlineState()
    {
        return this.m_OnlineState;
    }
    public final void setOnlineState(UserStatus value)
    {
        this.m_OnlineState = value;
    }

    private PersonInfo personInfo; //用户真实资料
		public final PersonInfo getPersonInfo()
		{
			return personInfo;
		}
		public final void setPersonInfo(PersonInfo value)
		{
			personInfo = value;
		}

	public byte[] serialize() throws Exception{
		int UserLen=0;
		ByteBuf body = BufferUtils.newBuffer();
		body.writeInt(UserLen);
		UserLen+=4;

		UserLen+=4;
		if(m_ClientIP==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_ClientIP.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			UserLen+=pro.length;
		}

		UserLen+=4;
		if(createtime==null){
			body.writeInt(-1);
		}else{
			byte[] pro=createtime.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			UserLen+=pro.length;
		}

		//图片
		UserLen+=4;
		if(headImageData==null) {
			body.writeInt(-1);//
		}else {
			body.writeInt(headImageData.length);
			body.writeBytes(headImageData);
			UserLen+=headImageData.length;
		}

		//headimage
		body.writeInt(headImageIndex);//这个是headIamgeIndex
		UserLen+=4;
		//hos
		body.writeInt(-1);//这个是m_UserFrom
		UserLen+=4;

		if(this.m_IsActivited)
			body.writeByte(1);
		else
			body.writeByte(0);
		UserLen+=1;


		UserLen+=4;
		if(m_LastLoginTime==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_LastLoginTime.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			UserLen+=pro.length;
		}

		UserLen+=4;
		if(m_LastPasswordChangeTime==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_LastPasswordChangeTime.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			UserLen+=pro.length;
		}

		UserLen+=4;
		if(m_LoginRegionCode==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_LoginRegionCode.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			UserLen+=pro.length;
		}

		body.writeInt(m_OnlineState.getType());//这个是m_UserFrom
		UserLen+=4;

		UserLen+=4;
		if(passwordMD5==null){
			body.writeInt(-1);
		}else{
			byte[] pro=passwordMD5.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			UserLen+=pro.length;
		}

		byte[] userbytes=personInfo.serialize();
		body.writeBytes(userbytes);
		UserLen+=userbytes.length;

		UserLen+=4;
		if(userID==null){
			body.writeInt(-1);
		}else{
			byte[] pro=userID.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			UserLen+=pro.length;
		}

		body.writeInt(m_UserType.getType());//
		UserLen+=4;

		body.writeInt(version);//这个是m_UserFrom
		UserLen+=4;

		body.resetWriterIndex();//将指针移至顶端
		body.writeInt(UserLen);

		byte[] des=new byte[UserLen];
		System.arraycopy(body.array(), 0, des, 0, UserLen);
		return des;
	}
	public void deserialize(ByteBuf buffer) throws IOException {

		int len=buffer.readInt();
		if(len==-1){
			return ;//对象为null;
		}
		this.m_ClientIP=SerializeHelper.readStrIntLen(buffer);
		this.createtime= SerializeHelper.readStrIntLen(buffer);

		int headImageDataLen=buffer.readInt();//图片长度;
		if(headImageDataLen>0)//图片长度如果大于0
		{
			this.headImageData=new byte[headImageDataLen];
			buffer.readBytes(headImageData);
		}
		this.headImageIndex=buffer.readInt();//头像索引号
		this.m_IsActivited=buffer.readBoolean();//读取
		this.m_LastLoginTime=SerializeHelper.readStrIntLen(buffer);
		this.m_LastPasswordChangeTime=SerializeHelper.readStrIntLen(buffer);
		this.m_LoginRegionCode=SerializeHelper.readStrIntLen(buffer);
		this.m_OnlineState=UserStatus.getUserStatusByCode(buffer.readInt());//跟下面的有点重复
		this.passwordMD5=SerializeHelper.readStrIntLen(buffer);
		//UserContact对象流
		PersonInfo userPersonInfo=new PersonInfo();
        userPersonInfo.deserialize(buffer);
		this.personInfo=userPersonInfo;
		this.userID=SerializeHelper.readStrIntLen(buffer);
		this.m_UserType=UserType.getUserTypeByCode(buffer.readInt());
		this.version=buffer.readInt();
	}

	public static List<LoginUser> deserializeUserList(ByteBuf buffer) throws IOException
	{
		List<LoginUser> list=new ArrayList<LoginUser>();
		int userCount=buffer.readInt();

		for (int i=0;i<userCount;i++)
		{
			LoginUser loginUser =new LoginUser();
			loginUser.deserialize(buffer);
			list.add(loginUser);
		}
		return  list;
	}
		//[NonSerialized]
		private Image headImage = null;
		/**
		 自定义头像。非DB字段〿

		*/
		public final Image getHeadImage()
		{
			if (this.headImage == null && this.headImageData != null)
			{
				//this.headImage = ESBasic.Helpers.ImageHelper.Convert(this.headImageData);
			}
			return headImage;
		}


		private Image headImageGrey = null;
		/**
		 自定义头像。非DB字段〿

		*/
		public final Image getHeadImageGrey()
		{
			if (this.headImageGrey == null && this.headImageData != null)
			{
				//this.headImageGrey = ESBasic.Helpers.ImageHelper.ConvertToGrey(this.getHeadImage());
			}
			return this.headImageGrey;
		}

			///#endregion


			///#region GetHeadIcon

//		private Image headIcon = null;
//		/**
//		 自定义头像。非DB字段〿
//
//		*/
//		public final Image GetHeadIcon(Image[] defaultHeadImages)
//		{
//			if (this.headIcon != null)
//			{
//				return this.headIcon;
//			}
//
//			if (this.getHeadImage() != null)
//			{
//				this.headIcon = ImageHelper.ConvertToIcon(this.headImage, 64);
//				return this.headIcon;
//			}
//
//			this.headIcon = ImageHelper.ConvertToIcon(defaultHeadImages[this.headImageIndex], 64);
//			return this.headIcon;
//		}

			///#endregion




		private Object tag;
		/**
		 可用于存傿LastWordsRecord〿

		*/
		public final Object getTag()
		{
			return tag;
		}
		public final void setTag(Object value)
		{
			tag = value;
		}








			///#region PartialCopy

		//[NonSerialized]
//		private User partialCopy = null;
//		public final User getPartialCopy()
//		{
//			if (this.partialCopy == null)
//			{
//				this.partialCopy = (User)this.MemberwiseClone();
//				this.partialCopy.setGroups("");
//				this.partialCopy.setFriends("");
//			}
//			else
//			{
//				this.partialCopy.userStatus = this.userStatus;
//			}
//			return this.partialCopy;
//		}

			///#endregion


			///#region IUser 接口
		public final String getID()
		{
			return this.userID;
		}
		public final String getPersonName()
		{
			if(this==null||this.personInfo==null)
				return "";
			return this.personInfo.getPersonName();
		}

		private java.util.HashMap<String, ArrayList<String>> friendDicationary = null;

		private PlatformType m_PlatformType = PlatformType.PC;
		public final PlatformType getPlatformType()
		{
			return this.m_PlatformType;
		}


		@Override
		public int compareTo(Object another) {
			// TODO Auto-generated method stub

			int i= DateUtil.compare_date(this.createtime,((LoginUser) another).createtime);
			return i;
		}



}