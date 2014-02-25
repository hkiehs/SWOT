package utility;
import java.util.List;

import com.google.gson.Gson;
import com.parse.ParseObject;

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

	public class MessageTag {
		public String id;
		public String name;
		public String type;
		public int offset;
		public int length;
	}

	public class Datum2 {
		public String category;
		public String name;
		public String id;
		public List<From> category_list;
	}

	public class Datum4 {
		public String id;
		public From from;
		public String message;
		public boolean can_remove;
		public String created_time;
		public int like_count;
		public boolean user_likes;
		public List<MessageTag> message_tags;
	}

	public class Cursors {
		public String after;
		public String before;
	}

	public class Paging {
		public Cursors cursors;
		public String next;
	}

	public class Comments {
		public List<Datum4> data;
		public Paging paging;
	}

	public class Likes {
		public List<From> data;
		public Paging paging;
	}

	public static Datum fromJson(String json) {
		return new Gson().fromJson(json, Datum.class);
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
}