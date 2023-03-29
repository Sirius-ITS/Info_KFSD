package com.kuwaitKFF.news;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kuwaitKFF.R;
import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.main.Kfsd;
import com.kuwaitKFF.untilities.ImageLoader;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

public class NewsDetailsFragment extends BaseActivity   {

	Kfsd kfsd;
	View view;
	ArrayList<String> circularList;
	ListView listView;
	 String content,sTitle,sDate, mainTitle, picPath;
	 TextView webViewContent,title,dateText, titleName;
	ImageView imageView;
	
	public NewsDetailsFragment() {
		super(R.layout.fragment_news_details);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();
        Bundle b = getIntent().getExtras();
        content = b.getString("content");
        sDate = b.getString("dateText");
        sTitle = b.getString("title");
        mainTitle = b.getString("mainTitle");
		picPath = b.getString("picPath");



        webViewContent = (TextView) findViewById(R.id.webViewContent);
        webViewContent.setText(content);

        title = (TextView) findViewById(R.id.titleText);
        title.setText(sTitle);
        dateText = (TextView) findViewById(R.id.dateText);
        dateText.setText(sDate);
        titleName = (TextView) findViewById(R.id.titleName);
        titleName.setText(mainTitle);

		imageView = (ImageView)findViewById(R.id.imageView);


		ImageLoader imgLoader = new ImageLoader(kfsd);
		Log.d("ffffffffffffffff",picPath);



		//imgLoader.DisplayImage(picPath,  imageView);
		Picasso.with(this).load(picPath).into(imageView);

		setHeader(getResources().getString(R.string.news_detail), true, true);

	}

	
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// preventing default implementation previous to
			// android.os.Build.VERSION_CODES.ECLAIR
			return true;
		}
		return false;

	}

	/**
	 * Overrides the default implementation for KeyEvent.KEYCODE_BACK so that
	 * all systems call onBackPressed().
	 */

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {

				finish();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;

		}

		return false;
	}



	

}
