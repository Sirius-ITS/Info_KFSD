package com.kuwaitKFF.advertisements;

import java.util.ArrayList;
import java.util.HashMap;

import com.kuwaitKFF.base.BaseFragment;
import com.kuwaitKFF.main.Kfsd;

import com.kuwaitKFF.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class AdvertisementsFragment_old extends BaseFragment  {

	Kfsd kfsd;
	View view;
	ArrayList<String> listDataHeader;

	
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;

	HashMap<String, ArrayList<String>> listDataChild;
	TextView title;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		kfsd = (Kfsd) getActivity().getApplication();

		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_advertisements, null);
		
				
		
		expListView = (ExpandableListView) view.findViewById(R.id.listDisplay);

		title = (TextView)view.findViewById(R.id.textTitle);
		title.setText(getResources().getString(R.string.advertisement));
		
		
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				
				
				// Toast.makeText(getApplicationContext(),
			// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			 int previousGroup = 0;

			@Override
			public void onGroupExpand(int groupPosition) {
				
				
				if(groupPosition != previousGroup)
					expListView.collapseGroup(previousGroup);
	            previousGroup = groupPosition;
				
			
//				Toast.makeText(getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Expanded",
//						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				
			
//				Toast.makeText(getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Collapsed",
//						Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				
				Intent i = new Intent();
				i.setClass(getActivity(), AdvertisementsDetailsFragment_old.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
				startActivity(i);

				
			  return true;
			}
		});
       
		

		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, ArrayList<String>>();
		
		populatedata();
		
		listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		//setHeader(view, getResources().getString(R.string.login), false, false);
	
			setHeader(view, getResources().getString(R.string.advertisement), false, false);
		

	

		return view;
	}

	private void populatedata() {
		listDataHeader.add("Heading1");
		listDataHeader.add("Heading2");
		listDataHeader.add("Heading3");
		
		
		
		ArrayList<String> top250 = new ArrayList<String>();
		top250.add("Text1");
		top250.add("Text2");
		top250.add("Text3");
		top250.add("Text4");
		
		ArrayList<String> nowShowing = new ArrayList<String>();
		nowShowing.add("Text1");
		nowShowing.add("Text2");
		nowShowing.add("Text3");
		nowShowing.add("Text4");

		ArrayList<String> comingSoon = new ArrayList<String>();
		comingSoon.add("Text1");
		comingSoon.add("Text2");
		comingSoon.add("Text3");

		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		 listDataChild.put(listDataHeader.get(1), nowShowing);
		 listDataChild.put(listDataHeader.get(2), comingSoon);

			
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

				getFragmentManager().popBackStack();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;

		}

		return false;
	}

		

}
