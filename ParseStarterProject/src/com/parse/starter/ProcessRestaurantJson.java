package com.parse.starter;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import model.OfficialParseRestaurant;
import model.ParsePost;
import net.sf.classifier4J.ClassifierException;
import net.sf.classifier4J.SimpleClassifier;
import utility.CategorisedDatum;
import utility.Datum;
import android.app.IntentService;
import android.content.Context;
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

		int numberOfMatches = 0;
		String reviewFileJson = "";
		CategorisedDatum categorisedDatum = null;

		for (final String reviewFileName : reviewFileNames) {
			Log.i(DEBUG_TAG, "Processing Base File [" + reviewFileName + "]");
			classifier.setSearchWord(reviewFileName);
			numberOfMatches = 0;

			for (OfficialParseRestaurant.Result result : parseRestaurant.results) {
				try {
					if (classifier.isMatch(result.name)) {
						Log.i(DEBUG_TAG, "*Match* :: Restaurant name [" + result.name + "] withFileName[" + reviewFileName + "]");

						// read and post data to post table
						reviewFileJson = readFile("reviews/" + reviewFileName + ".txt");
						categorisedDatum = CategorisedDatum.fromJson(reviewFileJson);

						for (Datum datum : categorisedDatum.datums) {
							ParsePost.createParseObject(datum, result.objectId).saveEventually(new SaveCallback() {
								@Override
								public void done(ParseException e) {
									if (e == null) {
										Log.i(DEBUG_TAG, "**** Data inserted ****");
									} else {
										Log.i(DEBUG_TAG, "Error[" + e.getMessage() + "]");
									}
								}
							});
						}

						numberOfMatches++;
						reviewFileJson = null;
						categorisedDatum = null;
					}
				} catch (ClassifierException e1) {
					e1.printStackTrace();
				}
				result = null;
			}
			Log.i(DEBUG_TAG, "FileName[" + reviewFileName + "] MatchesCount[" + numberOfMatches + "]");
			System.gc();
			Log.i(DEBUG_TAG, "Sleeping for 4 secs");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Log.i(DEBUG_TAG, "Sleeping for 1 hour");
		try {
			Thread.sleep(3600000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void saveState(Context context, String data) {
		FileOutputStream fos;
		try {
			fos = context.openFileOutput("SWOT_FILE_STATUS", 0);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		} catch (IOException e) {
			Log.e("Exception", "File write failed: " + e.toString());
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
