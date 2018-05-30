package com.example.data.myclass;

import com.oraycn.es.communicate.common.Consts;
import com.oraycn.es.communicate.utils.BufferUtils;


import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

/**
 * Created by ZN on 2015/9/2.
 */
public enum UserStatus
{
    Online(2),Away(3),  Busy (4), DontDisturb (5), OffLine (6),Hide(7);

    private int type;

    public static UserStatus getUserStatusByCode(int code) {
        for (UserStatus type : UserStatus.values()) {
            if (type.getType() == code) {
                return type;
            }
        }
        return null;
    }

    public static   byte[] GetStatusBytes(UserStatus status)
    {
       // ChannelBuffer body = new DynamicChannelBuffer(ByteOrder.LITTLE_ENDIAN, Consts.DYNAMIC_BUFFER_LEN);
        ByteBuf body= BufferUtils.newBuffer();
        body.writeInt(status.getType());
        return  body.array();
    }

    public static  String GetStatusName(UserStatus status)
    {
        String name="";
        switch (status)
        {
            case  Online:
            name="在线";
            break;
            case  Away:
                name="离开";
                break;
            case  Busy:
                name="正在忙";
                break;
            case  DontDisturb:
                name="请勿打扰";
                break;
            default:
                name="离线";
                break;
        }

        return  name;
    }

    private UserStatus(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
