package com.parse.starter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.OfficialParseRestaurant;
import model.ParsePost;
import net.sf.classifier4J.ClassifierException;
import net.sf.classifier4J.SimpleClassifier;
import utility.CategorisedDatum;
import utility.Datum;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.parse.ParseException;
import com.parse.SaveCallback;

public class ProcessRestaurantJson extends IntentService {
	private static final String DEBUG_TAG = "ProcessRestaurantJson";

	private SimpleClassifier classifier = null;

	public ProcessRestaurantJson() {
		super("ProcessRestaurantJson");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// read restaurants file
		String json = readFile("Restaurant.json");
		OfficialParseRestaurant parseRestaurant = OfficialParseRestaurant.fromJson(json);
		Log.i(DEBUG_TAG, "**** restaurants json read complete****");

		classifier = new SimpleClassifier();

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

		for (final String reviewFileName : reviewFileNames) {
			Log.i(DEBUG_TAG, "Processing Base File [" + reviewFileName + "]");
			classifier.setSearchWord(reviewFileName);

			for (final OfficialParseRestaurant.Result result : parseRestaurant.results) {
				try {
					if (classifier.isMatch(result.name)) {
						Log.i(DEBUG_TAG, "*Match* :: Restaurant name [" + result.name + "]");
						// read and post data to post table
						String reviewFileJson = readFile("/review/" + reviewFileName);
						CategorisedDatum categorisedDatum = CategorisedDatum.fromJson(reviewFileJson);
						ParsePost parsePost = null;
						for (Datum datum : categorisedDatum.datums) {
							parsePost = ParsePost.createParseObject(datum, result.objectId);
							parsePost.saveInBackground(new SaveCallback() {
								@Override
								public void done(ParseException e) {
									if (e == null) {
										Log.i(DEBUG_TAG, "**Post table updated for restaurant name [" + result.name + "]");
									} else {
										Log.i(DEBUG_TAG, "Error[" + e.getMessage() + "]");
									}
								}
							});
							parsePost = null;
						}
					} else {
						Log.i(DEBUG_TAG, "False [" + reviewFileName + "] != [" + result.name + "]");
					}
				} catch (ClassifierException e1) {
					e1.printStackTrace();
				}
			}
		}

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
