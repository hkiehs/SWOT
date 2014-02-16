package com.parse.starter;
import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Restaurant")
public class ParseRestaurant extends ParseObject {

	public ParseRestaurant() {
		// A default constructor is required.
	}

	public String getName() {
		return getString("name");
	}

	public String getAddress() {
		return getString("address");
	}

	public String getType() {
		return getString("type");
	}

	public String getTiming() {
		return getString("timing");
	}

	public String getPhoneNumber() {
		return getString("phoneNumber");
	}

	public double getLatitude() {
		return getDouble("latitude");
	}

	public double getLongitude() {
		return getDouble("longitude");
	}

	public double getRating() {
		return getDouble("rating");
	}

	public int getReviews() {
		return getInt("reviews");
	}

	public int getLikes() {
		return getInt("likes");
	}

	public int getRange() {
		return getInt("range");
	}

	public String getHighights() {
		return getString("highights");
	}

	public void setName(String name) {
		if (name != null)
			put("name", name);
	}

	public void setAddress(String address) {
		if (address != null)
			put("address", address);
	}

	public void setType(String type) {
		if (type != null)
			put("type", type);
	}

	public void setTiming(String timing) {
		if (timing != null)
			put("timing", timing);
	}

	public void setPhoneNumber(String phoneNumber) {
		if (phoneNumber != null)
			put("phoneNumber", phoneNumber);
	}

	public void setLatitude(double latitude) {
		put("latitude", latitude);
	}

	public void setLongitude(double longitude) {
		put("longitude", longitude);
	}

	public void setRating(double rating) {
		put("rating", rating);
	}

	public void setReviews(int reviews) {
		put("reviews", reviews);
	}

	public void setLikes(int likes) {
		put("likes", likes);
	}

	public void setRange(int range) {
		put("range", range);
	}

	public void setHighights(String highights) {
		if (highights != null)
			put("highights", highights);
	}
}
