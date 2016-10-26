package com.accessories.city.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.accessories.city.R;
import com.accessories.city.bean.UserInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.LoginInfoParse;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.SmartToast;
import com.accessories.city.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 账户管理-修改手机号
 * 
 * @author ccs
 * 
 */
public class PcenterModifyInfoIAFragment extends BaseFragment implements RequsetListener{

	private EditText id_pcenterinfo_line1_et1;//
	private ImageView close_btn;


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pcenterinfo_modifyinfo, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setTitleText("编辑名称");
		initView(view);
	}

	private void initView(View view) {
		setLeftHeadIcon(0);
		close_btn = (ImageView)view.findViewById(R.id.close_btn);
		id_pcenterinfo_line1_et1 = (EditText) view.findViewById(R.id.id_pcenterinfo_line1_et1);
		close_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				id_pcenterinfo_line1_et1.setText("");
			}
		});

		setHeaderRightText("保存", new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
					String string = id_pcenterinfo_line1_et1.getText().toString();
					if (TextUtils.isEmpty(string) ) {
						SmartToast.showText(mActivity,"姓名不能为空");
						return;
					}
					intent.putExtra("name",string);
//				mActivity.setResult(Activity.RESULT_OK,intent);
//                mActivity.finish();

				requestTask();
			}
		});
	}


	@Override
	protected void requestData(int requestCode) {
		HttpURL url = new HttpURL();
		Map postParams = new HashMap<String,String>();
		url.setmBaseUrl(URLConstants.UPDATE_USER);
		UserInfo mUserInfo = BaseApplication.getUserInfo();
		if(mUserInfo!= null)
			postParams.put("id", ""+mUserInfo.getId());
		postParams.put("nickname",id_pcenterinfo_line1_et1.getText().toString());
		RequestParam param = new RequestParam();
		param.setmPostMap(postParams);
		param.setmHttpURL(url);
		param.setPostRequestMethod();
		RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestCode), createMyReqErrorListener(), param);
	}
	@Override
	public void handleRspSuccess(int requestType,Object obj) {
		JsonParserBase<UserInfo> jsonParserBase = (JsonParserBase<UserInfo>)obj;
		if ((jsonParserBase != null)){
			UserInfo mUserInfo = jsonParserBase.getObj();
			BaseApplication.saveUserInfo(jsonParserBase.getObj());
			BaseApplication.setMt_token(jsonParserBase.getObj().getId());
			JPushInterface.setAlias(BaseApplication.getInstance(), "t_" + BaseApplication.getUserInfo().getId(), null);
		}
	}

}
