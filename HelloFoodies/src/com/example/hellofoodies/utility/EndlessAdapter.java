package com.example.hellofoodies.utility;

import java.util.List;

import com.example.hellofoodies.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EndlessAdapter extends ArrayAdapter<String> {

	private List<String> itemList;
	private Context ctx;
	private int layoutId;

	public EndlessAdapter(Context ctx, List<String> itemList, int layoutId) {
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
	public String getItem(int position) {
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

		// We should use class holder pattern
		TextView tv = (TextView) result.findViewById(R.id.txt1);
		tv.setText(itemList.get(position));

		return result;

	}

}
