package com.example.hellofoodies.utility;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hellofoodies.R;
import com.example.hellofoodies.parse.ParsePost;
import com.example.hellofoodies.parse.ParseReview;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class WallFeedAdapter extends ParseQueryAdapter<ParsePost> {
	private static final String LOG_TAG = "WallFeedAdapter";
	private Context context;

	public WallFeedAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<ParsePost>() {
			public ParseQuery<ParsePost> create() {
				ParseQuery<ParsePost> query = ParseQuery.getQuery(ParsePost.TABLE_NAME);
				query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
				query.orderByDescending(ParseReview.CREATED_AT);
				return query;
			}
		});
		this.context = context;
	}

	@Override
	public View getItemView(ParsePost parsePost, View v, ViewGroup parent) {
		super.getItemView(parsePost, v, parent);
		if (v == null) {
			v = View.inflate(getContext(), R.layout.review_wall_feed, null);
		}

		ParseReview review = (ParseReview) parsePost;

		ParseImageView profileImage = (ParseImageView) v.findViewById(R.id.imageViewProfile);
		profileImage.setPlaceholder(context.getResources().getDrawable(R.drawable.photo));
		ParseFile photoFile = parsePost.getParseFile(ParseReview.PHOTO);
		if (photoFile != null) {
			profileImage.setParseFile(photoFile);
			profileImage.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					// nothing to do
				}
			});
		}

		TextView username = (TextView) v.findViewById(R.id.textViewUsername);
		TextView textViewPost = (TextView) v.findViewById(R.id.textViewPost);
		final Button buttonLike = (Button) v.findViewById(R.id.buttonLike);

		username.setText(review.getFromName());
		textViewPost.setText(review.getMessage());
		buttonLike.setText(review.getLikes() + "");
		buttonLike.setTag(parsePost);

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

		return v;
	}

}