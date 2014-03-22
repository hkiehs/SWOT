package com.example.hellofoodies.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.hellofoodies.R;
import com.example.hellofoodies.parse.ParseReview;
import com.example.hellofoodies.utility.NewPostFragment;

/*
 * NewMealActivity contains two fragments that handle
 * data entry and capturing a photo of a given meal.
 * The Activity manages the overall meal data.
 */
public class NewReviewActivity extends BaseClassActivity {

	private ParseReview parsePost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		parsePost = new ParseReview();
		super.setParseObject(parsePost);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		// Begin with main data entry view,
		// NewMealFragment
		setContentView(R.layout.activity_new_review);
		FragmentManager manager = getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

		if (fragment == null) {
			fragment = new NewPostFragment();
			manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}

	/*public ParsePost getCurrentPost() {
		return parsePost;
	}*/

}
