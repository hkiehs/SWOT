package com.example.hellofoodies.parse;

import android.widget.Toast;

import com.example.hellofoodies.activity.BaseClassActivity;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ParsePost extends ParseObject {
	
	public static final String TABLE_NAME = "Post";
	private SaveCallback callBack;
	private ParsePost parsePost;
	private String postType;
 
    public ParsePost() {
        // A default constructor is required.
    	callBack = new SaveCallback() {
			
    		@Override
			public void done(ParseException e) {
				if (e == null) {
					Toast.makeText(
							BaseClassActivity.getContext(),
							"Successfull ",
							Toast.LENGTH_SHORT).show();
							saveToWall(parsePost, postType);
				} else {
					Toast.makeText(
							BaseClassActivity.getContext(),
							"Error saving: " + e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
				
			}
		};
    }
 
    public String getTitle() {
        return getString("title");
    }
 
    public void setTitle(String title) {
        put("title", title);
    }
 
    public ParseUser getAuthor() {
        return getParseUser("author");
    }
 
    public void setAuthor(ParseUser user) {
        put("author", user);
    }
 
    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }
 
    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }
    
    public void saveObjectInBackground(ParsePost post, String objectType)
    {
    	this.parsePost = post;
    	this.postType = objectType;
    	post.saveInBackground(callBack);
    }
    
    public void saveToWall(ParsePost post, String objectType)
    {   	
    	ParseWallFeed wallFeed = new ParseWallFeed();
    	wallFeed.setParseObjectType(objectType);
    	wallFeed.setObjectId(post.getObjectId());
    	
    	wallFeed.saveInBackground(new SaveCallback() {
    		
    		@Override
			public void done(ParseException e) {		
    		if (e == null) {
				Toast.makeText(
						BaseClassActivity.getContext(),
						"Successfull ",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(
						BaseClassActivity.getContext(),
						"Error saving: " + e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
    		
    	   }
			
		});
    	
    }
}