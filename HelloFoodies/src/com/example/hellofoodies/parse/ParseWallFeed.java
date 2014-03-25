package com.example.hellofoodies.parse;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Wall")
public class ParseWallFeed extends ParseObject
{
	 public static final String parseObjectType = "ObjectType";
	 public static final String pictureObject = "PictureID";
	 public static final String reviewObject = "ReviewID";
	 public static final String TABLE_NAME = "Wall";
	 public String objectType;
	 
	 public ParseWallFeed() {
		// TODO Auto-generated constructor stub
			Log.d("wallfeed", "wallfeed class");
	}
	 
	 public void setParseObjectType(String parseObject) {
		 put(parseObjectType, parseObject);
		 objectType = parseObject;
	}
	 
	 public String getParseObjectType() {
		 return getString(parseObjectType);
	}
	 	 
	 public void setObject(ParsePost object)
	 {
		 if(objectType.contentEquals("Picture"))
				put(pictureObject, (ParsePicture)object);
		else if(objectType.contentEquals("Review"))
				put(reviewObject, (ParseReview)object);
	 }
	 
	 public String getObject()
	 {
		 if(objectType.contentEquals("Picture"))
				return getString(pictureObject);
			 
		return getString(reviewObject);
	 }
}
