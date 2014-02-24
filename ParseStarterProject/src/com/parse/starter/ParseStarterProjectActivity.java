package com.parse.starter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.ParsePost;
import model.ParseRestaurant;
import net.sf.classifier4J.ClassifierException;
import net.sf.classifier4J.SimpleClassifier;
import Utility.CategorisedDatum;
import Utility.Datum;
import Utility.Restaurant;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class ParseStarterProjectActivity extends Activity {
	private List<String> restaurantNames;
	private SimpleClassifier classifier;

	private static final String DEBUG_TAG = "ParseStarterProjectActivity";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ParseAnalytics.trackAppOpened(getIntent());
		String restaurant = "restaurant_json.txt";
		pushRestaurantAndReviews();

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
//		// read the restaurant list line by line
//		// upload the json
//
//		restaurantNames = new ArrayList<String>();
//
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(restaurantListFile));
//			String filename;
//			while ((filename = br.readLine()) != null) {
//				restaurantNames.add(filename);
//			}
//			br.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("file read complete...");
//
//		String name1 = "B.B.Q.txt";
//		String name2 = "Biryani.txt";
//		String name3 = "Broast.txt";
//		String name4 = "Burger.txt";
//		String name5 = "Chinese.txt";
//		String name6 = "Haleem.txt";
//		String name7 = "Juice.txt";
//		String name8 = "Nihari.txt";
//		String name9 = "Pan.txt";
//		String name10 = "Pizza.txt";
//		String name11 = "Roll.txt";
//		String name12 = "Sajji.txt";
//		String name13 = "Boat Basin.txt";
//
//		List<String> filteredRestaurantNames = new ArrayList<String>();
//		for (String restaurantName : restaurantNames) {
//			// ignore dishes
//			if (restaurantName.equalsIgnoreCase(name1) || restaurantName.equalsIgnoreCase(name2) || restaurantName.equalsIgnoreCase(name3)
//					|| restaurantName.equalsIgnoreCase(name4) || restaurantName.equalsIgnoreCase(name5) || restaurantName.equalsIgnoreCase(name6)
//					|| restaurantName.equalsIgnoreCase(name7) || restaurantName.equalsIgnoreCase(name8) || restaurantName.equalsIgnoreCase(name9)
//					|| restaurantName.equalsIgnoreCase(name10) || restaurantName.equalsIgnoreCase(name11) || restaurantName.equalsIgnoreCase(name12)
//					|| restaurantName.equalsIgnoreCase(name13)) {
//			} else {
//				filteredRestaurantNames.add(restaurantName);
//			}
//		}
//		restaurantNames = null;
//		restaurantNames = new ArrayList<String>(filteredRestaurantNames);

		// TODO: read x, y, z files and merge them in one file also put them in
		// the output folder with one name
		// TODO: get id for this restaurant name
		// read the json from each file
		// create the parse object

		String jsonArray = readFile("SalmanNewRestaurantList.txt");
		// create object array object
		Restaurant[] restaurants = new Gson().fromJson(jsonArray, Restaurant[].class);
//		for (Restaurant restaurant : restaurants) {
//			System.out.println("RestaurantName[" + restaurant.name + "]");
//		}
		
		ParseRestaurant parseRestaurant = null;
		for (Restaurant salmanNewRestaurant : restaurants) {
			classifier = new SimpleClassifier();
			classifier.setSearchWord(salmanNewRestaurant.name);

			System.out.println("Pused restaurant [" + salmanNewRestaurant.name + "]");
			// TODO: insert restaurant and get the primary id
			parseRestaurant = pushRestaurant(salmanNewRestaurant); // get
																		// the
																		// update
																		// parse
																		// object
			parseRestaurant.fetchInBackground(new GetCallback<ParseRestaurant>() {
				public void done(ParseRestaurant parseRestaurant, ParseException e) {
					if (e == null) {
						final String objectId = parseRestaurant.getObjectId();
						System.out.println("********** ObjectId[" + objectId + "] ******************"); 
						ParsePost parsePost = null;

						for (String name : restaurantNames) {
							try {
								if (classifier.isMatch(name)) {
									String json = readFile("/review/" + name);
									Log.i(DEBUG_TAG, "*******************");
									Log.i(DEBUG_TAG, json);
									CategorisedDatum categorisedDatum = CategorisedDatum.fromJson(json);

									for (Datum datum : categorisedDatum.datums) {
										System.out.println("Review :: " + datum.toJson());
										parsePost = new ParsePost();
										parsePost.setId(datum.id);
										parsePost.setMessage(datum.message);
										parsePost.setType(datum.type);
										parsePost.setLikes(datum.likes.data.size());
										parsePost.setPicture(datum.picture);
										parsePost.setLink(datum.link);
										parsePost.setIcon(datum.icon);
										parsePost.setFromId(datum.from.id);
										parsePost.setFromName(datum.from.name);
										parsePost.setRestaurantId(objectId);

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
								// TODO Auto-generated catch block
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
		ParseRestaurant parseRestaurant = new ParseRestaurant();
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
					// TODO: get primary id for this object and send it back
				} else {
					Log.i(DEBUG_TAG, "Error[" + e.getMessage() + "]");
				}
			}
		});
		return parseRestaurant;
	}

	private void processRestaurants(String filename) {
		String jsonString = readFile(filename);
		Log.i(DEBUG_TAG, jsonString);
		ParseRestaurant[] restaurants = new Gson().fromJson(jsonString, ParseRestaurant[].class);

		ParseRestaurant parseRestaurant = new ParseRestaurant();
		for (ParseRestaurant restaurant : restaurants) {
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
}
