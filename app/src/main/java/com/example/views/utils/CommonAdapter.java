package com.example.views.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter
{
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	protected final int mItemLayoutId;

	public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId)
	{
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
	}

	@Override
	public int getCount()
	{
		return mDatas.size();
	}

	@Override
	public T getItem(int position)
	{
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final ViewHolder viewHolder = getViewHolder(position, convertView,
				parent);
		convert(viewHolder, getItem(position));
		return viewHolder.getConvertView();

	}

	public abstract void convert(ViewHolder helper, T item);

	private ViewHolder getViewHolder(int position, View convertView,
			ViewGroup parent)
	{
		return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
				position);
	}
	
	public int addItems( List<T> item){
		if (item != null) {
			int counter = 0;
			for (int i = 0; i < item.size(); i++) {
				counter +=1;
				mDatas.add(item.get(i));
			}
			this.notifyDataSetChanged();
			return counter;
		}
		return 0;
	}
	
	public boolean addItem(T item) {
		if (item!= null) {
			mDatas.add(item);
			this.notifyDataSetChanged();
			return true;
		}
		return false;
	}
	
	public boolean removeItem(int pos){
		if(pos>=0){
			mDatas.remove(pos);
			this.notifyDataSetChanged();
			return true;
		}
		return false;
	}
	}
