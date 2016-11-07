package com.volley.req.net;

import com.volley.req.net.inferface.IParser;
import org.json.JSONObject;

public class ParserHelper implements IParser {
	private IParser mParser;

	public IParser getmParser() {
		return mParser;
	}

	public void setmParser(IParser mParser) {
		this.mParser = mParser;
	}

	@Override
	public Object fromJson(JSONObject object) {
		// TODO Auto-generated method stub
		return mParser.fromJson(object);
	}

	@Override
	public Object fromJson(String json) {
		// TODO Auto-generated method stub
		return mParser.fromJson(json);
	}

}
