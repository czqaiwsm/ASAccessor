package com.volley.req.net;

import com.android.volley.Request.Method;

import java.util.Map;

public class RequestParam<T extends Object> implements IRequestParam {
	private HttpURL mHttpURL;

	public ParamType paramType = ParamType.KEY_VALUE;

	private String mParserClassName;

	private int mRequestMethod = Method.GET;

	private Map<String, String> mHeadersMap;
	private Map<String, String> mPostMap;//post map key=value&key1=value1
	private T mPostJsonObject;

	public HttpURL getmHttpURL() {
		return mHttpURL;
	}

	public void setmHttpURL(HttpURL mHttpURL) {
		this.mHttpURL = mHttpURL;
	}

	public T getmPostJsonObject() {
		return mPostJsonObject;
	}


	public Map<String, String> getmPostMap() {
		return mPostMap;
	}

	public void setmPostMap(Map<String, String> mPostMap) {
		this.mPostMap = mPostMap;
	}

	/**
	 * 设置post的请求参数
	 * 
	 * @param postJsonObject：可以是Map和自定义的实体类（Entity)
	 */
	public void setmPostarams(T postJsonObject) {
		this.mPostJsonObject = postJsonObject;
	}

	public Map<String, String> getmHeadersMap() {
		return mHeadersMap;
	}

	public void setmHeadersMap(Map<String, String> mHeadersMap) {
		this.mHeadersMap = mHeadersMap;
	}

	public void setPostRequestMethod() {
		mRequestMethod = Method.POST;
	}

	public void setDeleteRequestMethod() {
		mRequestMethod = Method.DELETE;
	}

	public void setPutRequestMethod() {
		mRequestMethod = Method.PUT;
	}

	@Override
	public String buildRequestUrl() {
		// TODO Auto-generated method stub
		return mHttpURL.toString();
	}

	public String getmParserClassName() {
		return mParserClassName;
	}

	public void setmParserClassName(String mParserClassName) {
		this.mParserClassName = mParserClassName;
	}

	@Override
	public int requestMethod() {
		return mRequestMethod;
	}

	public enum ParamType{
		JSON,//请求为json
		KEY_VALUE//key=value & key1=value1
	}

}
