package com.accessories.city.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.bean.UserInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.utils.BaseApplication;

/**
 *钱包
 * @author czq
 * @time 2015年9月28日上午11:44:26
 *
 */
public class EditFragment extends BaseFragment implements OnClickListener{

    private EditText rechargePrice ;
    private TextView rechareQuery;

    private int flag = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initTitleView();
    }

    private void initTitleView() {
        setLeftHeadIcon(0);

        Intent fromIn = mActivity.getIntent();
        String title = "";
        String content = "";
        UserInfo userInfo = BaseApplication.getUserInfo();
        if(fromIn != null ){
            switch (fromIn.getFlags()){
//                case  40:  //签名
//                     title =  "修改签名";
//                    content = userInfo==null?"":userInfo.getSignature();
//                    break;
//                case  50:
//                    title =  "修改简介";
//                    content = userInfo==null?"":userInfo.getIntroduction();
//                    break;
//                case  60:
//                    title =  "修改经历";
//                    content = userInfo==null?"":userInfo.getExperience();
//                    break;
            }
        }
        rechargePrice.setHint(content);
        setTitleText(title);
        setLeftHeadIcon(0);
        setHeaderRightText(R.string.submit, new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(TextUtils.isEmpty(rechargePrice.getText())){
                    toasetUtil.showInfo("请输入内容");
                    return;
                }
                Intent intent = new Intent();
                 intent.putExtra("value",rechargePrice.getText().toString());
                mActivity.setResult(Activity.RESULT_OK,intent);
                mActivity.finish();
            }
        });
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
                    toasetUtil.showInfo("请填写内容!");
                }
                break;
        }

    }







}
