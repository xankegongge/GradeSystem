package com.example.data.myclass;


public enum ProfessionTitle {

	助教(0),讲师(1),  副教授 (2), 教授 (3);
	private int type;

    public static ProfessionTitle getTitleByCode(int code) {
        for (ProfessionTitle type : ProfessionTitle.values()) {
            if (type.getType() == code) {
                return type;
            }
        }
        return null;
    }
    private ProfessionTitle(int type) {
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

    public static  String GetTitleName(ProfessionTitle status)
    {
       // ChannelBuffer body = new DynamicChannelBuffer(ByteOrder.LITTLE_ENDIAN, Consts.DYNAMIC_BUFFER_LEN);

        String name="";
        switch (status)
        {
            case  助教:
            name="助教";
            break;
            case  讲师:
                name="讲师";
                break;
            case  副教授:
                name="副教授";
                break;
            case  教授:
                name="教授";
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
