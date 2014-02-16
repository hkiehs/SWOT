package com.parse.starter;
import com.google.gson.Gson;

public class Restaurant {
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

	public String toJson() {
		return new Gson().toJson(this);
	}

	public static Restaurant fromJson(String json) {
		return new Gson().fromJson(json, Restaurant.class);
	}
}
