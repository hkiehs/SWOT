import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class RestaurantList {
	public List<String> restaurants;

	public RestaurantList() {
		restaurants = new ArrayList<String>();
	}

	public static RestaurantList fromJson(String json) {
		return new Gson().fromJson(json, RestaurantList.class);
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
}
