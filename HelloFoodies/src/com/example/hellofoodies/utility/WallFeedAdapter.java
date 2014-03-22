package com.example.hellofoodies.utility;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hellofoodies.R;
import com.example.hellofoodies.parse.ParsePost;
import com.example.hellofoodies.parse.ParseReview;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class WallFeedAdapter extends ParseQueryAdapter<ParsePost> {
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
		if (v == null) {
			v = View.inflate(getContext(), R.layout.review_wall_feed, null);
		}
		super.getItemView(parsePost, v, parent);
		
		ParseReview review = (ParseReview)parsePost;

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
		Button buttonLike = (Button) v.findViewById(R.id.buttonLike);

		username.setText(review.getFromName());
		textViewPost.setText(review.getMessage());
		buttonLike.setText(review.getLikes() + "");

		return v;
	}

}