package com.example.hellofoodies.utility;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hellofoodies.R;
import com.example.hellofoodies.parse.ParsePost;

public class EndlessAdapter extends ArrayAdapter<ParsePost> {

	private List<ParsePost> itemList;
	private Context ctx;
	private int layoutId;

	public EndlessAdapter(Context ctx, List<ParsePost> itemList, int layoutId) {
		super(ctx, layoutId, itemList);
		this.itemList = itemList;
		this.ctx = ctx;
		this.layoutId = layoutId;
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public ParsePost getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return itemList.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View result = convertView;

		if (result == null) {
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			result = inflater.inflate(layoutId, parent, false);
		}

		ParsePost parsePost = itemList.get(position);
		// We should use class holder pattern
		TextView tv = (TextView) result.findViewById(R.id.textViewUsername);
		tv.setText(parsePost.getFromName());

		return result;

	}

}
