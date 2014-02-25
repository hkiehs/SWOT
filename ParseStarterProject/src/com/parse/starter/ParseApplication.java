package com.parse.starter;

import model.ParsePost;
import model.ParseRestaurant;
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		ParseObject.registerSubclass(ParseRestaurant.class);
		ParseObject.registerSubclass(ParsePost.class);
		
		// Add your initialization code here
		Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));

//		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);
	}

}
