package com.volley.req.net.inferface;

import org.json.JSONObject;

public interface IParser {
	Object fromJson(JSONObject object);

	Object fromJson(String json);
}
