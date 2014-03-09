package com.example.hellofoodies.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hellofoodies.R;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_meal_list, menu);
		return true;
	}

	/*
	 * Posting meals and refreshing the list will be controlled from the Action
	 * Bar.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_new : {
				newMeal();
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	private void newMeal() {
		Intent i = new Intent(this, NewPostActivity.class);
		startActivityForResult(i, 0);
	}
}
