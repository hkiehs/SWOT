package com.example.hellofoodies.parse;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Post")
public class ParsePost extends ParseObject {
	public static final String TABLE_NAME = "Post";

	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String TYPE = "type";
	public static final String LIKE = "like";
	public static final String PICTURE = "picture";
	public static final String LINK = "link";
	public static final String ICON = "icon";
	public static final String FROM_ID = "fromId";
	public static final String FROM_NAME = "fromName";
	public static final String RESTAURANT_ID = "restaurantId";
	public static final String PHOTO = "photo";
	public static final String CREATED_AT = "createdAt";

	public ParsePost() {
		// A default constructor is required.
	}

	public String getId() {
		return getString(ID);
	}

	public String getMessage() {
		return getString(MESSAGE);
	}

	public String getType() {
		return getString(TYPE);
	}

	public int getLikes() {
		return getInt(LIKE);
	}

	public String getPicture() {
		return getString(PICTURE);
	}

	public String getLink() {
		return getString(LINK);
	}

	public String getIcon() {
		return getString(ICON);
	}

	public String getFromName() {
		return getString(FROM_NAME);
	}

	public String getFromId() {
		return getString(FROM_ID);
	}

	public String getRestaurantId() {
		return getString(RESTAURANT_ID);
	}

	public ParseFile getPhotoFile() {
		return getParseFile(PHOTO);
	}

	public static class Builder {
		// Required parameters
		private String restaurantId;
		private String message;
		private String type;
		private String picture;
		private String fromName;
		private String fromId;
		// Optional parameters - initialized to default values
		private String id = null;
		private String link = null;
		private Boolean like = null;
		private ParseFile photo = null;

		public Builder(String username, String userId, String message, String type, String restaurantId, String picture) {
			this.message = message;
			this.type = type;
			this.picture = picture;
			this.fromName = username;
			this.fromId = userId;
			this.restaurantId = restaurantId;
		}

		public Builder like(boolean value) {
			like = value;
			return this;
		}

		public Builder link(String facebookLink) {
			link = facebookLink;
			return this;
		}

		public Builder facebookId(String facebookId) {
			id = facebookId;
			return this;
		}

		public Builder photo(ParseFile photo) {
			this.photo = photo;
			return this;
		}

		public ParsePost build() {
			return new ParsePost(this);
		}
	}

	private ParsePost(Builder builder) {
		put(RESTAURANT_ID, ParseObject.createWithoutData("Restaurant", builder.restaurantId));
		put(FROM_NAME, builder.fromName);
		put(FROM_ID, builder.fromId);
		put(ID, builder.id);
		put(MESSAGE, builder.message);
		put(TYPE, builder.type);
		put(PICTURE, builder.picture);
		put(LINK, builder.link);

		if (builder.photo != null)
			put(PHOTO, builder.photo);

		if (builder.like != null) {
			if (builder.like)
				this.increment(LIKE);
			else
				this.increment(LIKE, -1);
		}
	}
}
