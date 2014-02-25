package com.parse.starter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.ParseRestaurant;
import net.sf.classifier4J.SimpleClassifier;
import utility.Restaurant;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class RSSPullService extends IntentService {
	private static final String DEBUG_TAG = "ParseStarterProjectActivity";

	private SimpleClassifier classifier = null;
	private List<ParseRestaurant> parseRestaurants = null;

	public RSSPullService() {
		super("RSSPullService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// read restaurants file
		String json = readFile("RestaurantsList.txt");
		Log.i(DEBUG_TAG, "**** restaurants json read complete****");
		// Log.i(DEBUG_TAG, json);

		// form an array using json
		Restaurant[] restaurants = new Gson().fromJson(json, Restaurant[].class);
		Log.i(DEBUG_TAG, "total restaurants [" + restaurants.length + "]");

		// start creating parse objects out of raw data objects
		parseRestaurants = new ArrayList<ParseRestaurant>();
		for (Restaurant restaurant : restaurants) {
			parseRestaurants.add(ParseRestaurant.createParseObject(restaurant));
		}
		restaurants = null;

		// read restaurant names list given
		List<String> reviewFileNames = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("review_filenames_list.txt")));
			String line;
			while ((line = br.readLine()) != null) {
				reviewFileNames.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		classifier = new SimpleClassifier();

//		for (final String reviewFileName : reviewFileNames) {
//			Log.i(DEBUG_TAG, "Processing Base File [" + reviewFileName + "]");
//			classifier.setSearchWord(reviewFileName);

			// start pushing restaurants to parse
			// NOTE: No, need to push again and again if already pushed!!
			for (final ParseRestaurant parseRestaurant : parseRestaurants) {
				parseRestaurant.saveInBackground(new SaveCallback() {
					@Override
					public void done(ParseException e) {
						if (e == null) {
							final String objectId = parseRestaurant.getObjectId();
							Log.i(DEBUG_TAG, "**Restaurant Pushed** Object Id[" + objectId + "]");
//							try {
//								if (classifier.isMatch(parseRestaurant.name)) {
//									Log.i(DEBUG_TAG, "*Match* :: Restaurant name [" + parseRestaurant.name + "] filename [" + reviewFileName + "]");
//									// read and post data to post table
//									String json = readFile("/review/" + reviewFileName);
//									CategorisedDatum categorisedDatum = CategorisedDatum.fromJson(json);
//									ParsePost parsePost = null;
//									for (Datum datum : categorisedDatum.datums) {
//										parsePost = ParsePost.createParseObject(datum, objectId);
//										parsePost.saveInBackground(new SaveCallback() {
//											@Override
//											public void done(ParseException e) {
//												if (e == null) {
//													Log.i(DEBUG_TAG, "**Post table updated for restaurant name [" + parseRestaurant.name + "]");
//												} else {
//													Log.i(DEBUG_TAG, "Error[" + e.getMessage() + "]");
//												}
//											}
//										});
//										parsePost = null;
//									}
//								}
//							} catch (ClassifierException e1) {
//								e1.printStackTrace();
//							}
						} else {
							Log.i(DEBUG_TAG, "Error[" + e.getMessage() + "]");
						}
					}
				});
			}
//		}

		try {
			Log.i(DEBUG_TAG, "Sleeping :");
			Thread.sleep(3600000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private String readFile(String filename) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(filename)));
			String line;
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	@Override
	public void onDestroy() {
		Log.i(DEBUG_TAG, "Service destroyed");
		super.onDestroy();
	}

	
}
