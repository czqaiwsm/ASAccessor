package com.accessories.city.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.accessories.city.R;
import com.accessories.city.bean.SellerInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.URLConstants;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 关于我们
 *
 * @author czq
 * @time 2015年9月28日上午11:44:26
 */
public class AboutUsFragment extends BaseFragment implements OnClickListener, RequsetListener {


    @Bind(R.id.selleName)
    TextView selleName;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.balance_layout)
    RelativeLayout balanceLayout;
    @Bind(R.id.account_recharge)
    TextView accountRecharge;
    @Bind(R.id.recharge_layout)
    RelativeLayout rechargeLayout;
    @Bind(R.id.account_withDraw)
    TextView accountWithDraw;
    @Bind(R.id.withDraw_layout)
    RelativeLayout withDrawLayout;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.addressLL)
    LinearLayout addressLL;
    @Bind(R.id.phone1)
    TextView phone1;
    @Bind(R.id.phone1LL)
    LinearLayout phone1LL;
    @Bind(R.id.phone2)
    TextView phone2;
    @Bind(R.id.phone2LL)
    LinearLayout phone2LL;
    @Bind(R.id.tel1)
    TextView tel1;
    @Bind(R.id.tel1LL)
    LinearLayout tel1LL;
    @Bind(R.id.tel2)
    TextView tel2;
    @Bind(R.id.tel2LL)
    LinearLayout tel2LL;
    @Bind(R.id.QQ)
    TextView QQ;
    @Bind(R.id.QQLL)
    LinearLayout QQLL;
    @Bind(R.id.wx)
    TextView wx;
    @Bind(R.id.wxLL)
    LinearLayout wxLL;
    @Bind(R.id.bussiness)
    TextView bussiness;

    private String shopId ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if(intent != null){
            shopId = intent.hasExtra("shopId")?intent.getStringExtra("shopId"):"";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        initView();
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText("商家主页");
        setLeftHeadIcon(0);

        requestTask(1);
    }

    private void initView() {
        phone1LL.setOnClickListener(this);
        phone2LL.setOnClickListener(this);
        tel1LL.setOnClickListener(this);
        tel2LL.setOnClickListener(this);
    }


   private String phoneStr = "";
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.phone1LL:
                phoneStr = phone1.getText().toString();
                break;
            case R.id.phone2LL:
                phoneStr = phone2.getText().toString();
                break;
            case R.id.tel1LL:
                phoneStr = tel1.getText().toString();
                break;
            case R.id.tel2LL:
                phoneStr = tel2.getText().toString();
                break;
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneStr));
        startActivity(intent);
        requestData(2);
    }

    /**
     * 重新登录
     */
    private void reLogin() {
//        startActivityForResult(new Intent(getActivity(), LoginActivity.class), Constant.RELOGIN);
    }

    /**
     * 请求 用户信息
     */
    @Override
    public void requestData(int requestType) {
        HttpURL url = new HttpURL();
        Map postParams = new HashMap();
        switch (requestType){
            case 1:
                url.setmBaseUrl(URLConstants.SHOPDETAIL);
                postParams.put("shopId",shopId);
                break;
            case 2:
                url.setmBaseUrl(URLConstants.CALL);
                postParams.put("phone", shopId);
                postParams.put("shopId", shopId);
                postParams.put("userId", BaseApplication.getUserInfo().getId());
                break;
        }
        RequestParam param = new RequestParam();
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType, Object obj) {
        switch (requestType){
            case 1:

                JsonParserBase<SellerInfo> jsonParserBase =  ParserUtil.fromJsonBase(obj.toString(), new TypeToken<JsonParserBase<SellerInfo>>() {
                }.getType());
                SellerInfo balanceInfo =  jsonParserBase.getObj();
                if (balanceInfo == null) return;
                ImageLoader.getInstance().displayImage(balanceInfo.getShopPic(), img);
                selleName.setText(balanceInfo.getShopName());
                bussiness.setText(balanceInfo.getShopDesc());
                if(!TextUtils.isEmpty(balanceInfo.getShopAddr())){
                    address.setText(balanceInfo.getShopAddr());
                    addressLL.setVisibility(View.VISIBLE);
                }
                if(!TextUtils.isEmpty(balanceInfo.getPhone())){
                    phone1.setText(balanceInfo.getPhone());
                    phone1LL.setVisibility(View.VISIBLE);
                }
                if(!TextUtils.isEmpty(balanceInfo.getPhone2())){
                    phone2.setText(balanceInfo.getPhone2());
                    phone2LL.setVisibility(View.VISIBLE);
                }
                if(!TextUtils.isEmpty(balanceInfo.getTel())){
                    tel1.setText(balanceInfo.getTel());
                    tel1LL.setVisibility(View.VISIBLE);
                }
                if(!TextUtils.isEmpty(balanceInfo.getTel2())){
                    tel2.setText(balanceInfo.getTel2());
                    tel2LL.setVisibility(View.VISIBLE);
                }
                if(!TextUtils.isEmpty(balanceInfo.getQq())){
                    QQ.setText(balanceInfo.getQq());
                    QQLL.setVisibility(View.VISIBLE);
                }
                if(!TextUtils.isEmpty(balanceInfo.getWx())){
                    wx.setText(balanceInfo.getWx());
                    wxLL.setVisibility(View.VISIBLE);
                }
                break;
            case 2:

                break;

        }



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            requestTask(1);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
