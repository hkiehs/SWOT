package model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Comment")
public class ParseComment extends ParseObject {

	public static final String FACEBOOK_ID = "facebookID";
	public static final String USER_NAME = "userName";
	public static final String COMMENT = "comment";
	public static final String POST_ID = "postId";
	public static final String LIKE = "like";

	public ParseComment() {
		// A default constructor is required.
	}

	public int getLikes() {
		return getInt(LIKE);
	}

	public String getComment() {
		return getString(COMMENT);
	}

	public String getRestaurantId() {
		return getString(POST_ID);
	}

	public String getUserName() {
		return getString(USER_NAME);
	}

	public String getFacebookId() {
		return getString(FACEBOOK_ID);
	}

	public static class Builder {
		// Required parameters
		private final String postId;
		private final String comment;
		private final String username;
		private final String facebookId;
		// Optional parameters - initialized to default values
		private Boolean like = null;

		public Builder(String postId, String comment, String username, String facebookId) {
			this.postId = postId;
			this.comment = comment;
			this.username = username;
			this.facebookId = facebookId;
		}

		public Builder like(Boolean val) {
			like = val;
			return this;
		}

		public ParseComment build() {
			return new ParseComment(this);
		}
	}

	private ParseComment(Builder builder) {
		put(POST_ID, ParseObject.createWithoutData("POST", builder.postId));
		put(COMMENT, builder.comment);
		put(USER_NAME, builder.username);
		put(FACEBOOK_ID, builder.facebookId);

		if (builder.like != null) {
			if (builder.like)
				this.increment(LIKE);
			else
				this.increment(LIKE, -1);
		}
	}

}
