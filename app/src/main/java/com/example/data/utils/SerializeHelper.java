package com.example.data.utils;

import java.io.IOException;
import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by ZN on 2015/9/2.
 */
public class SerializeHelper {

    public static String readStrIntLen(ByteBuf buffer) throws IOException {
        int len = buffer.readInt();
        if (len <= 0)
            return "";
        byte[] arrayOfByte = new byte[len];
        buffer.readBytes(arrayOfByte);
        return new String(arrayOfByte, "utf-8");
    }

    public static ByteBuf newBuffer(){
        //设置ByteBuf默认初始化大小为128，如果设置的过小，写入数据时会照成频繁的内存分配和memoryCopy
        return Unpooled.buffer(128).order(ByteOrder.LITTLE_ENDIAN);
    }

    public static ByteBuf newBuffer(int buffSize){
        return Unpooled.buffer(buffSize).order(ByteOrder.LITTLE_ENDIAN);
    }

    public static ByteBuf wrappedBuffer(ByteBuf... bufs){
        return Unpooled.wrappedBuffer(bufs);
    }

    public static ByteBuf wrappedBuffer(byte[] bufs){
        return Unpooled.wrappedBuffer(bufs).order(ByteOrder.LITTLE_ENDIAN);
    }
}
