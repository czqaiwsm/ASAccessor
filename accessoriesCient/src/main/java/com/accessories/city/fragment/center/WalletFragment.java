package com.accessories.city.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.activity.center.DetailActivity;
import com.accessories.city.activity.center.RechargeActivity;
import com.accessories.city.activity.center.WidthDrawActivity;
import com.accessories.city.bean.BalanceInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.BaseInfoParse;
import com.accessories.city.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.Map;

/**
 *钱包
 * @author czq
 * @time 2015年9月28日上午11:44:26
 *
 */
public class WalletFragment extends BaseFragment implements OnClickListener,RequsetListener {

//    private RelativeLayout photo_layout;
//    private RelativeLayout name_layout;
    private RelativeLayout balance_layout;//余额
    private RelativeLayout recharge_layout;//充值
    private RelativeLayout withDraw_layout;//提现

    private TextView account_balance;
//    private TextView name;
//    private TextView jonior;
//    private TextView city;


   private int recharge = 0x10;
    private int withDraw = 0x11;
    String releaName = "";
    String account   = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        initView(view);
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText(R.string.wallet);
        setLeftHeadIcon(0);
        setHeaderRightText(R.string.detail, new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 明细 todo
             toClassActivity(WalletFragment.this, DetailActivity.class.getName());
            }
        });

        requestTask();
    }

    private void initView(View v) {
//        mHeadImg = (RoundImageView) v.findViewById(R.id.account_head_img);
//        photo_layout = (RelativeLayout) v.findViewById(R.id.photo_avatar_layout);
//        name_layout = (RelativeLayout) v.findViewById(R.id.name_layout);
        balance_layout = (RelativeLayout) v.findViewById(R.id.balance_layout);
        recharge_layout = (RelativeLayout) v.findViewById(R.id.recharge_layout);
        withDraw_layout = (RelativeLayout) v.findViewById(R.id.withDraw_layout);
        account_balance = (TextView)v.findViewById(R.id.account_balance);
//        name = (TextView)v.findViewById(R.id.nick_name);
//        jonior = (TextView)v.findViewById(R.id.account_joniorname);
//        city = (TextView)v.findViewById(R.id.account_cityname);


//        photo_layout.setOnClickListener(this);
//        name_layout.setOnClickListener(this);
        balance_layout.setOnClickListener(this);
        recharge_layout.setOnClickListener(this);
        withDraw_layout.setOnClickListener(this);
        recharge_layout.setVisibility(View.GONE);

    }


    private Intent intent ;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.balance_layout:// 余额
            break;
            case R.id.recharge_layout:// 充值
            intent = new Intent(mActivity, RechargeActivity.class);
            startActivityForResult(intent,recharge);
            break;
            case R.id.withDraw_layout:// 提现
            intent = new Intent(mActivity, WidthDrawActivity.class);
                intent.putExtra("balance",balance);
                intent.putExtra("releaName",releaName);
                intent.putExtra("account",account  );
                startActivityForResult(intent,withDraw);
            break;
        }

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
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("QueryBalance");
        RequestParam param = new RequestParam();
//        param.setmParserClassName(BaseParse.class.getName());
        param.setmParserClassName(BaseInfoParse.class.getName());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    int balance = 0;
    @Override
    public void handleRspSuccess(int requestType,Object obj)  {
        BalanceInfo balanceInfo = (BalanceInfo) ((JsonParserBase)obj).getObj();
        if(balanceInfo == null) return;
        balance  =  Integer.valueOf(balanceInfo.getBalance());
        releaName = balanceInfo.getRealName();
        account = balanceInfo.getAlipay();
        account_balance.setText(String.format(getResources().getString(R.string.balance_has),balance+"") );
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            requestTask();
        }
    }


}
