package com.boyaa.push.lib.service;

import com.boyaa.push.lib.util.AtomicIntegerUtil;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 *
 */
public class Packet {
	
	private int id=AtomicIntegerUtil.getIncrementID();
	private byte[] data;
	
	public int getId() {
		return id;
	}

	public void pack(String txt)
	{
		data=txt.getBytes();
	}

	public void pack(byte[] d){
		data=d;
	}
	public byte[] getPacket()
	{
		return data;
	}
}
