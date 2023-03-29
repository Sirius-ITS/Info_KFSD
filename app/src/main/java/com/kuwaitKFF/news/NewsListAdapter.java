package com.kuwaitKFF.news;

import java.util.ArrayList;

import com.kuwaitKFF.R;
import com.kuwaitKFF.untilities.ImageLoader;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

public class NewsListAdapter extends BaseAdapter {
	ArrayList<NewsResponseBean> mList;
	Context mActivity;

	public NewsListAdapter(FragmentActivity mActivity,
						   ArrayList<NewsResponseBean> myResMutableList) {
		// TODO Auto-generated constructor stub
		super();
		this.mActivity = mActivity;
		this.mList =  myResMutableList;

	}

	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder1;

		convertView = LayoutInflater.from(mActivity).inflate(
				R.layout.row_news_list, null);
		holder1 = new ViewHolder();
		holder1.title = (TextView) convertView.findViewById(R.id.newsTitleText);
		holder1.desc = (TextView) convertView.findViewById(R.id.descText);
		holder1.img = (ImageView) convertView.findViewById(R.id.imageNews);
		
		
		convertView.setTag(holder1);
		if(getItem(position).getNewsFilefullPath()!= null)
		{
			 String DownloadUrl = getItem(position).getNewsFilefullPath();
			Picasso.with(this.mActivity).load(DownloadUrl).into(holder1.img);
			// imgLoader.DisplayImage(DownloadUrl,  holder1.img);
			 
			
		}else{
			//holder1.brandImage.setImageResource(R.drawable.pinkberry);
			
			
		}

		holder1.title.setText(getItem(position).getNewsSource());
		holder1.desc.setText(getItem(position).getNewsSubject());
//		holder1.title.setVisibility(View.GONE);
		return convertView;

	}

	ImageLoader imgLoader = new ImageLoader(this.mActivity);
	static class ViewHolder {
		TextView title;
		TextView desc;
		ImageView img;
		

	}

	@Override
	public int getCount() {

		return mList.size();
	}

	@Override
	public NewsResponseBean getItem(int position) {

		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
}
