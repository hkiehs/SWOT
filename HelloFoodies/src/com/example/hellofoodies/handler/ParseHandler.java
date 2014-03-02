package com.example.hellofoodies.handler;

import com.example.hellofoodies.parse.ParsePost;
import com.parse.ParseQuery;

public class ParseHandler {

	public static ParseQuery<ParsePost> queryPostInDescOrder(Integer limit, Integer skipCount, boolean enableCache) {
		ParseQuery<ParsePost> query = ParseQuery.getQuery(ParsePost.TABLE_NAME);
		if (limit != null)
			query.setLimit(limit);
		if (skipCount != null)
			query.setSkip(skipCount);
		if (enableCache)
			query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);

		query.orderByDescending("createdAt");
		return query;
	}

}
