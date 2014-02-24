package com.parse.starter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import model.ParsePost;
import model.ParseRestaurant;
import net.sf.classifier4J.ClassifierException;
import net.sf.classifier4J.SimpleClassifier;
import utility.CategorisedDatum;
import utility.Datum;
import utility.Restaurant;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class ParseStarterProjectActivity extends Activity {
	private static final String DEBUG_TAG = "ParseStarterProjectActivity";

	private Restaurant[] restaurants = null;
	private SimpleClassifier classifier = null;
	private List<ParseRestaurant> parseRestaurants = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ParseAnalytics.trackAppOpened(getIntent());

//		startService(new Intent(ParseStarterProjectActivity.this, RSSPullService.class));
		startService(new Intent(ParseStarterProjectActivity.this, ProcessRestaurantJson.class));
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

	private void pushRestaurantAndReviews() {

		// TODO: read x, y, z files and merge them in one file also put them in
		// the output folder with one name
		// TODO: get id for this restaurant name
		// read the json from each file
		// create the parse object

		String jsonArray = readFile("RestaurantsList.txt");
		System.out.println("File reading complete...");
		// create and insert restaurants
		restaurants = new Gson().fromJson(jsonArray, Restaurant[].class);
		for (Restaurant restaurant : restaurants) {
			pushRestaurant(restaurant);
			System.out.println("Pushed restaurant[" + restaurant.name + "]");
		}

		System.out.println("Push complete...");
		// SystemClock.sleep(2*60000);

		for (Restaurant salmanNewRestaurant : restaurants) {
			System.out.println("Processing restaurant[" + salmanNewRestaurant.name + "]");
			classifier.setSearchWord(salmanNewRestaurant.name);

			ParseRestaurant parseRestaurant = ParseRestaurant.createParseObject(salmanNewRestaurant);
			// get the object id for this restaurant
			parseRestaurant.fetchInBackground(new GetCallback<ParseRestaurant>() {
				public void done(ParseRestaurant parseRestaurant, ParseException e) {
					if (e == null) {
						final String objectId = parseRestaurant.getObjectId();
						System.out.println("********** ObjectId[" + objectId + "] ******************");

						ParsePost parsePost = null;
						for (Restaurant restaurant : restaurants) {
							try {
								if (classifier.isMatch(restaurant.name)) {
									String json = readFile("/review/" + restaurant.name);
									Log.i(DEBUG_TAG, "*******************");
									Log.i(DEBUG_TAG, json);
									CategorisedDatum categorisedDatum = CategorisedDatum.fromJson(json);

									for (Datum datum : categorisedDatum.datums) {
										parsePost = ParsePost.createParseObject(datum, objectId);
										parsePost.saveInBackground(new SaveCallback() {
											@Override
											public void done(ParseException e) {
												if (e == null) {
													Log.i(DEBUG_TAG, "**Success**");
												} else {
													Log.i(DEBUG_TAG, "Error[" + e.getMessage() + "]");
												}
											}
										});
										parsePost = null;
									}
								}
							} catch (ClassifierException e1) {
								e1.printStackTrace();
							}
						}

					} else {
						e.printStackTrace();
					}
				}
			});
		}
	}

	private ParseRestaurant pushRestaurant(Restaurant restaurant) {
		ParseRestaurant parseRestaurant = ParseRestaurant.createParseObject(restaurant);
		parseRestaurant.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.i(DEBUG_TAG, "**Restaurant Pushed**");
					// TODO: get primary id for this object and send it back
				} else {
					Log.i(DEBUG_TAG, "Error[" + e.getMessage() + "]");
				}
			}
		});
		return parseRestaurant;
	}
}
