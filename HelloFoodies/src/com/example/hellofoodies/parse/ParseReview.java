package com.example.hellofoodies.parse;

import com.parse.ParseClassName;

@ParseClassName("Review")
public class ParseReview extends ParsePost {
    public static final String TABLE_NAME = "Review";
    public static final String REVIEW = "restaurantReview";
    //	public static final String MESSAGE = "message";
//	public static final String TYPE = "type";
    public static final String LIKE = "like";
//	public static final String PICTURE = "picture";
//	public static final String LINK = "link";
//	public static final String ICON = "icon";
//	public static final String FROM_ID = "fromId";
//	public static final String FROM_NAME = "fromName";
//	public static final String RESTAURANT_ID = "restaurantId";
//	public static final String PHOTO = "photo";
//	public static final String CREATED_AT = "createdAt";

    public ParseReview() {
        // A default constructor is required.
    }


//	public String getId() {
//		return getString(ID);
//	}
//
//	public String getMessage() {
//		return getString(MESSAGE);
//	}
//
//	public String getType() {
//		return getString(TYPE);
//	}
//
//	public int getLikes() {
//		return getInt(LIKE);
//	}
//
//	public String getPicture() {
//		return getString(PICTURE);
//	}
//
//	public String getLink() {
//		return getString(LINK);
//	}
//
//	public String getIcon() {
//		return getString(ICON);
//	}
//
//	public String getFromName() {
//		return getString(FROM_NAME);
//	}
//
//	public String getFromId() {
//		return getString(FROM_ID);
//	}
//
//	public String getRestaurantId() {
//		return getString(RESTAURANT_ID);
//	}
//
//	/*public ParseFile getPhotoFile() {
//		return getParseFile(PHOTO);
//	}*/
//
//	public void setRestaurantId(String restaurantId) {
//		put(RESTAURANT_ID, ParseObject.createWithoutData(ParseRestaurant.TABLE_NAME, restaurantId));
//	}
//
//	public void setMessage(String message) {
//		put(MESSAGE, message);
//	}
//
//	public void setType(String type) {
//		put(TYPE, type);
//	}
//
//	public void setPicture(String picture) {
//		put(PICTURE, picture);
//	}
//
//	public void setFromName(String fromName) {
//		put(FROM_NAME, fromName);
//	}
//
//	public void setFromId(String fromId) {
//		put(FROM_ID, fromId);
//	}
//
//	public void setId(String id) {
//		put(ID, id);
//	}
//
//	public void setLink(String link) {
//		put(LINK, link);
//	}
//
//	public void setLike(Boolean like) {
//		if (like != null) {
//			if (like)
//				this.increment(LIKE);
//			else
//				this.increment(LIKE, -1);
//		}
//	}

	/*
     * public void setPhotoFile(ParseFile photo) { put(PHOTO, photo); }
	 */
}
