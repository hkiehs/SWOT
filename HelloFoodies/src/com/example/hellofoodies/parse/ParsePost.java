package com.example.hellofoodies.parse;

import android.util.Log;
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
	public static final String PHOTO = "photo";
	private ParsePost parsePost;
	private String postType;
 
    public ParsePost() {
        // A default constructor is required.
    	Log.d("Post", "Post class");
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
    
    public ParseUser getRestaurant() {
        return getParseUser("restaurantID");
    }
    
    public void setRating(String title) {
        put("rating", title);
    }
 
    public String getRating() {
        return getString("rating");
    }
    
 
    public void setRestaurant(String resID) {
        put("restaurantID", resID);
    }
 
    public ParseFile getPhotoFile() {
        return getParseFile(PHOTO);
    }
 
    public void setPhotoFile(ParseFile file) {
        put(PHOTO, file);
    }
    
    public void saveObjectInBackground(ParsePost post, String objectType)
    {
    	this.parsePost = post;
    	this.postType = objectType;
    	post.saveInBackground(new SaveCallback() {
			
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
					//comment
				}
				
			}
		});
    }
    
    public void saveToWall(ParsePost post, String objectType)
    {   	
    	ParseWallFeed wallFeed = new ParseWallFeed();
    	wallFeed.setParseObjectType(objectType);
    	wallFeed.setObject(post);
    	
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