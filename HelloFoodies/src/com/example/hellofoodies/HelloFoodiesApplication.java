package com.example.hellofoodies;

import android.app.Application;

import com.example.hellofoodies.parse.ParseComment;
import com.example.hellofoodies.parse.ParsePost;
import com.example.hellofoodies.parse.ParseRestaurant;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;

public class HelloFoodiesApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		ParseObject.registerSubclass(ParseRestaurant.class);
		ParseObject.registerSubclass(ParseComment.class);
		ParseObject.registerSubclass(ParsePost.class);

		// Add your initialization code here
		Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));

		// ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);
	}

}
