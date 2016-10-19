package com.volley.req.net;

import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.volley.req.net.inferface.IDeliverParser;
import com.volley.req.parser.ParserUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Wrapper for Volley requests to facilitate parsing of json responses.
 * 
 * @param
 */
public class ObjectRequest extends JsonRequest<Object> {

	private Listener<Object> mListener;
	private RequestParam mRequestParam;

	/**
	 * Creates a new request.
	 * 
	 * @param method the HTTP method to use
	 * @param jsonRequest A {@link JSONObject} to post with the request. Null is allowed and indicates no parameters will be posted along with
	 * request.
	 * @param listener Listener to receive the JSON response
	 * @param errorListener Error listener, or null to ignore errors.
	 */
	public <T extends Object> ObjectRequest(int method, RequestParam param, T jsonRequest, Listener<Object> listener, ErrorListener errorListener) {
		super(method, param.buildRequestUrl(), (jsonRequest == null) ? null : (jsonRequest instanceof JSONObject ? jsonRequest.toString()
				: ParserUtil.toJson(jsonRequest)), listener, errorListener);
		if (jsonRequest != null) {
			System.out.println("postRequstParams=="
					+ (jsonRequest instanceof JSONObject ? jsonRequest.toString() : ParserUtil.toJson(jsonRequest)));
		}
		mListener = listener;
		mRequestParam = param;
	}

	/**
	 * Constructor which defaults to <code>GET</code> if <code>jsonRequest</code> is <code>null</code>, <code>POST</code> otherwise.
	 *
	 * @throws JSONException
	 *
	 */
	public ObjectRequest(RequestParam param, Listener<Object> listener, ErrorListener errorListener) throws JSONException {

		this(param.requestMethod(), param,
				param.requestMethod() != Method.GET ? param.getmPostJsonObject() : null, listener, errorListener);

	}

	@Override
	protected Response<Object> parseNetworkResponse(NetworkResponse response) {
		try {
			byte[] data = response.data;
			String charset = HttpHeaderParser.parseCharset(response.headers);
			String jsonString = new String(data, charset);
			System.out.println("responJson=="+jsonString);
			IDeliverParser deliverParser = DeliverParser.newDeliverParser();
			Object object = null;
			if (mRequestParam.getmParserClassName() == null) {
				object = jsonString;
			} else {
				object = deliverParser.deliverJson(mRequestParam.getmParserClassName(), jsonString);
			}

			return Response.success(object, HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(Object response) {
		if (response != null) {
			Log.d("respone","response===" + response.toString());
			mListener.onResponse(response);
		}

	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		if (mRequestParam.getmHeadersMap() != null) {
			return mRequestParam.getmHeadersMap();
		}
		return super.getHeaders();
	}

}
