package com.example.hellofoodies;

import android.app.Application;

import com.example.hellofoodies.parse.ParseComment;
import com.example.hellofoodies.parse.ParsePicture;
import com.example.hellofoodies.parse.ParseRestaurant;
import com.example.hellofoodies.parse.ParseReview;
import com.example.hellofoodies.parse.ParseWallFeed;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class HelloFoodiesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // register parse
        ParseObject.registerSubclass(ParseRestaurant.class);
        ParseObject.registerSubclass(ParseComment.class);
        ParseObject.registerSubclass(ParseReview.class);
        ParseObject.registerSubclass(ParsePicture.class);
        ParseObject.registerSubclass(ParseWallFeed.class);

        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));
        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        // initialize timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.HollowTree {
        @Override
        public void i(String message, Object... args) {
            // TODO e.g., Crashlytics.log(String.format(message, args));
            System.out.println(String.format(message, args));
        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            i(message, args); // Just add to the log.
        }

        @Override
        public void e(String message, Object... args) {
            i("ERROR: " + message, args); // Just add to the log.
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            e(message, args);
            // TODO e.g., Crashlytics.logException(t);
            System.out.println(t);
        }
    }

}
