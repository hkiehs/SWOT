package model;

import java.util.List;

import com.google.gson.Gson;

public class OfficialParseRestaurant {
	public List<Result> results;

	public class Result {
		public String address;
		public String createdAt;
		public int latitude;
		public int like;
		public int longitude;
		public String name;
		public String objectId;
		public String phoneNumber;
		public int range;
		public int rating;
		public int review;
		public String timing;
		public String type;
		public String updatedAt;
	}
	
	public static OfficialParseRestaurant fromJson(String json) {
		return new Gson().fromJson(json, OfficialParseRestaurant.class);
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
}