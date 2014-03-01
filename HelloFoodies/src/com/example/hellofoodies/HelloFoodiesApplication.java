package com.example.hellofoodies;

import android.app.Application;

import com.example.hellofoodies.parse.ParseComment;
import com.example.hellofoodies.parse.ParsePost;
import com.example.hellofoodies.parse.ParseRestaurant;
import com.google.android.gms.plus.model.people.Person;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;

public class HelloFoodiesApplication extends Application {

	private Person currentUser;

	@Override
	public void onCreate() {
		super.onCreate();
		
		ParseObject.registerSubclass(ParseRestaurant.class);
		ParseObject.registerSubclass(ParseComment.class);
		ParseObject.registerSubclass(ParsePost.class);

		// Add your initialization code here
		Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));

		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}

	public void setCurrentUser(Person user) {
		this.currentUser = user;
	}

	public Person getCurrentUser() {
		return this.currentUser;
	}

}
