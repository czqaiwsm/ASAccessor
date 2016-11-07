package com.accessories.city.fragment.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.accessories.city.R;
import com.accessories.city.activity.login.LoginActivity;
import com.accessories.city.bean.VerifyCode;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.BaseParse;
import com.accessories.city.parse.VerifyCodeParse;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.PhoneUitl;
import com.accessories.city.utils.SmartToast;
import com.accessories.city.utils.URLConstants;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordFragment extends BaseFragment implements OnClickListener,RequsetListener {

	private EditText forget_phone;
	private EditText forget_inputCode;
	private EditText forget_passAgain2;
	private EditText forget_pass;
	private TextView forget_getCode;
	private TextView forget_next;
	private boolean isgetCode = true;

	private int MSG_TOTAL_TIME;
	private final int MSG_UPDATE_TIME = 500;
	private  int requetType = -1;
	private String phone = null;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// startReqTask(this);
		// mLoadHandler.sendEmptyMessageDelayed(Constant.NET_SUCCESS, 100);// 停止加载框
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pcenter_forget_password, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initTitleView();
		initView(view);

	}

	private void initTitleView() {
		setTitleText(R.string.pcenter_forget_pass);
		setLeftHeadIcon(0);
	}

	private void initView(View view) {
		forget_phone = (EditText) view.findViewById(R.id.forget_username);
		forget_inputCode = (EditText) view.findViewById(R.id.forget_passCode);
		forget_pass = (EditText) view.findViewById(R.id.forget_pass);
		forget_getCode = (TextView) view.findViewById(R.id.forget_getCode);
		forget_next = (TextView) view.findViewById(R.id.forget_next);
		forget_passAgain2 = (EditText) view.findViewById(R.id.forget_passAgain2);

		forget_next.setOnClickListener(this);
		forget_getCode.setOnClickListener(this);
	}


	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.forget_next:
			isgetCode = false;
			onJudge();
			break;
		case R.id.forget_getCode:
			isgetCode = true;
			getCode();
			break;
		default:
			break;
		}
	}

	private void onJudge() {
		if (TextUtils.isEmpty(forget_phone.getText().toString()+forget_inputCode.getText().toString()+forget_pass.getText().toString())) {
			SmartToast.showText(BaseApplication.getInstance(), R.string.input_error);
			return;
		}
		if (!forget_phone.getText().toString().equalsIgnoreCase(phone)) {
			toasetUtil.showInfo("手机号不一致,请重新获取验证码!");
			return;
		}

		if(verifyCode == null || !verifyCode.getMsgCode().equalsIgnoreCase(forget_inputCode.getText().toString())){
			toasetUtil.showInfo("请输入正确的验证码!");
			return;
		}
		if (!forget_passAgain2.getText().toString().equals(forget_pass.getText().toString())) {
			toasetUtil.showInfo(R.string.pass_errors);
			return;
		}
		requetType = 2;
		requestTask();
	}

	private void getCode() {
		if (forget_phone.length() == 0) {
			SmartToast.makeText(mActivity, R.string.input_error, Toast.LENGTH_SHORT).show();
		} else {
			if (!PhoneUitl.isPhone(forget_phone.getText().toString())) {
				toasetUtil.showInfo(R.string.phone_error);
				forget_phone.setText("");
			} else {
				forget_getCode.setEnabled(false);
				MSG_TOTAL_TIME = 60;
				Message message = new Message();
				message.what = MSG_UPDATE_TIME;
				timeHandler.sendMessage(message);
				phone = forget_phone.getText().toString();
				requetType = 1;
				requestData(0);// ----------发送请求
				forget_getCode.requestFocus();
			}
		}
	}

	// 验证码倒计时
	public Handler timeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_UPDATE_TIME:
				MSG_TOTAL_TIME--;
				if (MSG_TOTAL_TIME > 0) {
					forget_getCode.setText(String.format(getResources().getString(R.string.has_minuter,MSG_TOTAL_TIME+"")));

					forget_getCode.setText(MSG_TOTAL_TIME + " 秒");
					Message message = obtainMessage();
					message.what = MSG_UPDATE_TIME;
					sendMessageDelayed(message, 1000);
				} else if (MSG_TOTAL_TIME < -10) {
					forget_getCode.setText(R.string.addcode_resend);
					forget_getCode.setEnabled(true);
				} else {
					forget_getCode.setText(R.string.addcode_code);
					forget_getCode.setEnabled(true);
				}
				break;
			default:
				break;
			}
		}
	};



	@Override
	public void requestData(int questType) {
		RequestParam param = new RequestParam();
		HttpURL url = new HttpURL();
		Map postParams = new HashMap();

		switch (requetType){
			case 1:
				url.setmBaseUrl(URLConstants.MSGCODE);
				postParams.put("phone", phone);
				param.setmParserClassName(VerifyCodeParse.class.getName());
				break;
			case 2://注册
				url.setmBaseUrl(URLConstants.UPDATEPWD);
				postParams.put("phone", phone);
//				postParams.put("code",forget_inputCode.getText().toString());
//				postParams.put("userId",BaseApplication.getUserInfo().getId());
				postParams.put("newPwd",forget_pass.getText().toString());

//				postParams.put("vcode",inputCode.getText().toString());
//				postParams.put("sendId",verifyCode.getSendId());
//				postParams.put("sendId",verifyCode.getSendId());
				param.setmParserClassName(BaseParse.class.getName());
//				param.setmParserClassName(new BaseParse());
				break;
		}
		param.setmPostMap(postParams);
		param.setmHttpURL(url);
		param.setPostRequestMethod();
		RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
	}

	private VerifyCode verifyCode = null;//验证码

	@Override
	public void handleRspSuccess(int requestType,Object obj) {
		switch (requetType){
			case 1:
				MSG_TOTAL_TIME = -1;
				phone = forget_phone.getText().toString();
				JsonParserBase<VerifyCode> jsonParserBase1 = (JsonParserBase<VerifyCode>)obj;
				verifyCode = jsonParserBase1.getObj();
                toasetUtil.showInfo("信息已发送!");
//				forget_inputCode.setText(verifyCode !=null?verifyCode.getSmsCode():"");
//				AlertDialogUtils.displayMyAlertChoice(mActivity,"验证码",verifyCode.getSmsCode()+"",null,null);
				break;
			case 2:
				toClassActivity(ForgetPasswordFragment.this, LoginActivity.class.getName());
				SmartToast.showText(mActivity,"成功找回密码!");
				mActivity.finish();
				break;
		}

	}

	@Override
	protected void failRespone() {
		super.failRespone();
		MSG_TOTAL_TIME = -11;

	}
	protected Response.ErrorListener createMyReqErrorListener() {
		super.createMyReqErrorListener();
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (!isDetached()) {
					Message msg = new Message();
					MSG_TOTAL_TIME = -11;
					Message message = new Message();
					message.what = MSG_UPDATE_TIME;
					timeHandler.removeMessages(MSG_UPDATE_TIME);
					timeHandler.sendMessageDelayed(message, 10000);
				}
			}
		};
	}
}
