package com.example.hellofoodies.handler;

import com.example.hellofoodies.parse.ParsePost;
import com.parse.ParseQuery;

public class ParseHandler {

	public static ParseQuery<ParsePost> queryPost(Integer limit, Integer skipCount) {
		ParseQuery<ParsePost> query = ParseQuery.getQuery(ParsePost.TABLE_NAME);
		if (limit != null)
			query.setLimit(limit);
		if (skipCount != null)
			query.setSkip(skipCount);
		return query;
	}

}
