package com.parse.starter;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.OfficialParseRestaurant;
import model.ParseComment;
import model.ParsePost;
import net.sf.classifier4J.ClassifierException;
import net.sf.classifier4J.SimpleClassifier;
import utility.CategorisedDatum;
import utility.Datum;
import utility.Datum.Datum4;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParseException;
import com.parse.SaveCallback;

public class ProcessRestaurantJson extends IntentService {
	private static final String DEBUG_TAG = "ProcessRestaurantJson";

	private SimpleClassifier classifier = null;
	private ParsePost parsePost = null;

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
			BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("review_filenames_list_2.txt")));
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
		String reviewFileName = null;
		for (int i = 0; i < reviewFileNames.size(); i++) {
			reviewFileName = reviewFileNames.get(i);
			classifier.setSearchWord(reviewFileName);
			numberOfMatches = 0;

			for (OfficialParseRestaurant.Result result : parseRestaurant.results) {
				try {
					if (classifier.isMatch(result.name)) {
						Log.i(DEBUG_TAG, "*Match* :: Restaurant name [" + result.name + "] withFileName[" + reviewFileName + "]");

						// read and post data to post table
						reviewFileJson = readFile("reviews/" + reviewFileName + ".txt");
						categorisedDatum = CategorisedDatum.fromJson(reviewFileJson);

						for (final Datum datum : categorisedDatum.datums) {
							parsePost = ParsePost.createParseObject(datum, result.objectId);

							try {
								parsePost.save();
							} catch (ParseException e1) {
								e1.printStackTrace();
							}

							String objectId = parsePost.getObjectId();
							Log.i(DEBUG_TAG, "**** Data inserted [" + objectId + "] Processing comments ****");
							if (datum.comments != null && datum.comments.data.size() > 0) {
								for (Datum4 datum4 : datum.comments.data) {
									ParseComment parseComment = new ParseComment.Builder(objectId, datum4.message, datum4.from.name, datum4.from.id).build();
									try {
										parseComment.save();
										Log.i(DEBUG_TAG, "**** Comment saved [ " + parseComment.getObjectId() + "] ****");
									} catch (ParseException e) {
										e.printStackTrace();
									}
								}
							}
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

			if (i == 30 || i == 60 || i == 90 || i == 120 || i == 150 || i == 180 || i == 210 || i == 240 || i == 270 || i == 300 || i == 330 || i == 360) {
				System.gc();
				Log.i(DEBUG_TAG, "Sleeping for 10 secs");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		Log.i(DEBUG_TAG, "Hurray!! I am done...");
	}

	public static int randInt(int min, int max) {
		// Usually this can be a field rather than a method variable
		Random rand = new Random();
		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
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
