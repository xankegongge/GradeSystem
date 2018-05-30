package com.example.data.myclass;

/**
 自定义信息的类型，取值0-100
 
*/
public final class InformationTypes
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Function
	/** 
	 聊天信息 0
	 
	*/
	public static final int LOGIN = 0;

	/** 
	 窗口抖动 1
	 
	*/
	public static final int Vibration = 1;

	/** 
	 请求视频 2
	 
	*/
	public static final int VideoRequest = 2;

	/** 
	 同意视频 3 （C->C）
	 
	*/
	public static final int AgreeVideo = 3;

	/** 
	 拒绝视频 4 （C->C）
	 
	*/
	public static final int RejectVideo = 4;

	/** 
	 挂断视频 5 （C->C）
	 
	*/
	public static final int HungUpVideo = 5;


	/** 
	 请求访问对方的磁盘 6 （C->C）
	 
	*/
	public static final int DiskRequest = 6;

	/** 
	 同意磁盘访问 7 （C->C）
	 
	*/
	public static final int AgreeDisk = 7;

	/** 
	 拒绝磁盘访问 8 （C->C）
	 
	*/
	public static final int RejectDisk = 8;

	/** 
	 请求对方远程协助自己（访问自己的桌面） 9 （C->C）
	 
	*/
	public static final int RemoteHelpRequest = 9;

	/** 
	 同意远程协助对方 10 （C->C）
	 
	*/
	public static final int AgreeRemoteHelp = 10;

	/** 
	 拒绝远程协助对方 （C->C）
	 
	*/
	public static final int RejectRemoteHelp = 11;

	/** 
	 请求方终止了协助 （C->C）
	 
	*/
	public static final int TerminateRemoteHelp = 12;

	/** 
	 协助方终止了协助 （C->C）
	 
	*/
	public static final int CloseRemoteHelp = 13;

	/** 
	 请求离线消息 （C->S）
	 
	*/
	public static final int GetOfflineMessage = 14;

	/** 
	 服务端转发离线消息给客户端 （S->C）
	 
	*/
	public static final int OfflineMessage = 15;

	/** 
	 请求离线文件 （C->S）
	 
	*/
	public static final int GetOfflineFile = 16;

	/** 
	 通知发送方，对方是接收了离线文件，还是拒绝了离线文件。（S->C）
	 
	*/
	public static final int OfflineFileResultNotify = 17;

	/** 
	 通知对方自己正在输入 （C->C）
	 
	*/
	public static final int InputingNotify = 18;

	/** 
	 请求对语音对话 （C->C）
	 
	*/
	public static final int AudioRequest = 19;

	/** 
	 同意语音对话 （C->C）
	 
	*/
	public static final int AgreeAudio = 20;

	/** 
	 拒绝语音对话 （C->C）
	 
	*/
	public static final int RejectAudio = 21;

	/** 
	 挂断语音对话 （C->C）
	 
	*/
	public static final int HungupAudio = 22;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 个人资料、状态
	/** 
	 获取用户资料（C->S）
	 
	*/
	public static final int GetUserInfo = 32;

	/** 
	 修改自己的个人资料（C->S）
	 
	*/
	public static final int UpdateUserInfo = 33;

	/** 
	 获取指定某些用户的资料（C->S）
	 
	*/
	public static final int GetSomeUsers = 34;

	/** 
	 通知某人资料发生了变化（S->C）
	 
	*/
	public static final int UserInforChanged = 35;

	/** 
	 修改状态（C->S）
	 
	*/
	public static final int ChangeStatus = 36;

	/** 
	 通知某人状态发生了变化（S->C）
	 
	*/
	public static final int UserStatusChanged = 37;

	/** 
	 修改密码（C->S）
	 
	*/
	public static final int ChangePassword = 38;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region contacts
	/** 
	 获取我的所有联系人的在线状态、版本号，以及我的所有组的版本号（C->S）
	 
	*/
	public static final int GetContactsRTData = 40;

	/** 
	 获取我的所有好友ID（C->S）
			 
	*/
	public static final int GetFriendIDList = 41;

	/** 
	 获取我的所有联系人资料（C->S）
	 
	*/
	public static final int GetAllContacts = 42;


	/** 
	 添加好友（C->S）
	 
	*/
	public static final int AddFriend = 43;

	/** 
	 删除好友（C->S）
	 
	*/
	public static final int RemoveFriend = 44;

	/** 
	 通知客户端其被对方从好友中删除（S->C）
	 
	*/
	public static final int FriendRemovedNotify = 45;

	/** 
	 通知客户端其被对方添加为好友（S->C）
	 
	*/
	public static final int FriendAddedNotify = 46;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Group
	/** 
	 获取我的所有组资料（C->S）
	 
	*/
	public static final int GetMyGroups = 50;

	/** 
	 获取指定的某些组的资料（C->S）
	 
	*/
	public static final int GetSomeGroups = 51;

	/** 
	 加入组（C->S）
	 
	*/
	public static final int JoinGroup = 52;

	/** 
	 获取组资料（C->S）
	 
	*/
	public static final int GetGroup = 53;

	/** 
	 创建组（C->S）
	 
	*/
	public static final int CreateGroup = 54;

	/** 
	 退出组（C->S）
	 
	*/
	public static final int QuitGroup = 55;

	/** 
	 解散组（C->S）
	 
	*/
	public static final int DeleteGroup = 56;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region FriendCatalog
	/** 
	 添加好友分组（C->S）
	 
	*/
	public static final int AddFriendCatalog = 70;

	/** 
	 修改好友分组名称（C->S）
	 
	*/
	public static final int ChangeFriendCatalogName = 71;

	/** 
	 删除好友分组（C->S）
	 
	*/
	public static final int RemoveFriendCatalog = 72;

	/** 
	 移动好友到别的分组（C->S）
	 
	*/
	public static final int MoveFriendToOtherCatalog = 73;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region 阅片消息
	/** 
	 获取所有专家的用户信息
	 
	*/

    public static final int GetAllHospitals=87;//获取所有医院列表
	public static final int SendUpdateMedicalReading=88; //正在处理状态时，专家或用户更新阅片信息，
	public static final int SendMedicalReadingReceived=89; //专家发送接收信息
	public static final int SendMedicalReadingRejectedReason=90; //专家发送拒绝理由
	public static final int GetAllExperts = 91; //获取所有专家列表消息
	public static final int GetMedicalReading= 92; //获取所有阅片消息
	public static final int SendMedicalReading = 93; //发送阅片信息
	public static final int MedicalInsertOKServerACK = 94; //服务器回复阅片提交发送消息
	public static final int GetAllMedicalReading = 95; //客户端查询所有阅片信息
	public static final int GetMDImages = 96; //获取阅片图片列表;
	public static final int GetClientOrExpertMDGuids=97; //获取客户或者专家的所有阅片GUID列表；
	public static final int GetSomeMedicalReading=98; //通过部分guid列表获取对应阅片信息；（用于分批获取阅片信息）
	public static final int MedicalReadingAdd=99; //服务器发送已插入成功消息，

	public static final int GetAllCheckedUser=86;//获取所有已经审核人员列表;

	public static final int CheckUserResult=85;//提交审核结果;

	public static final int CheckCancel = 83;//发送给其他客服取消用户的审核，因为已经被审核了

	public static final int NewCheckUser=84;

	public static final int GetMDSmallImages=82;

	public static final int GetSingelImage = 81;

	public static final int OfflineMDMessage = 80;

	public static final int UpdateCheckUserInfo = 79;

	public static final int GetUnCheckedUser = 78;//先获取未审核的用户列表;

	public static final int Advice=77;
	public static final int GetNewMedicalReadings = 76;//上啦获取最新的阅片
	public static final int GetMoreMedicalReadings = 75;//下拉获取更多的阅片;
	public static final int GetNewCheckUserInfo=74;
	public static final int GetMoreCheckUserInfo=73;


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	//取值0-200。 V1.9
	public static boolean ContainsInformationType(int infoType)
	{
		return infoType >= 0 && infoType <= 200;
	}
}