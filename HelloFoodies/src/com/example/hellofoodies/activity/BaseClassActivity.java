package com.example.hellofoodies.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.hellofoodies.R;
import com.example.hellofoodies.parse.ParsePost;
import com.parse.ParseObject;

/*
 * NewMealActivity contains two fragments that handle
 * data entry and capturing a photo of a given meal.
 * The Activity manages the overall meal data.
 */
public class BaseClassActivity extends Activity {

	//private ParsePost parsePost;
	private static Context context;
	
	private ParsePost parseObject;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_new_review);		
		context = getApplicationContext();
	}
	
	public static Context getContext()	{
		return context;		
	}
		
	public void setParseObject(ParsePost parseObj) {
		parseObject = parseObj;
	}
	
	public ParseObject getParseObject() {
		return parseObject;
	}
	
	

}
