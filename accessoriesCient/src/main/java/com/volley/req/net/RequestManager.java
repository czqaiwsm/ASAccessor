package com.volley.req.net;

import android.content.Context;
import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;

/**
 * Manager for the queue
 * 
 * @author fly.f.ren
 * 
 */
public abstract class RequestManager {

	/**
	 * the queue :-)
	 */
	private static RequestQueue mRequestQueue;

	/**
	 * Nothing to see here.
	 */
	protected RequestManager() {
		// no instances
	}

	/**
	 * @param context application context
	 */
	public static void init(Context context) {// 获取RequestQueue
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(context);
		}
	}

	/**
	 * @return instance of the queue
	 */
	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("Not initialized");
		}
	}

	public static void getRequestData(Context context, Listener<Object> listener, ErrorListener errorListener, RequestParam param) {
		// to do auto-generate

		String uri = param.buildRequestUrl();
		RequestManager.init(context);
		if (param.getmParserClassName() != null) {
			RequestManager.getRequestQueue().cancelAll(param.getmParserClassName());
		}
		Log.i("hylapp", "uri==" + uri);
		Request<Object> request = null;
		try {
			if(param.paramType == RequestParam.ParamType.JSON){
				request = new ObjectRequest(param, listener, errorListener);
			}else {
				request = new KEYValuesRequest(param,listener, errorListener);
			}
			if (param.getmParserClassName() != null) {
				request.setTag(param.getmParserClassName());
			}
		} catch (JSONException e) {
			Log.e("JSON","JSONException" + e.getMessage());
			return;
		}
		request.setRetryPolicy(new DefaultRetryPolicy(50000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		RequestManager.getRequestQueue().add(request);
	}

}
