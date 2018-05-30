package com.example.data.myclass;

import com.example.data.utils.SerializeHelper;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

/**
 * Created by Administrator on 2017-11-18.
 */

public class RespLogon {
    public LogonResult get_LoginResult() {
        return m_LoginResult;
    }

    public void set_LoginResult(LogonResult m_LoginResult) {
        this.m_LoginResult = m_LoginResult;
    }

    public String get_Failure() {
        return m_Failure;
    }

    public void set_Failure(String m_Failure) {
        this.m_Failure = m_Failure;
    }

    private LogonResult m_LoginResult;
    private String m_Failure ;//失败理由;
    public  RespLogon()
    {

    }
    public RespLogon(LogonResult login,String fail)
    {
        m_LoginResult=login;
        m_Failure = fail;
    }
    public void deserialize(ByteBuf buffer) throws IOException {

        int len = buffer.readInt();
        if (len == -1) {
            return;//对象为null;
        }

        this.m_Failure = SerializeHelper.readStrIntLen(buffer);
        this.m_LoginResult = LogonResult.getLogonResultByCode(buffer.readInt());//读取
    }
}
