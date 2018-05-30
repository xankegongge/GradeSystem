package com.example.views;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import com.example.administrator.gradesystem.R;
import com.example.data.utils.BitmapTools;
import com.example.views.utils.ImageFloder;

import com.example.views.ListImageDirPopupWindow.OnImageDirSelected;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

//批量加载图片
public class ActivityImgLoader extends SwipeBackActivity implements OnImageDirSelected
{
	private  ProgressDialog mProgressDialog;

	/**
	 * 存储文件夹中的图片数�?
	 */
	private int mPicsSize;
	/**
	 * 图片数量最多的文件�?
	 */
	private File mImgDir;
	/**
	 * �?有的图片
	 */
	private List<String> mImgs;

	private GridView mGirdView;
	private ImgLoaderAdapter mAdapter;
	/**
	 * 临时的辅助类，用于防止同个文件夹的多次扫描
	 */
	private HashSet<String> mDirPaths = new HashSet<String>();

	/**
	 * 扫描拿到所有有的图片文件目录
	 */
	private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

	private RelativeLayout mBottomLy;

	private TextView mChooseDir;
	private TextView mImageCount;
	int totalCount = 0;

	private int mScreenHeight;

	private ListImageDirPopupWindow mListImageDirPopupWindow;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			mProgressDialog.dismiss();
			// 为View绑定数据
			data2View();
			// 初始化展示文件夹的popupWindw
			initListDirPopupWindw();
		}
	};

	private TextView tv_Choose;

	/**
	 * 为View绑定数据
	 */

	private void data2View()
	{
		if (mImgDir == null)
		{
			Toast.makeText(getApplicationContext(), "无图片可扫描",
					Toast.LENGTH_SHORT).show();
			return;
		}
		String [] fileArrayStrings=mImgDir.list(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String filename)
			{
				if (filename.endsWith(".jpg")
						|| filename.endsWith(".png")||filename.endsWith(".jpeg")
						)
					return true;
				return false;
			}
		});
		mImgs = Arrays.asList(fileArrayStrings);
		Collections.sort(mImgs,new Comparator<String>(){
			@Override
			public int compare(String arg0, String arg1) {
				File file1 = new File(mImgDir.getAbsolutePath()+"/"+arg0);
				File file2 = new File(mImgDir.getAbsolutePath()+"/"+arg1);
				if (file1 == null || !file1.exists()) {
					return 1;
				}
				if (file2 == null || !file2.exists()) {
					return 1;
				}
				if( file1.lastModified() >= file2.lastModified()){
					return -1;
				}
				return 1;
			}
			 
		 });  
		//Collections.reverse(mImgs);
		/**
		 * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消�?�；
		 */
		mAdapter = new ImgLoaderAdapter( ActivityImgLoader.this, mImgs,
				R.layout.md_grid_item, mImgDir.getAbsolutePath());
		mGirdView.setAdapter(mAdapter);
		mImageCount.setText(totalCount + "张");
	};

	/**
	 * 初始化展示文件夹的popupWindw
	 */
	private void initListDirPopupWindw()
	{
		mListImageDirPopupWindow = new ListImageDirPopupWindow(
				LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
				mImageFloders, LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.md_list_dir, null));

		mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener()
		{

			@Override
			public void onDismiss()
			{
				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		});
		// 设置选择文件夹的回调
		mListImageDirPopupWindow.setOnImageDirSelected(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.md_activity_img_loader);
		StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bluetips), true);
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;

		initView();
		getImages();
		initEvent();

	}

	/**
	 * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，�?终获得jpg�?多的那个文件�?
	 */
	private void getImages()
	{
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度加载
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{

				String firstImage = null;

				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = ActivityImgLoader.this
						.getContentResolver();

				// 只查询jpeg和png的图�?
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?" ,
						new String[] { "image/jpeg","image/png" },
						MediaStore.Images.Media.DATE_MODIFIED+ " DESC");

				Log.e("TAG", mCursor.getCount() + "");
				while (mCursor.moveToNext())
				{
					// 获取图片的路�?
				//	String recentPicpath = mCursor.getColumnName(mCursor.getColumnCount()-1);
					String path	= mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));

					Log.e("TAG", path);
					// 拿到最新张图片的路径3
					if (firstImage == null)
						firstImage = path;
					// 获取该图片的父路径名
					File parentFile = new File(path).getParentFile();
					if (parentFile == null)
						continue;
					String dirPath = parentFile.getAbsolutePath();
					ImageFloder imageFloder = null;
					// 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~�?
					if (mDirPaths.contains(dirPath))
					{
						continue;
					} else
					{
						mDirPaths.add(dirPath);
						// 初始化imageFloder
						imageFloder = new ImageFloder();
						imageFloder.setDir(dirPath);
						imageFloder.setFirstImagePath(path);
					}

					int picSize = parentFile.list(new FilenameFilter()
					{
						@Override
						public boolean accept(File dir, String filename)
						{
							if (filename.endsWith(".jpg")
									|| filename.endsWith(".png")||filename.endsWith(".jpeg")
									)
								return true;
							return false;
						}
					}).length;
					totalCount += picSize;

					imageFloder.setCount(picSize);
					mImageFloders.add(imageFloder);

					if (picSize > mPicsSize)
					{
						mPicsSize = picSize;
						mImgDir = parentFile;
					}
				}
				mCursor.close();
				Collections.sort(mImageFloders,new Comparator<ImageFloder>(){
					@Override
					public int compare(ImageFloder arg0, ImageFloder arg1) {
						
						if (arg0 == null|| arg1 == null) {
							return 1;
						}
						boolean c1 = arg0.getDir().toLowerCase().contains("DCIM/CAMERA".toLowerCase());
						boolean c2 = arg1.getDir().toLowerCase().contains("DCIM/CAMERA".toLowerCase());
						//特殊文件夹排前面
						if (c1== false && c2 == true) {
							return 1;
						}
						else if( c1 == true && c2 == false){
							return -1;
						}else {
							//如果不是特殊文件夹 ，那么按照含有照片数量来排
							if(arg0.getCount()< arg1.getCount() ){
								return 1;
							}
							else if (arg0.getCount()> arg1.getCount()) {
								return -1;
							}
							else {
								return 0;
							}
							
						}
						 
					}
					 
				 });  

				// 扫描完成，辅助的HashSet也就可以释放内存�?
				mDirPaths = null;

				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(0x110);

			}
		}).start();

	}

	/**
	 * 初始化View
	 */
	private void initView()
	{
		mGirdView = (GridView) findViewById(R.id.id_gridView);
		mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
		mImageCount = (TextView) findViewById(R.id.id_total_count);

		mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
		((Button)findViewById(R.id.btn_selectimg_back)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ActivityImgLoader.this.finish();
			}
		});
		((Button)findViewById(R.id.btn_imgselect_finish)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				BitmapTools.ShowLoading(ActivityImgLoader.this );
				List<String> result = mAdapter.getSelectPath();
				Intent intent = new Intent();
				String [] resultStr = new String[result.size()] ;
				result.toArray(resultStr);
				intent.putExtra("result", resultStr);
				 
				ActivityImgLoader.this.setResult(RESULT_OK , intent);
 
				ActivityImgLoader.this.finish();
				
				
			}
		});

	}

	private void initEvent()
	{
		/**
		 * 为底部的布局设置点击事件，弹出popupWindow
		 */
		mBottomLy.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mListImageDirPopupWindow
						.setAnimationStyle(R.style.anim_popup_dir);
				mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = .3f;
				getWindow().setAttributes(lp);
			}
		});
	}
	List<File> mList=new ArrayList<File>();
	private class FileComparator implements Comparator<File>{

		@Override
		public int compare(File lhs, File rhs) {
			if(lhs.lastModified()<rhs.lastModified()){
				return 1;//最后修改的照片在前
			}else{
				return -1;
			}
		}
    	
    }
	
	@Override
	public void selected(ImageFloder floder)
	{

		mImgDir = new File(floder.getDir());
		mImgs = Arrays.asList(mImgDir.list(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String filename)
			{
				if (filename.endsWith(".jpg") || filename.endsWith(".png")
						|| filename.endsWith(".jpeg"))
					return true;
				return false;
			}
		}));
		Collections.sort(mImgs,new Comparator<String>(){
			@Override
			public int compare(String arg0, String arg1) {
				File file1 = new File(mImgDir.getAbsolutePath()+"/"+arg0);
				File file2 = new File(mImgDir.getAbsolutePath()+"/"+arg1);
				if (file1 == null || !file1.exists()) {
					return 1;
				}
				if (file2 == null || !file2.exists()) {
					return 1;
				}
				if( file1.lastModified() >= file2.lastModified()){
					return -1;
				}
				return 1;
			}
			 
		 });  

//    	int len = mImgs.size();    	
//    	for(int i=0;i<len;i++){
//    		mList.add(new File(mImgs.get(i)));	
//    	}
//    	Collections.sort(mList, new FileComparator());
	//	Collections.reverse(mImgs);//倒叙，让创建时间最近的排在最前;
		/**
		 * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消�?�；
		 */
		mAdapter = new ImgLoaderAdapter( ActivityImgLoader.this , mImgs,
				R.layout.md_grid_item, mImgDir.getAbsolutePath());
		mGirdView.setAdapter(mAdapter);
		// mAdapter.notifyDataSetChanged();
		mImageCount.setText(floder.getCount() + "张");
		mChooseDir.setText(floder.getName());
		mListImageDirPopupWindow.dismiss();

	}

}
