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
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.utils.SmartToast;

/**
 * 账户管理-修改手机号
 * 
 * @author ccs
 * 
 */
public class PcenterModifyInfoIAFragment extends BaseFragment {

	private EditText id_pcenterinfo_line1_et1;//
	private LinearLayout  modify_info_2, id_pcenterinfo_line1, id_pcenterinfo_line2;
	private RelativeLayout modify_info_1;
	private ImageView sex_0, sex_1;
	private ImageView close_btn;

	// 请求状态标志位
	private int FLAG = -1;// 1:昵称 2:qq 3:邮箱 4:微博 5：博客6：签名 7：公众号
	private String sex = "";// 选择性别

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		FLAG = activity.getIntent().getExtras().getInt("flag");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pcenterinfo_modifyinfo, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initData();
	}

	private void initView(View view) {
		setLeftHeadIcon(0);
		modify_info_1 = (RelativeLayout) view.findViewById(R.id.modify_info_1);
		close_btn = (ImageView)view.findViewById(R.id.close_btn);
		id_pcenterinfo_line1_et1 = (EditText) view.findViewById(R.id.id_pcenterinfo_line1_et1);
		modify_info_2 = (LinearLayout) view.findViewById(R.id.modify_info_2);
		id_pcenterinfo_line1 = (LinearLayout) view.findViewById(R.id.id_pcenterinfo_line1);
		id_pcenterinfo_line2 = (LinearLayout) view.findViewById(R.id.id_pcenterinfo_line2);
		close_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				id_pcenterinfo_line1_et1.setText("");
			}
		});
		if (FLAG != 8) {// 不是性别
			modify_info_1.setVisibility(View.VISIBLE);
			modify_info_2.setVisibility(View.GONE);

		} else {// 性别
			sex_0 = (ImageView) view.findViewById(R.id.sex_0);
			sex_1 = (ImageView) view.findViewById(R.id.sex_1);
			modify_info_1.setVisibility(View.GONE);
			modify_info_2.setVisibility(View.VISIBLE);
//			sex = BaseApplication.getUserInfo().getGender();
			initData();
			id_pcenterinfo_line1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sex = "1";
					initData();
				}
			});
			id_pcenterinfo_line2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sex = "2";
					initData();
				}
			});
		}

		setHeaderRightText(R.string.sure, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				if (FLAG != 8) {//姓名
					String string = id_pcenterinfo_line1_et1.getText().toString();
					if (TextUtils.isEmpty(string) ) {
						SmartToast.showText(mActivity,"姓名不能为空");
						return;
					}

					intent.putExtra("name",string);
				}else {
					intent.putExtra("gender",sex);
				}
				mActivity.setResult(Activity.RESULT_OK,intent);
                mActivity.finish();
			}
		});
	}

	private void initData() {
		// 1:昵称 2:qq 3:邮箱 4:微博 5：博客6：签名 7：公众号
		switch (FLAG) {
		case 1:
			setTitleText("修改昵称");
			id_pcenterinfo_line1_et1.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) }); // 最大输入长度
//			id_pcenterinfo_line1_et1.setText(BaseApplication.getUserInfo().getNickName());
			break;
		case 8:
			setTitleText("选择性别");
			if (sex.equals("1")) {
				sex_0.setVisibility(View.VISIBLE);
				sex_1.setVisibility(View.INVISIBLE);
			} else {
				sex_0.setVisibility(View.INVISIBLE);
				sex_1.setVisibility(View.VISIBLE);
			}
			break;
		}
	}

	/******************* 网络数据获取 **************************/
	// 请求数据
	@Override
	public void requestData(int requestType) {
//		RequestParam param = new RequestParamSub(getActivity());
//		HttpURL url = new HttpURL();
//		url.setmBaseUrl(url);
//		switch (FLAG) {
//		case 1:
//			url.setmGetParamPrefix("nickname").setmGetParamValues(id_pcenterinfo_line1_et1.getText().toString());
//			break;
//		case 8:
//			url.setmGetParamPrefix("sex").setmGetParamValues(sex + "");
//			break;
//		}
//		param.setmHttpURL(url);
//		param.setmParserClassName(CommonParser.class.getName());
//		RequestManager.getRequestData(getActivity(), creatReqSuccessListener(), createErrorListener(), param);
	}


	@Override
	public void onStop() {
//		RequestManager.getRequestQueue().cancelAll(CommonParser.class.getName());
		super.onStop();
	}
}
