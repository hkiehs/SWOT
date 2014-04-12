package com.example.hellofoodies.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Restaurant")
public class ParseRestaurant extends ParseObject {
    public static final String TABLE_NAME = "Restaurant";

    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String TYPE = "type";
    public static final String TIMING = "timing";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String RATING = "rating";
    public static final String REVIEW = "review";
    public static final String LIKE = "like";
    public static final String RANGE = "range";
    public static final String HIGHLIGHT = "highight";

    public ParseRestaurant() {
        // A default constructor is required.
    }

    public String getName() {
        return getString(NAME);
    }

    public String getAddress() {
        return getString(ADDRESS);
    }

    public String getType() {
        return getString(TYPE);
    }

    public String getTiming() {
        return getString(TIMING);
    }

    public String getPhoneNumber() {
        return getString(PHONE_NUMBER);
    }

    public double getLatitude() {
        return getDouble(LATITUDE);
    }

    public double getLongitude() {
        return getDouble(LONGITUDE);
    }

    public double getRating() {
        return getDouble(RATING);
    }

    public int getReviews() {
        return getInt(REVIEW);
    }

    public int getLikes() {
        return getInt(LIKE);
    }

    public int getRange() {
        return getInt(RANGE);
    }

    public String getHighights() {
        return getString(HIGHLIGHT);
    }

    public void setHighights(String highights) {
        if (highights != null)
            put(HIGHLIGHT, highights);
    }

    public static class Builder {
        // Required parameters
        public String name;
        public String address;
        public String type;
        // Optional parameters - initialized to default values
        public String timing;
        public String phoneNumber;
        public double latitude;
        public double longitude;
        public double rating;
        public int reviews;
        public Boolean like;
        public int range;
        public String highights;

        public Builder(String name, String address, String type) {
            this.name = name;
            this.address = address;
            this.type = type;
        }

        public Builder timing(String start, String end) {
            this.timing = start + " - " + end;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder reviews(int reviews) {
            this.reviews = reviews;
            return this;
        }

        public Builder likes(boolean value) {
            this.like = value;
            return this;
        }

        public Builder range(int range) {
            this.range = range;
            return this;
        }

        public Builder highights(String highights) {
            this.highights = highights;
            return this;
        }

        public ParseRestaurant build() {
            return new ParseRestaurant(this);
        }
    }

    private ParseRestaurant(Builder builder) {
        put(NAME, builder.name);
        put(ADDRESS, builder.address);
        put(TYPE, builder.type);
        put(TIMING, builder.timing);
        put(PHONE_NUMBER, builder.phoneNumber);
        put(LATITUDE, builder.latitude);
        put(LONGITUDE, builder.longitude);
        put(RATING, builder.rating);
        put(REVIEW, builder.reviews);
        put(RANGE, builder.range);
        if (builder.like != null) {
            if (builder.like)
                this.increment(LIKE);
            else
                this.increment(LIKE, -1);
        }
    }
}
