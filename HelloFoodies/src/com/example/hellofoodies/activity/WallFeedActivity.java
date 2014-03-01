package com.example.hellofoodies.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.hellofoodies.R;
import com.example.hellofoodies.utility.EndlessAdapter;
import com.example.hellofoodies.utility.EndlessListView;

public class WallFeedActivity extends Activity implements EndlessListView.EndlessListener {
	private final static int ITEM_PER_REQUEST = 30;
	private EndlessListView endLessListView;

	int mult = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wallfeed_activity);

		endLessListView = (EndlessListView) findViewById(R.id.el);

		EndlessAdapter endLessAdapter = new EndlessAdapter(this, createItems(mult), R.layout.row_layout);
		endLessListView.setLoadingView(R.layout.loading_layout);
		endLessListView.setAdapter(endLessAdapter);
		endLessListView.setListener(this);
	}

	private class FakeNetLoader extends AsyncTask<String, Void, List<String>> {

		@Override
		protected List<String> doInBackground(String... params) {
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return createItems(mult);
		}

		@Override
		protected void onPostExecute(List<String> result) {
			super.onPostExecute(result);
			endLessListView.addNewData(result);
		}

	}

	private List<String> createItems(int mult) {
		List<String> result = new ArrayList<String>();

		for (int i = 0; i < ITEM_PER_REQUEST; i++) {
			result.add("Item " + (i * mult));
		}

		return result;
	}

	@Override
	public void loadData() {
		System.out.println("Load data");
		mult += 10;
		// We load more data here
		FakeNetLoader fl = new FakeNetLoader();
		fl.execute(new String[]{});
	}
}
