package com.example.data.myclass;


import com.example.data.utils.SerializeHelper;
import com.oraycn.es.communicate.utils.BufferUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;

public class PersonInfo
{

		///#region Force Static Check
	public static final String TableName = "UserContact";
	public static final String _User_ContactOID = "User_ContactOID";
	public static final String _PersonName = "PersonName";
	public static final String _WorkPhone = "WorkPhone";
	public static final String _MobilePhone = "MobilePhone";
	public static final String _Email = "Email";
	public static final String _WorkAddress = "WorkAddress";
	public static final String _HomeAddress = "HomeAddress";
	public static final String _WorkPostCode = "WorkPostCode";
	public static final String _HomePostCode = "HomePostCode";
	public static final String _IsExpert = "IsExpert";
	public static final String _IsMan = "IsMan";
	public static final String _Birthday = "Birthday";
	public static final String _ProfessionTitle = "ProfessionTitle";
	public static final String _Hobby = "Hobby";
	public static final String _MarriageState = "MarriageState";
	public static final String _Education = "Education";
	public static final String _SYS_CreatedTime = "SYS_CreatedTime";
	public static final String _SYS_CreatedByUserID = "SYS_CreatedByUserID";
	public static final String _Hospital = "Hospital";

		///#endregion


		///#region 构造方�?
	public PersonInfo()
	{

	}
	public PersonInfo(String realname, String mobilephone, String email)
	{
		this.m_PersonName = realname;
		this.m_MobilePhone = mobilephone;
		this.m_Email = email;

	}
	public void deserialize(ByteBuf buffer) throws IOException {
		int len=buffer.readInt();
		if(len==-1){
			return ;//对象为null;
		}
		this.m_Birthday= SerializeHelper.readStrIntLen(buffer);
		this.m_Education=SerializeHelper.readStrIntLen(buffer);
		this.m_Email=SerializeHelper.readStrIntLen(buffer);
		this.m_Hobby=SerializeHelper.readStrIntLen(buffer);
		this.m_HomeAddress=SerializeHelper.readStrIntLen(buffer);
		this.m_HomePostCode=SerializeHelper.readStrIntLen(buffer);
		this.m_Introduction=SerializeHelper.readStrIntLen(buffer);
		this.m_IsMan=buffer.readBoolean();
		this.m_MarriageState=SerializeHelper.readStrIntLen(buffer);
		this.m_MobilePhone=SerializeHelper.readStrIntLen(buffer);
		this.m_PersonName=SerializeHelper.readStrIntLen(buffer);
		this.m_SYS_CreatedTime=SerializeHelper.readStrIntLen(buffer);
		this.m_WorkAddress=SerializeHelper.readStrIntLen(buffer);
		this.m_WorkPhone=SerializeHelper.readStrIntLen(buffer);
		this.m_WorkPostCode=SerializeHelper.readStrIntLen(buffer);

	}
	public byte[] serialize() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		int contactLen=0;
		ByteBuf body = BufferUtils.newBuffer();
		body.writeInt(contactLen);
		contactLen+=4;

		contactLen+=4;
		if(m_Birthday==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_Birthday.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}

		contactLen+=4;
		if(m_Education==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_Education.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}

		contactLen+=4;
		if(m_Email==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_Email.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}

		contactLen+=4;
		if(m_Hobby==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_Hobby.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}

		contactLen+=4;
		if(m_HomeAddress==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_HomeAddress.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}

		contactLen+=4;
		if(m_HomePostCode==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_HomePostCode.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}


		contactLen+=4;
		if(m_Introduction==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_Introduction.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}


		if(this.m_IsMan)
			body.writeByte(1);
		else
			body.writeByte(0);
		contactLen+=1;





		contactLen+=4;
		if(m_MarriageState==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_MarriageState.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}

		contactLen+=4;
		if(m_MobilePhone==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_MobilePhone.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}

		contactLen+=4;
		if(m_PersonName==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_PersonName.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}


		contactLen+=4;
		if(m_WorkAddress==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_WorkAddress.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}



		contactLen+=4;
		if(m_WorkPhone==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_WorkPhone.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}

		contactLen+=4;
		if(m_WorkPostCode==null){
			body.writeInt(-1);
		}else{
			byte[] pro=m_WorkPostCode.getBytes("UTF-8");
			body.writeInt(pro.length);
			body.writeBytes(pro);
			contactLen+=pro.length;
		}
		body.resetWriterIndex();//将指针移至顶端
		body.writeInt(contactLen);

		byte[] des=new byte[contactLen];
		System.arraycopy(body.array(), 0, des, 0, contactLen);
		return des;

	}

	private String m_Introduction="";
	
	public String getIntroduction() {
		return m_Introduction;
	}
	public void setIntroduction(String m_Introduction) {
		this.m_Introduction = m_Introduction;
	}


	private String m_PersonName = "";
	public final String getPersonName()
	{
		return this.m_PersonName;
	}
	public final void setPersonName(String value)
	{
		this.m_PersonName = value;
	}

	private String m_WorkPhone = "";
	public final String getWorkPhone()
	{
		return this.m_WorkPhone;
	}
	public final void setWorkPhone(String value)
	{
		this.m_WorkPhone = value;
	}

	private String m_MobilePhone = "";
	public final String getMobilePhone()
	{
		return this.m_MobilePhone;
	}
	public final void setMobilePhone(String value)
	{
		this.m_MobilePhone = value;
	}

	private String m_Email = "";
	public final String getEmail()
	{
		return this.m_Email;
	}
	public final void setEmail(String value)
	{
		this.m_Email = value;
	}
	private String m_WorkAddress = "";
	public final String getWorkAddress()
	{
		return this.m_WorkAddress;
	}
	public final void setWorkAddress(String value)
	{
		this.m_WorkAddress = value;
	}
	private String m_HomeAddress = "";
	public final String getHomeAddress()
	{
		return this.m_HomeAddress;
	}
	public final void setHomeAddress(String value)
	{
		this.m_HomeAddress = value;
	}
	private String m_WorkPostCode = "";
	public final String getWorkPostCode()
	{
		return this.m_WorkPostCode;
	}
	public final void setWorkPostCode(String value)
	{
		this.m_WorkPostCode = value;
	}
	private String m_HomePostCode = "";
	public final String getHomePostCode()
	{
		return this.m_HomePostCode;
	}
	public final void setHomePostCode(String value)
	{
		this.m_HomePostCode = value;
	}
	private boolean m_IsMan = false;
	public final boolean getIsMan()
	{
		return this.m_IsMan;
	}
	public final void setIsMan(boolean value)
	{
		this.m_IsMan = value;
	}
	private String m_Birthday;
	public final String getBirthday()
	{
		return this.m_Birthday;
	}
	public final void setBirthday(String value)
	{
		this.m_Birthday = value;
	}
	private String m_Hobby = "";
	public final String getHobby()
	{
		return this.m_Hobby;
	}
	public final void setHobby(String value)
	{
		this.m_Hobby = value;
	}
	private String m_MarriageState = "";
	public final String getMarriageState()
	{
		return this.m_MarriageState;
	}
	public final void setMarriageState(String value)
	{
		this.m_MarriageState = value;
	}
	private String m_Education = "";
	public final String getEducation()
	{
		return this.m_Education;
	}
	public final void setEducation(String value)
	{
		this.m_Education = value;
	}
	private String m_SYS_CreatedTime = new String();
	public final String getSYS_CreatedTime()
	{
		return this.m_SYS_CreatedTime;
	}
	public final void setSYS_CreatedTime(String value)
	{
		this.m_SYS_CreatedTime = value;
	}

	@Override
	public String toString()
	{
		return  this.getPersonName().toString();
	}

		///#endregion
}