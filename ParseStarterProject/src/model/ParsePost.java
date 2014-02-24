package model;

import utility.Datum;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Post")
public class ParsePost extends ParseObject {

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

	public String id;
	public String message;
	public String type;
	public int likes;
	public String picture;
	public String link;
	public String icon;
	public String fromName;
	public String fromId;

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

	public void setRestaurantId(String restaurantId) {
		if (restaurantId != null)
			put(RESTAURANT_ID, ParseObject.createWithoutData("Restaurant", restaurantId));
	}

	public void setFromName(String fromName) {
		if (fromName != null)
			put(FROM_NAME, fromName);
	}

	public void setFromId(String fromId) {
		if (fromId != null)
			put(FROM_ID, fromId);
	}

	public void setId(String id) {
		if (id != null)
			put(ID, id);
	}

	public void setMessage(String message) {
		if (message != null)
			put(MESSAGE, message);
	}

	public void setType(String type) {
		if (type != null)
			put(TYPE, type);
	}

	public void setLikes(int likes) {
		put(LIKE, likes);
	}

	public void setPicture(String picture) {
		if (picture != null)
			put(PICTURE, picture);
	}

	public void setLink(String link) {
		if (link != null)
			put(LINK, link);
	}

	public void setIcon(String icon) {
		if (icon != null)
			put(LINK, icon);
	}
	
	public static ParsePost createParseObject(Datum datum ,String objectId) {
		ParsePost parsePost = new ParsePost();
		parsePost.setId(datum.id);
		parsePost.setMessage(datum.message);
		parsePost.setType(datum.type);
		parsePost.setLikes(datum.likes.data.size());
		parsePost.setPicture(datum.picture);
		parsePost.setLink(datum.link);
		parsePost.setIcon(datum.icon);
		parsePost.setFromId(datum.from.id);
		parsePost.setFromName(datum.from.name);
		parsePost.setRestaurantId(objectId);
		return parsePost;
	}

}
