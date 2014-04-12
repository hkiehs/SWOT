package com.example.hellofoodies.activity;

import android.app.Activity;
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
import com.example.hellofoodies.parse.ParsePicture;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class NewPictureActivity extends BaseClassActivity {

    private ParsePicture picture;
    private ImageButton photoButton;
    private Button saveButton;
    private Button cancelButton;
    private TextView pictureName;
    private Spinner pictureTag;
    private ParseImageView picturePreview;
    private Uri imagePath;
    private ParseFile photoFile;
    /**
     * The Constant PICK_IMAGE.
     */
    private static final int PICK_IMAGE = 0;


//    private ParsePost parseObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        Log.d("Picture", "Picture class");
        // Begin with main data entry view,
        // NewMealFragment
        setContentView(R.layout.activity_new_picture);

        picture = new ParsePicture();
        super.setParseObject(picture);

    	/* FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.pictureFragmentContainer);
 
        if (fragment == null) {
            fragment = new NewPictureFragment();
            manager.beginTransaction().add(R.id.pictureFragmentContainer, fragment)
                    .commit();
        }*/


        pictureName = ((EditText) findViewById(R.id.picture_name));
        pictureTag = ((Spinner) findViewById(R.id.tags_spinner));
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(this, R.array.tags_array,
                        android.R.layout.simple_spinner_dropdown_item);
        pictureTag.setAdapter(spinnerAdapter);

        photoButton = ((ImageButton) findViewById(R.id.picture_photo_button));
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pictureName.getWindowToken(), 0);
                startCamera();
            }
        });

        saveButton = ((Button) findViewById(R.id.picture_save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ParsePicture picture = (ParsePicture) getParseObject();

                // When the user clicks "Save," upload the meal to Parse
                // Add data to the meal object:
                picture.setTitle(pictureName.getText().toString());

                // Associate the meal with the current user
                picture.setAuthor(ParseUser.getCurrentUser());

                // Add the rating
                picture.setRating(pictureTag.getSelectedItem().toString());

                //Add picture
                picture.setPhotoFile(photoFile);

                picture.saveObjectInBackground(picture, "Picture");
            }
        });

        cancelButton = ((Button) findViewById(R.id.picture_cancel_button));
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        // Until the user has taken a photo, hide the preview
        picturePreview = (ParseImageView) findViewById(R.id.picture_preview_image);
        picturePreview.setVisibility(View.INVISIBLE);

    }

    public void startCamera() {
        Intent camera = new Intent();
        camera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //camera.putExtra("crop", "true");

        // File f=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        imagePath = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myFile.jpg"));
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
        //ParseFile photoFile = ((ParsePicture) getParseObject()).getPhotoFile();
        if (photoFile != null) {
            picturePreview.setParseFile(photoFile);
            picturePreview.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    picturePreview.setVisibility(View.VISIBLE);
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
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {

                InputStream is = null;
                try {
                    is = this.getContentResolver().openInputStream(imagePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bmp = BitmapFactory.decodeStream(is);
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