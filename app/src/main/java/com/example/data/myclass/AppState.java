package com.example.data.myclass;

/**
 * Created by Administrator on 2017/11/7 0007.
 */
public enum AppState {

    STARTING(0), STARTED(1), CONNECTING(2), CONNECTED(3), STOPPING(4), STOPPED(5);
    private int type;

    public static ProfessionTitle getTitleByCode(int code) {
        for (ProfessionTitle type : ProfessionTitle.values()) {
            if (type.getType() == code) {
                return type;
            }
        }
        return null;
    }
    private AppState(int type) {
        this.type = type;
    }
//    public static   byte[] GetTitleBytes(EProfessionTitle status)
//    {
//        ChannelBuffer body = new DynamicChannelBuffer(ByteOrder.LITTLE_ENDIAN, Consts.DYNAMIC_BUFFER_LEN);
//
//        body.writeInt(status.getType());
//
//        return  body.array();
//    }

    public static  String GetAppStateName(AppState status)
    {
        // ChannelBuffer body = new DynamicChannelBuffer(ByteOrder.LITTLE_ENDIAN, Consts.DYNAMIC_BUFFER_LEN);

        String name="";
        switch (status)
        {
            case  STARTING:
                name="正在打开";
                break;
            case  STARTED:
                name="已打开";
                break;
            case  CONNECTING:
                name="正在连接";
                break;
            case  CONNECTED:
                name="已连接";
                break;
            case STOPPING:
                name="停止中";
                break;
            case STOPPED:
                name="已停止";
                break;
            default:
                name="";
                break;
        }

        return  name;
    }
    public int getType() {
        return type;
    }
}