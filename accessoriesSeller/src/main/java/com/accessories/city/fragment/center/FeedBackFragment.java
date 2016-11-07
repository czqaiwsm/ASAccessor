package com.accessories.city.fragment.center;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.accessories.city.R;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.BaseParse;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;

import java.util.Map;

/**
 *钱包
 * @author czq
 * @time 2015年9月28日上午11:44:26
 *
 */
public class FeedBackFragment extends BaseFragment implements OnClickListener,RequsetListener {

    private EditText rechargePrice ;
    private TextView rechareQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
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
        setTitleText(R.string.feed_back_content);
        setLeftHeadIcon(0);
    }

    private void initView(View v) {
        rechargePrice = (EditText)v.findViewById(R.id.recharge_price);
        rechareQuery = (TextView)v.findViewById(R.id.recharge_query);
        rechareQuery.setOnClickListener(this);

    }


    private Intent intent ;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.recharge_query:
                if(!TextUtils.isEmpty(rechargePrice.getText())){
                    requestTask();
                }else {
                    toasetUtil.showInfo("请填写反馈类容!");
                }
                break;
        }

    }


    /**
     * 请求 用户信息
     */
    @Override
    public void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("FeedBackPost");//关注
        RequestParam param = new RequestParam();
//        param.setmParserClassName(TeacherDetailParse.class.getName());
        postParams.put("content",rechargePrice.getText().toString());
        param.setmParserClassName(BaseParse.class.getName());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj)  {
        Toast.makeText(BaseApplication.getInstance(),"感谢您的反馈!",Toast.LENGTH_LONG).show();
        mActivity.finish();

    }




}
