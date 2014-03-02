package com.example.hellofoodies.activity;

import android.app.ListActivity;
import android.os.Bundle;

import com.example.hellofoodies.utility.WallFeedAdapter;

public class WallFeedActivity extends ListActivity {
	private static final String LOG_TAG = "WallFeedActivity";

	private WallFeedAdapter wallFeedAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wallFeedAdapter = new WallFeedAdapter(this);
		setListAdapter(wallFeedAdapter);
	}

//	private void updateMealList() {
//		wallFeedAdapter.loadObjects();
//		setListAdapter(wallFeedAdapter);
//	}
}
