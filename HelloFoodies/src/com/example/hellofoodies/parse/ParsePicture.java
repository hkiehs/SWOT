package com.example.hellofoodies.parse;

import com.parse.ParseClassName;

@ParseClassName("Picture")
public class ParsePicture extends ParsePost {
 
    public ParsePicture() {
        // A default constructor is required.
    } 
    
    public String getTag() {
        return getString("tag");
    }
 
    public void setTag(String tag) {
        put("tag", tag);
    }
}