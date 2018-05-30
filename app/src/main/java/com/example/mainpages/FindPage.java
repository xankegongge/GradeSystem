package com.example.mainpages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.gradesystem.GradeApplication;
import com.example.administrator.gradesystem.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.swu.pulltorefreshswipemenulistview.library.PullToRefreshSwipeMenuListView;
import edu.swu.pulltorefreshswipemenulistview.library.pulltorefresh.interfaces.IXListViewListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenu;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenuItem;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnMenuItemClickListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnSwipeListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.SwipeMenuCreator;
import edu.swu.pulltorefreshswipemenulistview.library.util.RefreshTime;


public class FindPage extends Fragment implements IXListViewListener{
	 private static final int MESSAGE = 0x22;
		// private List<ApplicationInfo> mAppList;

	   // private AppAdapter mAdapter;
	    private PullToRefreshSwipeMenuListView mListView;
	    private Handler mHandler;
		private View rootView;
		private GradeApplication app;
		private TextView showEmptyMsg;
		
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("FragmentPage1=>>>>",  "onCreateView");
		if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.main_message, null);
           
        }
	
	return rootView;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("ContactPage========>>>>",  "onCreate");
		setRetainInstance(true);
		this.app=(GradeApplication)this.getActivity().getApplication();
//		List<String> allUserIDList =app.getAllContactsIDList();//得到所有的用户，包含专家来进行监听;
//		for(String usrID:allUserIDList){
//			AddChatEvent(usrID);//将用户添加至监听列表中
//		}
//		contactMessageList=app.getGlobalUserCache().getContactMessageInfoList();//获取本地保存的消息列表;
//		if(contactMessageList==null){
//			contactMessageList=new ArrayList<ContactMessageInfo>();
//		}
//		List<GGUser> userList=app.getMyFriendUserInfo();//得到朋友列表;
//        for(int i=0;i<userList.size();i++)
//        {
//            ContactMessageInfo user=new ContactMessageInfo(userList.get(i));
//            Date now = new Date(); 
//            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");//可以方便地修改日期格式
//            String hehe = dateFormat.format( now ); 
//            user.setTimeString(hehe);
//            addMessageUser(user);
//        }
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("ContactPage========>>>>",  "OnPause");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("ContactPage========>>>>",  "onResume");
//		for(int i=0;i<app.getHadTalkedUserList().size();i++)
//		{
//			String talkingString=app.getHadTalkedUserList().get(i);
//			app.setHaveNewChatContent(talkingString, false);//取消小红点;
//			  if(hadreadCountMap.containsKey(talkingString)){
//	          	hadreadCountMap.remove(talkingString);
//	          }
//	          hadreadCountMap.put(talkingString, app.getUnReadMessageCount(talkingString));
//		}
		//mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("ContactPage========>>>>",  "onStart");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		Log.i("ContactPage========>>>>",  "onViewCreated");
		initView(view);
		 //请求离线消息 
       // app.getEngine().getCustomizeOutter().send(InformationTypes.GetOfflineMessage, null);//请求离线消息
	}
	private void initView(View view) {
		// TODO Auto-generated method stub
		mListView = (PullToRefreshSwipeMenuListView) view.findViewById(R.id.listView);
		showEmptyMsg = (TextView) view.findViewById(R.id.show_emptyMes);
//        mAdapter = new AppAdapter(getActivity().getApplicationContext(), contactMessageList);
//        mListView.setAdapter(mAdapter);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(false);
		mListView.setXListViewListener(this);
		mListView.setDivider(null);
		mHandler = new Handler();

		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(getActivity().getApplicationContext());
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
				// set item width
				openItem.setWidth(dp2px(90));
				// set item title
				openItem.setTitle("打开");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		mListView.setMenuCreator(creator);

		// step 2. listener item click event
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
//                ContactMessageInfo item = contactMessageList.get(position);
//                switch (index) {
//                case 0:
//                    // open
//                    open(item);
//                    break;
//                case 1:
//                    // delete
//                    // delete(item);
//                	contactMessageList.remove(position);
//                    mAdapter.notifyDataSetChanged();
//                    if(!contactMessageList.isEmpty())//如果不是空，不显示提示;
//             	       showEmptyMsg.setVisibility(View.GONE);
//             	        else {
//             	        	showEmptyMsg.setVisibility(View.VISIBLE);
//             			}
//                    break;
//                }

			}
		});

		// set SwipeListener
		mListView.setOnSwipeListener(new OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});

		// other setting
		// listView.setCloseInterpolator(new BounceInterpolator());

		// test item long click
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getActivity().getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {


			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

			}
		});
	}

	
	
//	 private void AddChatEvent(String userID) {
//	        app.AddChatMessageListener(userID, this);
//	    }
//	public class AppAdapter extends BaseAdapter {
//
//	    private Context context;
//
//	    private List<ContactMessageInfo> messageList;
//
//	    public AppAdapter(Context context,  List<ContactMessageInfo> mAppList) {
//	        this.context = context;
//	        this.messageList = mAppList;
//	    }
//
//	    @Override
//	    public int getCount() {
//	        return messageList.size();
//	    }
//
//	    @Override
//	    public ContactMessageInfo getItem(int position) {
//	        return messageList.get(position);
//	    }
//
//	    @Override
//	    public long getItemId(int position) {
//	        return position;
//	    }
//
//	    @Override
//	    public View getView(int position, View convertView, ViewGroup parent) {
//	        if (convertView == null) {
//	            convertView = View.inflate(context, R.layout.layout_message_item, null);
//	            new ViewHolder(convertView);
//	        }
//	        ViewHolder holder = (ViewHolder) convertView.getTag();
//	        ContactMessageInfo item = getItem(position);
//	        if(item!=null){
//		        holder.messagePhoto.setImageBitmap(GlobalData.getHeaderBitmap(app.getUserByID(item.getUserID())));
//				holder.messageUserName.setText(item.getUserName());
//		        holder.messageLastWord.setText(item.getLastWordsString());
//		        holder.messagetime.setText(item.getTimeString());
//		        holder.messageUnReadCount.setText(""+app.getUnReadCount(item.getUserID()));
//		        //如果有新消息，提示小红点
//	            if(app.getUnReadCount(item.getUserID())==0)
//	            {
//	                holder.messageUnReadCount.setVisibility(View.INVISIBLE);
//	            }
//	            else
//	            {
//	                holder.messageUnReadCount.setVisibility(View.VISIBLE);
//	            }
//	        }
//
//	        return convertView;
//	    }
//
//	    class ViewHolder {
//	        public TextView messageUnReadCount;
//			public TextView messagetime;
//			public TextView messageLastWord;
//			public TextView messageUserName;
//			public CircleImage messagePhoto;
//
//	        public ViewHolder(View view) {
//	        	messagePhoto = (CircleImage) view.findViewById(R.id.message_user_photo);
//	        	messageUserName = (TextView) view.findViewById(R.id.message_user_name);
//	        	messageLastWord = (TextView) view.findViewById(R.id.message_lastword);
//	        	messagetime = (TextView) view.findViewById(R.id.message_time);
//	        	messageUnReadCount = (TextView) view.findViewById(R.id.message_unread_count);
//	            view.setTag(this);
//	        }
//	    }
//	}
	private void onLoad() {
        mListView.setRefreshTime(RefreshTime.getRefreshTime(getActivity().getApplicationContext()));
        mListView.stopRefresh();

        mListView.stopLoadMore();

    }
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		 mHandler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	                SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
	                RefreshTime.setRefreshTime(getActivity().getApplicationContext(), df.format(new Date()));
	                onLoad();
	            }
	        }, 2000);
	}
	  private void delete(ApplicationInfo item) {
	        // delete app
	        try {
	            Intent intent = new Intent(Intent.ACTION_DELETE);
	            intent.setData(Uri.fromParts("package", item.packageName, null));
	            startActivity(intent);
	        } catch (Exception e) {
	        }
	    }
//	  private void open(ContactMessageInfo item) {
//	        // open app
//			// TODO Auto-generated method stub
//			Intent intent = new Intent();
//          intent.setClass(getActivity(), ChatActivity.class);
//          String talkingUserID=item.getUserID();
//          String talkingUserName=item.getUserName();
//          intent.putExtra("TalkingUserID", talkingUserID);
//          intent.putExtra("TalkingUserName", talkingUserName);
//          app.setHaveNewChatContent(talkingUserID, false);
//          getActivity().startActivity(intent);
//          edithandler.sendMessage(new Message());//更新界面，取消小红点;
//	    }

	    private int dp2px(int dp) {
	        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	    }
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoad();
            }
        }, 2000);
	}
	 private Handler edithandler = new Handler() {//界面更新；
	        @Override
	        public void handleMessage(Message msg) {
//	        	switch (msg.what) {
//				case MESSAGE:
//					ChatMessageRecord record=(ChatMessageRecord)(msg.obj);
//					ChatContentContract content=FaceConversionUtil.getInstace().getChatContentContract(record.getContent());
//					String targetString=msg.getData().getString("UserID");
//					String sourceID=null;
//					//如果发现是发送者是自己
//					if(targetString.equals(app.getMyUserInfo().getUserID())){
//						sourceID=record.getAudienceID();//则监听的为对方;
//					}else {
//						sourceID=record.getSpeakerID();//则监听为说话的人;
//					}
//				   String word=FaceConversionUtil.getInstace().getRichString(content);//得到富文本
//				    if(word.length()>15){
//				    	word=word.substring(0, 14)+"...";
//				    }
//				    Date now = new Date();
//		            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");//可以方便地修改日期格式
//		            String hehe = dateFormat.format( now );
//				  ContactMessageInfo cmiContactMessageInfo= app.getGlobalUserCache().getContactMessageUser(sourceID);
//
//				    if(cmiContactMessageInfo==null){//消息list没有这个人物，则添加这个人物消息
//				    	GGUser GGuser=app.getUserByID(sourceID);
//				    	 ContactMessageInfo mess=new ContactMessageInfo(GGuser);
//				    	 mess.setTimeString(hehe);
//				    	 mess.setLastWordsString(word);
//				    	 app.getGlobalUserCache().addContactMessageUser(mess);
//				    }else {//有的话，则更新该用户的
//				    	cmiContactMessageInfo.setTimeString(hehe);
//				    	cmiContactMessageInfo.setLastWordsString(word);
//					}
//
//					break;
//
//				default:
//					break;
//				}
//	        	  if(!contactMessageList.isEmpty())//如果不是空，不显示提示;
//	       	       showEmptyMsg.setVisibility(View.GONE);
//	       	        else {
//	       	        	showEmptyMsg.setVisibility(View.VISIBLE);
//	       			}
//	        	mAdapter.notifyDataSetChanged();
	        }
	    };


	
//	@Override
//	public void ChatMessageReceived(ChatMessageRecord chatContent){
//			 Message ms=new Message();
//			 ms.what=MESSAGE;
//			 Bundle bundle=new Bundle();
//			 bundle.putString("UserID", chatContent.getSpeakerID());//接收肯定是来自其他用户的信息;
//			 ms.setData(bundle);
//			 ms.obj=chatContent;
//	         edithandler.sendMessage(ms);
//	}

	

//	@Override
//	public void FriendStatusChanged(String sourceUserID, UserStatus status) {
//		// TODO Auto-generated method stub
//		app.changMyFriendStatus(sourceUserID, status);//改变朋友状态
//	}
}
