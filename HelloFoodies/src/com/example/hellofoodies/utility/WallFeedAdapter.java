package com.example.hellofoodies.utility;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hellofoodies.R;
import com.example.hellofoodies.parse.ParseComment;
import com.example.hellofoodies.parse.ParsePicture;
import com.example.hellofoodies.parse.ParsePost;
import com.example.hellofoodies.parse.ParseReview;
import com.example.hellofoodies.parse.ParseWallFeed;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class WallFeedAdapter extends ParseQueryAdapter<ParseObject> {
	private static final String LOG_TAG = "WallFeedAdapter";
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
	public View getItemView(final ParseObject parsePost, View v, ViewGroup parent) {
		super.getItemView(parsePost, v, parent);

		ParseWallFeed wallPost = (ParseWallFeed) parsePost;
		if (v == null) {
			if (wallPost.getParseObjectType().contentEquals("Picture")) {
				v = View.inflate(getContext(), R.layout.picture_wall_feed, null);
				picture = (ParsePicture) wallPost.getParseObject("PictureID");
				profileImage = (ParseImageView) v.findViewById(R.id.picture_wall_feed_photo);
				// photoFile = wallPost.getParseFile(ParsePicture.PHOTO);
				photoFile = picture.getPhotoFile();

				username = (TextView) v.findViewById(R.id.picture_wall_feed_username);
				textViewPost = (TextView) v.findViewById(R.id.picture_wall_feed_post);
				buttonLike = (Button) v.findViewById(R.id.picture_wall_feed_tag);

			} else {
				v = View.inflate(getContext(), R.layout.review_wall_feed, null);
				review = (ParseReview) wallPost.getParseObject("ReviewID");
				profileImage = (ParseImageView) v.findViewById(R.id.imageViewProfile);
				// photoFile = wallPost.getParseFile(ParseReview.PHOTO);
				photoFile = review.getPhotoFile();

				username = (TextView) v.findViewById(R.id.textViewUsername);
				textViewPost = (TextView) v.findViewById(R.id.textViewPost);
				buttonLike = (Button) v.findViewById(R.id.buttonLike);
			}
		}
		ParseReview review = (ParseReview) parsePost;

		// TODO: MUNEEB REMOVED
		// //ParsePicture review = (ParsePicture)parsePost;
		// //profileImage = (ParseImageView)
		// v.findViewById(R.id.imageViewProfile);
		// profileImage.setPlaceholder(context.getResources().getDrawable(R.drawable.photo));
		// ParseFile photoFile = parsePost.getParseFile(ParseReview.PHOTO);
		// //ParseFile photoFile = parsePost.getParseFile(ParseReview.PHOTO);
		// if (photoFile != null) {
		// profileImage.setParseFile(photoFile);
		// profileImage.loadInBackground(new GetDataCallback() {
		// @Override
		// public void done(byte[] data, ParseException e) {
		// // nothing to do
		// }
		// });
		// }
		// TextView username = (TextView) v.findViewById(R.id.textViewUsername);
		// TextView textViewPost = (TextView) v.findViewById(R.id.textViewPost);
		// final Button buttonLike = (Button) v.findViewById(R.id.buttonLike);
		// username.setText(review.getFromName());
		// textViewPost.setText(review.getMessage());
		// buttonLike.setText(review.getLikes() + "");
		// buttonLike.setTag(parsePost);

		buttonLike.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ParsePost parsePost = (ParsePost) buttonLike.getTag();
				Log.d(LOG_TAG, "ObjectId[" + parsePost.getObjectId() + "]");

				ParseQuery<ParsePost> query = ParseQuery.getQuery("Post");
				query.getInBackground(parsePost.getObjectId(), new GetCallback<ParsePost>() {
					public void done(ParsePost parsePost, ParseException e) {
						if (e == null) {
							ParseACL postACL = new ParseACL(ParseUser.getCurrentUser());
							postACL.setPublicReadAccess(true);
							postACL.setPublicWriteAccess(true);
							parsePost.setACL(postACL);

							parsePost.increment(ParseReview.LIKE);
							parsePost.saveInBackground(new SaveCallback() {
								@Override
								public void done(ParseException e) {
									if (e == null) {
										Log.d(LOG_TAG, "Like updated");
									} else {
										e.printStackTrace();
									}
								}
							});
						}
					}
				});
			}
		});

		Button comment = (Button) v.findViewById(R.id.buttonComment);
		comment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ParseComment parseComment = new ParseComment();

				parseComment.put("postId", ParseObject.createWithoutData("POST", parsePost.getObjectId()));
				parseComment.put("comment", "Jhonny Brovo... <)");
				parseComment.saveInBackground(new SaveCallback() {
					@Override
					public void done(ParseException e) {
						if (e == null) {
							Log.d(LOG_TAG, "Comment saved");
						} else {
							e.printStackTrace();
						}

					}
				});

			}
		});

		if (wallPost.getParseObjectType().contentEquals("Picture")) {
			username.setText("Salman");
			textViewPost.setText(picture.getTitle());
			buttonLike.setText(picture.getRating());
		}

		else {
			username.setText("Salman");
			textViewPost.setText(review.getTitle());
			buttonLike.setText(review.getRating() + "");
		}
		return v;
	}

}