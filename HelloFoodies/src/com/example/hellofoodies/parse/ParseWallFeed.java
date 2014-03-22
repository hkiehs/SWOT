package com.example.hellofoodies.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Wall")
public class ParseWallFeed extends ParseObject
{
	 public static final String parseObjectType = "objectType";
	 public static final String parseObject = "postID";
	 public static final String TABLE_NAME = "Wall";
	 
	 public ParseWallFeed() {
		// TODO Auto-generated constructor stub
	}
	 
	 public void setParseObjectType(String parseObject) {
		 put(parseObjectType, parseObject);
	}
	 
	 public String getParseObjectType() {
		 return getString(parseObjectType);
	}
	 
	 @Override
	public void setObjectId(String newObjectId) {
		// TODO Auto-generated method stub
		put(parseObject, newObjectId);
	}
}
