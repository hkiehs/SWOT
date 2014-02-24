package model;
import utility.Restaurant;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Restaurant")
public class ParseRestaurant extends ParseObject {
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String TYPE = "type";
	public static final String TIMING = "timing";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String RATING = "rating";
	public static final String REVIEW = "review";
	public static final String LIKE = "like";
	public static final String RANGE = "range";
	public static final String HIGHLIGHT = "highight";
	
	public String name;
	public String address;
	public String type;
	public String timing;
	public String phoneNumber;
	public double latitude;
	public double longitude;
	public double rating;
	public int reviews;
	public int likes;
	public int range;
	public String highights;

	public ParseRestaurant() {
		// A default constructor is required.
	}

	public String getName() {
		return getString(NAME);
	}

	public String getAddress() {
		return getString(ADDRESS);
	}

	public String getType() {
		return getString(TYPE);
	}

	public String getTiming() {
		return getString(TIMING);
	}

	public String getPhoneNumber() {
		return getString(PHONE_NUMBER);
	}

	public double getLatitude() {
		return getDouble(LATITUDE);
	}

	public double getLongitude() {
		return getDouble(LONGITUDE);
	}

	public double getRating() {
		return getDouble(RATING);
	}

	public int getReviews() {
		return getInt(REVIEW);
	}

	public int getLikes() {
		return getInt(LIKE);
	}

	public int getRange() {
		return getInt(RANGE);
	}

	public String getHighights() {
		return getString(HIGHLIGHT);
	}

	public void setName(String name) {
		if (name != null)
			put(NAME, name);
	}

	public void setAddress(String address) {
		if (address != null)
			put(ADDRESS, address);
	}

	public void setType(String type) {
		if (type != null)
			put(TYPE, type);
	}

	public void setTiming(String timing) {
		if (timing != null)
			put(TIMING, timing);
	}

	public void setPhoneNumber(String phoneNumber) {
		if (phoneNumber != null)
			put(PHONE_NUMBER, phoneNumber);
	}

	public void setLatitude(double latitude) {
		put(LATITUDE, latitude);
	}

	public void setLongitude(double longitude) {
		put(LONGITUDE, longitude);
	}

	public void setRating(double rating) {
		put(RATING, rating);
	}

	public void setReviews(int reviews) {
		put(REVIEW, reviews);
	}

	public void setLikes(int likes) {
		put(LIKE, likes);
	}

	public void setRange(int range) {
		put(RANGE, range);
	}

	public void setHighights(String highights) {
		if (highights != null)
			put(HIGHLIGHT, highights);
	}
	
	public static ParseRestaurant createParseObject(Restaurant restaurant) {
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
		return parseRestaurant;
	}
}
