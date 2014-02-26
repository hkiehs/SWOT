package model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Comment")
public class ParseComment extends ParseObject {

	public static final String COMMENT = "comment";
	public static final String LIKE = "like";
	public static final String POST_ID = "postId";

	private ParseComment() {
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

	public static class Builder {
		// Required parameters
		private final String postId;
		private final String comment;
		// Optional parameters - initialized to default values
		private Boolean like = null;

		public Builder(String postId, String comment) {
			this.postId = postId;
			this.comment = comment;
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

		if (builder.like != null) {
			if (builder.like)
				this.increment(LIKE);
			else
				this.increment(LIKE, -1);
		}
	}

}
