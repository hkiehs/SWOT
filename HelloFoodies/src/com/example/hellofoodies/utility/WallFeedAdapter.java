package com.example.hellofoodies.utility;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hellofoodies.R;
import com.example.hellofoodies.parse.ParsePicture;
import com.example.hellofoodies.parse.ParseReview;
import com.example.hellofoodies.parse.ParseWallFeed;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class WallFeedAdapter extends ParseQueryAdapter<ParseObject> {
	private Context context;	
	private ParsePicture picture;
	private ParseReview review;
	private ParseImageView profileImage;
	private TextView username;
	private TextView textViewPost;
	private Button buttonLike;
	private ParseFile photoFile;
	

	public WallFeedAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery<ParseObject> create() {
				
				ParseQuery<ParseObject> wallQuery = ParseQuery.getQuery(ParseWallFeed.TABLE_NAME);
				 
				// Retrieve the most recent ones
				wallQuery.orderByDescending("createdAt");
				 
				// Only retrieve the last ten
				wallQuery.setLimit(10);
				 
				// Include the post data with each comment
				wallQuery.include("ReviewID");
				wallQuery.include("PictureID");
				
				return wallQuery;
				
				
			}
		});
		this.context = context;
	}

	@Override
	public View getItemView(ParseObject parsePost, View v, ViewGroup parent) {
		
		ParseWallFeed wallPost = (ParseWallFeed) parsePost;
			
		if (v == null) {
			if(wallPost.getParseObjectType().contentEquals("Picture"))
			{
				v = View.inflate(getContext(), R.layout.picture_wall_feed, null);
				picture = (ParsePicture) wallPost.getParseObject("PictureID");
				profileImage = (ParseImageView) v.findViewById(R.id.picture_wall_feed_photo);
				//photoFile = wallPost.getParseFile(ParsePicture.PHOTO);
				photoFile = picture.getPhotoFile();

				username = (TextView) v.findViewById(R.id.picture_wall_feed_username);
				textViewPost = (TextView) v.findViewById(R.id.picture_wall_feed_post);
				buttonLike = (Button) v.findViewById(R.id.picture_wall_feed_tag);

			}
			else
			{
				v = View.inflate(getContext(), R.layout.review_wall_feed, null);
				review = (ParseReview) wallPost.getParseObject("ReviewID");
				profileImage = (ParseImageView) v.findViewById(R.id.imageViewProfile);
				//photoFile = wallPost.getParseFile(ParseReview.PHOTO);
				photoFile = review.getPhotoFile();

				username = (TextView) v.findViewById(R.id.textViewUsername);
				textViewPost = (TextView) v.findViewById(R.id.textViewPost);
				buttonLike = (Button) v.findViewById(R.id.buttonLike);
			}
		}
		super.getItemView(parsePost, v, parent);		
		
		
		//ParsePicture review = (ParsePicture)parsePost;

		//profileImage = (ParseImageView) v.findViewById(R.id.imageViewProfile);
		profileImage.setPlaceholder(context.getResources().getDrawable(R.drawable.photo));
		//ParseFile photoFile = parsePost.getParseFile(ParseReview.PHOTO);
		if (photoFile != null) {
			profileImage.setParseFile(photoFile);
			profileImage.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					// nothing to do
				}
			});
		}

		if(wallPost.getParseObjectType().contentEquals("Picture"))
		{
			username.setText("Salman");
			textViewPost.setText(picture.getTitle());
			buttonLike.setText(picture.getRating());
		}
		
		else
		{
			username.setText("Salman");
			textViewPost.setText(review.getTitle());
			buttonLike.setText(review.getRating() + "");
		}
		
		return v;
	}

}