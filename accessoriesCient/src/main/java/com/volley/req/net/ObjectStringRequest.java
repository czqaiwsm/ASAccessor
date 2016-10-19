package com.volley.req.net;//package com.volley.req.net;
//
//import com.android.volley.NetworkResponse;
//import com.android.volley.ParseError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.Response.ErrorListener;
//import com.android.volley.Response.Listener;
//import com.android.volley.toolbox.HttpHeaderParser;
//import com.volley.req.net.inferface.IDeliverParser;
//
//import java.io.UnsupportedEncodingException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author weizhi
// * @version 1.0
// * @date 2015.08.27
// *
// * purpose:�����Ŀ�ľ���Ϊ�˽��post����
// * */
//public class ObjectStringRequest extends /* StringRequest */Request<Object> {
//
//	private Listener<Object> mListener;
//	private RequestParam mRequestParam;
//	Map<String, String> map = new HashMap<String, String>();
//
//	/**
//	 * Creates a new request with the given method.
//	 *
//	 * @param method the request {@link Method} to use
//	 * @param url URL to fetch the string at
//	 * @param listener Listener to receive the String response
//	 * @param errorListener Error listener, or null to ignore errors
//	 */
//	public ObjectStringRequest(int method, String url, Listener<Object> listener, ErrorListener errorListener) {
//
//		super(method, url, errorListener);
//
//		mListener = listener;
//	}
//
//	/**
//	 * Creates a new GET request.
//	 *
//	 * @param url URL to fetch the string at
//	 * @param listener Listener to receive the String response
//	 * @param errorListener Error listener, or null to ignore errors
//	 */
//	// public ObjectStringRequest(RequestParam param, Listener<Object> listener, ErrorListener errorListener) {
//	// this(TextUtils.isEmpty(param.buildBody()) ? Method.GET : Method.POST, param.buildRequestUrl(), listener, errorListener);
//	// Log.i("test", "param url = " + param.buildRequestUrl());
//	// mRequestParam = param;
//	// }
//
//	@Override
//	protected void deliverResponse(Object response) {
//		AppLog.Logd("response===" + response.toString());
//		mListener.onResponse(response);
//	}
//
//	@Override
//	protected Response<Object> parseNetworkResponse(NetworkResponse response) {
//
//		try {
//			byte[] data = response.data;
//			String charset = HttpHeaderParser.parseCharset(response.headers);
//
//			String jsonString = new String(data, charset);
//			IDeliverParser deliverParser = DeliverParser.newDeliverParser();
//			Object object = deliverParser.deliverJson(mRequestParam.getmParserClassName(), jsonString);
//			return Response.success(object, HttpHeaderParser.parseCacheHeaders(response));
//		} catch (UnsupportedEncodingException e) {
//			return Response.error(new ParseError(e));
//		}
//	}
//
//	// @Override
//	// protected Map<String, String> getParams() throws AuthFailureError {
//	// // TODO Auto-generated method stub
//	//
//	// return mRequestParam.getmPostMap();
//	//
//	// }
//
//}
