package com.volley.req.net;


import java.util.HashMap;
import java.util.Map;

public class RequestParamSub extends RequestParam {
	public  Map<String, String> getmHeadersMap() {
		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("mt-token", BaseApplication.getMt_token());
//		headers.put("mt-id", BaseApplication.getMt_id());
		return headers;
	}
}
