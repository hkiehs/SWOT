package com.example.hellofoodies.parse;

import android.util.Log;

import com.parse.ParseClassName;

@ParseClassName("Picture")
public class ParsePicture extends ParsePost {
	
	public static final String TABLE_NAME = "Picture";
 
    public ParsePicture() {
        // A default constructor is required.
    	Log.d("Picture", "Picture class");
    } 
    
//    public String getTag() {
//        return getString("tag");
//    }
// 
//    public void setTag(String tag) {
//        put("tag", tag);
//    }
}