package com.example.hellofoodies.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hellofoodies.R;
import com.example.hellofoodies.parse.ParseReview;
import com.example.hellofoodies.utility.CameraFragment;
import com.example.hellofoodies.utility.NewPostFragment;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;

/*
 * NewMealActivity contains two fragments that handle
 * data entry and capturing a photo of a given meal.
 * The Activity manages the overall meal data.
 */
public class NewReviewActivity extends BaseClassActivity {

	private ParseReview review;
	private ImageButton photoButton;
	private Button saveButton;
	private Button cancelButton;
	private TextView reviewName;
	private Spinner reviewRating;
	private ParseImageView reviewPicturePreview;
	private Uri imagePath;
	private ParseFile photoFile;
	  /** The Constant PICK_IMAGE. */
    private static final int PICK_IMAGE = 0;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		Log.d("Review", "Review class");
		// Begin with main data entry view,
		// NewMealFragment
		setContentView(R.layout.activity_new_review);
		review = new ParseReview();
		super.setParseObject(review);
		
		
//		FragmentManager manager = getFragmentManager();
//		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
//
//		if (fragment == null) {
//			fragment = new NewPostFragment();
//			manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
//		}
		
		
		reviewName = ((EditText) findViewById(R.id.meal_name));

		// The mealRating spinner lets people assign favorites of meals they've
		// eaten.
		// Meals with 4 or 5 ratings will appear in the Favorites view.
		reviewRating = ((Spinner) findViewById(R.id.rating_spinner));
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.ratings_array,
				android.R.layout.simple_spinner_dropdown_item);
		reviewRating.setAdapter(spinnerAdapter);

		photoButton = ((ImageButton) findViewById(R.id.photo_button));
		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(reviewName.getWindowToken(), 0);
				startCamera();
			}
		});

		saveButton = ((Button) findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParseReview review = (ParseReview) getParseObject();
//				parsePost.setFromName("Muneeb");
//				parsePost.setFromId("objectId");
//				parsePost.setMessage("Wow, Amazing Food");
//				parsePost.setType("Review");
//				parsePost.setRestaurantId("restaurantId");
				
				// Add data to the meal object:
				review.setTitle(reviewName.getText().toString());

				// Associate the meal with the current user
				review.setAuthor(ParseUser.getCurrentUser());

				// Add the rating
				review.setRating(reviewRating.getSelectedItem().toString());	
				review.setRestaurant("restaurantId");
				review.setPhotoFile(photoFile);
				review.saveObjectInBackground(review, "Review");

				// If the user added a photo, that data will be
				// added in the CameraFragment

				// Save the post and return
//				parsePost.saveInBackground(new SaveCallback() {
//
//					@Override
//					public void done(ParseException e) {
//						if (e == null) {
//							getActivity().setResult(Activity.RESULT_OK);
//							getActivity().finish();
//						} else {
//							Toast.makeText(getActivity().getApplicationContext(), "Error saving: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//						}
//					}
//
//				});

			}
		});

		cancelButton = ((Button) findViewById(R.id.cancel_button));
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(Activity.RESULT_CANCELED);
				finish();
			}
		});

		// Until the user has taken a photo, hide the preview
		reviewPicturePreview = (ParseImageView) findViewById(R.id.meal_preview_image);
		reviewPicturePreview.setVisibility(View.INVISIBLE);
	}

	/*
	 * All data entry about a Meal object is managed from the NewMealActivity.
	 * When the user wants to add a photo, we'll start up a custom
	 * CameraFragment that will let them take the photo and save it to the Meal
	 * object owned by the NewMealActivity. Create a new CameraFragment, swap
	 * the contents of the fragmentContainer (see activity_new_meal.xml), then
	 * add the NewMealFragment to the back stack so we can return to it when the
	 * camera is finished.
	 */
	public void startCamera() {
		 Intent camera=new Intent();
         camera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
         //camera.putExtra("crop", "true");

        // File f=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

         imagePath = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"myFile.jpg"));
         camera.putExtra(MediaStore.EXTRA_OUTPUT, imagePath);
         startActivityForResult(camera, PICK_IMAGE);
	}

	/*
	 * On resume, check and see if a meal photo has been set from the
	 * CameraFragment. If it has, load the image in this fragment and make the
	 * preview image visible.
	 */
	@Override
	public void onResume() {
		super.onResume();
		//ParseFile photoFile = ((ParseReview) getParseObject()).getPhotoFile();
		if (photoFile != null) {
			reviewPicturePreview.setParseFile(photoFile);
			reviewPicturePreview.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					reviewPicturePreview.setVisibility(View.VISIBLE);
				}
			});
		}
	}
	
	 /* (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode==RESULT_OK )
        {
            if(requestCode == PICK_IMAGE) {

                InputStream is=null;
                try {
                    is = this.getContentResolver().openInputStream(imagePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bmp=BitmapFactory.decodeStream(is);
                saveScaledPhoto(bmp);
            }

        }
    }
    
    
    /*
	 * ParseQueryAdapter loads ParseFiles into a ParseImageView at whatever size
	 * they are saved. Since we never need a full-size image in our app, we'll
	 * save a scaled one right away.
	 */
	private void saveScaledPhoto(Bitmap image) {

		// Resize photo from camera byte array
		Bitmap imageScaled = Bitmap.createScaledBitmap(image, 600, 600
				* image.getHeight() / image.getWidth(), false);

		// Override Android default landscape orientation and save portrait
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Bitmap rotatedScaledMealImage = Bitmap.createBitmap(imageScaled, 0,
				0, imageScaled.getWidth(), imageScaled.getHeight(),
				matrix, true);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		rotatedScaledMealImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

		byte[] scaledData = bos.toByteArray();

		// Save the scaled image to Parse
		photoFile = new ParseFile("meal_photo.jpg", scaledData);
	}

}
