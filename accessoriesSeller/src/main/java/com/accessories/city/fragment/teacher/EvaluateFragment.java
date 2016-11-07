package com.accessories.city.fragment.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.bean.OrderInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.SmartToast;
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
public class EvaluateFragment extends BaseFragment implements OnClickListener,RequsetListener {

    private EditText rechargePrice ;
    private TextView rechareQuery;
    private RatingBar ratingBar;

    private TextView teacherName;
    private OrderInfo orderInfo ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments() ;
        if(bundle != null){
            orderInfo = (OrderInfo) bundle.getSerializable("orderInfo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluate, container, false);
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
        ratingBar = (RatingBar)v.findViewById(R.id.myRatingbar);
        ratingBar.setRating(5);
        teacherName = (TextView)v.findViewById(R.id.teacher_name);
        if(orderInfo != null){
            rechareQuery.setOnClickListener(this);
            teacherName.setText(orderInfo.getTeacherName());
        }


    }


    private Intent intent ;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.recharge_query:
                if(!TextUtils.isEmpty(rechareQuery.getText())){
                    requestTask();
                }else {
                    toasetUtil.showInfo("请填写评价内容!");
                }
                break;
        }

    }


    /**
     * 请求 用户信息
     */
    @Override
    public void requestData(int requestType) {

//        teacherId	老师ID
//        studentName	学生姓名
//        grade	学生年级
//        headImg	评价人头像
//        serviceScore	服务评分
//        commentDesc	评价描述

        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("UserEvaluateSubmit");//关注
        RequestParam param = new RequestParam();
//        param.setmParserClassName(TeacherDetailParse.class.getName());
        postParams.put("teacherId",orderInfo.getTeacherId());
//        postParams.put("studentName", BaseApplication.getUserInfo().getNickName());
//        postParams.put("grade",BaseApplication.getUserInfo().getGrade());
//        postParams.put("headImg",BaseApplication.getUserInfo().getHeadImg());
        postParams.put("serviceScore",ratingBar.getRating());
        postParams.put("commentDesc",TextUtils.isEmpty(rechareQuery.getText())?"":rechareQuery.getText());

//        param.setmParserClassName(new TeacherDetailParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj)  {
        SmartToast.showText("评价成功!");
        mActivity.setResult(Activity.RESULT_OK);
        mActivity.finish();

    }




}
