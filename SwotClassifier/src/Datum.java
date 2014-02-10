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
	public String restaurantName;

	public class From {
		public String name;
		public String id;
	}

	public class From2 {
		public String name;
		public String id;
	}

	public class CategoryList {
		public String id;
		public String name;
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
		public List<CategoryList> category_list;
	}

	public class Datum3 {
		public String id;
		public String name;
	}

	public class Datum4 {
		public String id;
		public From2 from;
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

	public class Cursors2 {
		public String after;
		public String before;
	}

	public class Paging {
		public Cursors cursors;
		public String next;
	}

	public class Paging2 {
		public Cursors2 cursors;
		public String next;
	}

	public class Comments {
		public List<Datum4> data;
		public Paging2 paging;
	}

	public class Likes {
		public List<Datum3> data;
		public Paging paging;
	}

	public static Datum fromJson(String json) {
		return new Gson().fromJson(json, Datum.class);
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
}

// public class Datum {
// public String id;
// public From from;
// public String message;
// public String type;
// public String created_time;
// public String updated_time;
// public String link;
// public String restaurantName;
//
// public class From {
// public String name;
// public String id;
// }
//
// public static Datum fromJson(String json) {
// return new Gson().fromJson(json, Datum.class);
// }
//
// public String toJson() {
// return new Gson().toJson(this);
// }
// }