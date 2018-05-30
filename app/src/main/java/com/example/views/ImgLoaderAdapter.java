package com.example.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

import com.example.administrator.gradesystem.R;

import com.example.views.utils.CommonAdapter;
import com.example.views.utils.ViewHolder;

public class ImgLoaderAdapter extends CommonAdapter<String>
{

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public  List<String> mSelectedImage = new LinkedList<String>();

	/**
	 * 文件夹路�??
	 */
	private String mDirPath;
	private Context mContext;
	public ImgLoaderAdapter(Context context, List<String> mDatas, int itemLayoutId,
			String dirPath)
	{
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
		this.mContext = context;
	}
	
	public List<String> getSelectPath() {
		return mSelectedImage;
	}
	@Override
	public void convert(ViewHolder helper, final String item) {

		//设置no_pic
		helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		//设置no_selected
				helper.setImageResource(R.id.id_item_select,
						R.drawable.picture_unselected);
		//设置图片
		helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);
		
		final ImageView mImageView = helper.getView(R.id.id_item_image);
		final ImageView mSelect = helper.getView(R.id.id_item_select);
		
		mSelect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// 已经选择过该图片
				if (mSelectedImage.contains(mDirPath + "/" + item))
				{
					mSelectedImage.remove(mDirPath + "/" + item);
					mSelect.setImageResource(R.drawable.picture_unselected);
					mImageView.setColorFilter(null);
				} else
				// 未�?�择该图�??
				{
					mSelectedImage.add(mDirPath + "/" + item);
					mSelect.setImageResource(R.drawable.pictures_selected);
					mImageView.setColorFilter(Color.parseColor("#77000000"));
				}
				
			}
		});
		mImageView.setColorFilter(null);
		//设置ImageView的点击事�??
		mImageView.setOnClickListener(new OnClickListener()
		{
			//选择，则将图片变暗，反之则反�??
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(mContext,ActivityReViewImg.class);
				intent.putExtra("imgpath", mDirPath + "/" + item);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); 
				((Activity)mContext).startActivity(intent);
				((Activity)mContext).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


			}
		});
		
		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (mSelectedImage.contains(mDirPath + "/" + item))
		{
			mSelect.setImageResource(R.drawable.pictures_selected);
			mImageView.setColorFilter(Color.parseColor("#77000000"));
		}

	
		
	}
}
