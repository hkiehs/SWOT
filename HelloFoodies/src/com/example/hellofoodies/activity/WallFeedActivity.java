package com.example.hellofoodies.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.hellofoodies.R;
import com.example.hellofoodies.handler.ParseHandler;
import com.example.hellofoodies.parse.ParsePost;
import com.example.hellofoodies.utility.EndlessAdapter;
import com.example.hellofoodies.utility.EndlessListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class WallFeedActivity extends Activity implements EndlessListView.EndlessListener {
	private static final String LOG_TAG = "WallFeedActivity";
	private static int ITEM_PER_REQUEST = 10;
	private static int SKIP_PER_REQUEST = 0;

	private EndlessListView endLessListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wall_feed);

		endLessListView = (EndlessListView) findViewById(R.id.el);

		ParseQuery<ParsePost> query = ParseHandler.queryPost(ITEM_PER_REQUEST, null);
		query.findInBackground(new FindCallback<ParsePost>() {
			@Override
			public void done(List<ParsePost> objects, ParseException e) {
				if (e == null) {
					EndlessAdapter endLessAdapter = new EndlessAdapter(WallFeedActivity.this, objects, R.layout.widget_wall_feed);
					endLessListView.setLoadingView(R.layout.loading_layout);
					endLessListView.setAdapter(endLessAdapter);
					endLessListView.setListener(WallFeedActivity.this);
				} else {
					Log.d(LOG_TAG, "Error: " + e.getMessage());
				}
			}
		});
	}

	@Override
	public void loadData() {
		SKIP_PER_REQUEST = SKIP_PER_REQUEST + ITEM_PER_REQUEST;
		ParseQuery<ParsePost> query = ParseHandler.queryPost(ITEM_PER_REQUEST, SKIP_PER_REQUEST);
		query.findInBackground(new FindCallback<ParsePost>() {
			@Override
			public void done(List<ParsePost> objects, ParseException e) {
				if (e == null) {
					endLessListView.addNewData(objects);
				} else {
					Log.d(LOG_TAG, "Error: " + e.getMessage());
				}
			}
		});
	}
}
