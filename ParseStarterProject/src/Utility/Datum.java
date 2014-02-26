package utility;
import java.util.List;

import com.google.gson.Gson;

public class Datum {

	public String id;
	public From from;
	public String message;
	public String type;
	public String created_time;
	public String updated_time;
	public Likes likes;
	public Comments comments;
	public String picture;
	public String link;
	public String icon;

	public Datum() {
	}

	public class From {
		public String name;
		public String id;
	}

	public class Datum2 {
		public String category;
		public String name;
		public String id;
		public List<From> category_list;
	}

	public class Datum4 {
		public From from;
		public String message;
		public int like_count;
	}

	public class Comments {
		public List<Datum4> data;
	}

	public class Likes {
		public List<From> data;
	}

	public static Datum fromJson(String json) {
		return new Gson().fromJson(json, Datum.class);
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
}