package com.kuwaitKFF.base;



import com.kuwaitKFF.R;
import com.kuwaitKFF.advertisements.AdvertisementsGroupActivity;
import com.kuwaitKFF.common.MyCommon;
import com.kuwaitKFF.common.MySharedPref;
import com.kuwaitKFF.home.HomeGroupActivity;
import com.kuwaitKFF.more.MoreGroupActivity;
import com.kuwaitKFF.news.NewsGroupActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;


@SuppressWarnings("deprecation")
public class TabbarActivity extends TabActivity {
	static TabHost tabHost;
	String addToCart;
	int counter;
	private TabWidget tabDisplay;
	public static final String TAB_ID = "tabid";
	public static final String ADD_TO_CART = "addToCart";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabbar);
		
		tabDisplay = (TabWidget)findViewById(android.R.id.tabs);

		if(MySharedPref.getLanguage(TabbarActivity.this).equals(MyCommon.LANGUAGE_ENG))
		{
			Log.d("MzTag","ENG TABS11");
			setTabs();	
		}else
		{
			Log.d("MzTag","ARB TABS11");
			setTabsArabi();
		}
		
		Integer bigInt = (Integer) getIntent().getSerializableExtra(TAB_ID);
		
		
	
		
		if (bigInt != null) {
			tabHost.setCurrentTab(bigInt.intValue());
		} else {
			tabHost.setCurrentTab(0);
		}

		if(MySharedPref.getLanguage(TabbarActivity.this).equals(MyCommon.LANGUAGE_ENG))
		{
			tabHost.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Log.d("MzTag", "CLICKEDD");
					tabHost.setCurrentTab(3);
					tabHost.setCurrentTab(0);

				}
			});
		}
		else
		{
			tabHost.getTabWidget().getChildAt(3).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Log.d("MzTag", "CLICKEDD");
					tabHost.setCurrentTab(0);
					tabHost.setCurrentTab(3);

				}
			});
		}



//		if(MySharedPref.getLanguage(TabbarActivity.this).equals(MyCommon.LANGUAGE_ENG))
//		{
//			Log.d("MzTag","ENG TABS22");
//
//			tabHost.getTabWidget().getChildAt(0).setOnTouchListener(new View.OnTouchListener()
//			{
//
//
//					@Override
//					public boolean onTouch(View arg0, MotionEvent arg1)
//					{
//						// TODO Auto-generated method stub
//
//						System.out.println("Rupa inside on touch");
//

//						String currentTag = tabHost.getCurrentTabTag();
//						if (arg1.getAction() == MotionEvent.ACTION_DOWN )
//					{
//						LocalActivityManager manager = getLocalActivityManager();
//						System.out.println("currentTag " + currentTag);
//						manager.destroyActivity(currentTag, true);
//						manager.startActivity(currentTag, new Intent(TabbarActivity.this, HomeGroupActivity.class));
////						Intent intent = new Intent(TabbarActivity.this, HomeGroupActivity.class);
////						spec.setContent(intent);
//						tabHost.setCurrentTab(0);
//					}
//						return false;
//					}
//	});
//		}
//
//
//		else
//		{
//			Log.d("MzTag","ARB TABS22");
//
//			tabHost.getTabWidget().getChildAt(3).setOnTouchListener(new View.OnTouchListener()
//			{
//
//
//					@Override
//					public boolean onTouch(View arg0, MotionEvent arg1)
//					{
//						// TODO Auto-generated method stub
//						String currentTag = tabHost.getCurrentTabTag();
//						  	if (arg1.getAction() == MotionEvent.ACTION_DOWN )
//					{
//						LocalActivityManager manager = getLocalActivityManager();
//						System.out.println("currentTag "+currentTag);
//						manager.destroyActivity(currentTag, true);
//						manager.startActivity(currentTag, new Intent(TabbarActivity.this, HomeGroupActivity.class));
////						Intent intent = new Intent(TabbarActivity.this, HomeGroupActivity.class);
////						spec.setContent(intent);
//						tabHost.setCurrentTab(3);
//					}
//						return false;
//					}
//	});
//		}
		
	}

	private void setTabs() {
		MyCommon.saveSelectedLanguage(getBaseContext(), MyCommon.LANGUAGE_ENG);

		addTab("1", R.drawable.brands_tab_button,                                                                                             
				HomeGroupActivity.class);
		addTab("2", R.drawable.cart_tab_button,
				NewsGroupActivity.class);
		addTab("3", R.drawable.reorder_tab_button,
				AdvertisementsGroupActivity.class);
		addTab("4", R.drawable.promotions_tab_button,
				MoreGroupActivity.class);

	}

	
	private void setTabsArabi() {
		MyCommon.saveSelectedLanguage(getBaseContext(), MyCommon.LANGUAGE_AR);

		addTab("1", R.drawable.promotions_tab_button,
				MoreGroupActivity.class);
		addTab("2", R.drawable.cart_tab_button,
				AdvertisementsGroupActivity.class);
		addTab("3", R.drawable.reorder_tab_button,
				NewsGroupActivity.class);
		addTab("4", R.drawable.brands_tab_button,                                                                                             
				HomeGroupActivity.class);
	}
	
	private void addTab(final String txt, int drawableId, Class<?> c) {

		tabHost = getTabHost();

		Intent intent;
		if(drawableId == R.drawable.brands_tab_button)
			 intent = new Intent(this, c).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // only for home tab we make it refresh
		else
			 intent = new Intent(this, c);

		Bundle x = getIntent().getExtras();
		intent.putExtras(getIntent().getExtras());
	

		final TabHost.TabSpec spec = tabHost.newTabSpec("tab" + txt);

	View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, getTabWidget(), false);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		
		
		icon.setImageResource(drawableId);

		spec.setIndicator(tabIndicator);
		spec.setContent(intent);

		tabHost.addTab(spec);
		tabHost.setCurrentTab(0);
		

	}

	public static TabHost getHost() {
		return tabHost;
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// preventing default implementation previous to
			// android.os.Build.VERSION_CODES.ECLAIR
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Overrides the default implementation for KeyEvent.KEYCODE_BACK so that
	 * all systems call onBackPressed().
	 */

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {
				
				 super.onBackPressed();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;

		}

		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onResume() {
		super.onResume();
		try {
			this.getWindow().getDecorView().requestFocus();
		}catch(Exception e){}
	}




}
