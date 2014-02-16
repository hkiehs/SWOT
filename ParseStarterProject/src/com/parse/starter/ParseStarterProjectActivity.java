package com.parse.starter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class ParseStarterProjectActivity extends Activity {
	private static final String DEBUG_TAG = "ParseStarterProjectActivity";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ParseAnalytics.trackAppOpened(getIntent());

		StringBuilder json = readFile();
		String jsonString = json.toString();
		Log.i(DEBUG_TAG, jsonString);
		Restaurant[] restaurants = new Gson().fromJson(jsonString, Restaurant[].class);

		ParseRestaurant parseRestaurant = new ParseRestaurant();
		for (Restaurant restaurant : restaurants) {
			parseRestaurant = new ParseRestaurant();

			parseRestaurant.setName(restaurant.name);
			parseRestaurant.setAddress(restaurant.address);
			parseRestaurant.setType(restaurant.type);
			parseRestaurant.setTiming(restaurant.timing);
			parseRestaurant.setPhoneNumber(restaurant.phoneNumber);
			parseRestaurant.setLatitude(restaurant.latitude);
			parseRestaurant.setLongitude(restaurant.longitude);
			parseRestaurant.setRating(restaurant.rating);
			parseRestaurant.setReviews(restaurant.reviews);
			parseRestaurant.setLikes(restaurant.likes);
			parseRestaurant.setRange(restaurant.range);
			parseRestaurant.setHighights(restaurant.highights);

			parseRestaurant.saveInBackground(new SaveCallback() {

				@Override
				public void done(ParseException e) {
					if (e == null) {
						Log.i(DEBUG_TAG, "**Success**");
					} else {
						Log.i(DEBUG_TAG, "Error[" + e.getMessage() + "]");
					}
				}

			});

			parseRestaurant = null;
		}

	}

	private StringBuilder readFile() {
		// File sdcard = Environment.getExternalStorageDirectory();
		// File file = new File(sdcard, "file.txt");
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("restaurant_json.txt")));
			String line;
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder;
	}
}
