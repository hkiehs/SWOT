package com.example.hellofoodies.activity;

import android.app.ListActivity;
import android.os.Bundle;

import com.example.hellofoodies.utility.WallFeedAdapter;

public class WallFeedActivity extends ListActivity {
	private static final String LOG_TAG = "WallFeedActivity";

	private static int ITEM_PER_REQUEST = 10;
	private static int SKIP_PER_REQUEST = 0;

	private WallFeedAdapter wallFeedAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(wallFeedAdapter);
//		updateMealList();
	}

	private void updateMealList() {
		wallFeedAdapter.loadObjects();
		setListAdapter(wallFeedAdapter);
	}
}
