package com.accessories.seller.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.accessories.seller.R;
import com.accessories.seller.bean.OrderDetailInfo;
import com.accessories.seller.fragment.BaseFragment;
import com.accessories.seller.help.RequestHelp;
import com.accessories.seller.help.RequsetListener;
import com.accessories.seller.parse.OrderDetailBeanParse;
import com.accessories.seller.utils.URLConstants;
import com.accessories.seller.utils.WaitLayer;
import com.accessories.seller.view.PayPopupwidow;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.Map;

/**
 * 订单详情
 *
 * @author czq
 * @time 2015年9月28日上午11:44:26
 */
public class OrederDetailFragment extends BaseFragment implements RequsetListener,View.OnClickListener {


    @Bind(R.id.courseName)
    TextView courseName;
    @Bind(R.id.course_duration)
    TextView course_duration;
    @Bind(R.id.count_time)
    TextView count_time;
    @Bind(R.id.date_time)
    TextView dateTime;
    @Bind(R.id.orderPrice)
    TextView orderPrice;
    @Bind(R.id.discount)
    TextView discount;
    @Bind(R.id.payPrice)
    TextView payPrice;
    @Bind(R.id.contact)
    TextView contact;
    @Bind(R.id.buy)
    TextView buy;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.grade)
    TextView grade;

    private String orderId = "";
    private String orderStatus = "";
    private String studentName = "";
    private OrderDetailInfo orderDetailInfo ;

    private int flag = 0 ;//1 待支付\2 已支付\ 3 已取消\ 4 已完成
    private PayPopupwidow payPopupwidow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if(intent != null ){
            orderId = intent.getStringExtra("orderId");
            flag = intent.getIntExtra("flag",0);
            studentName = intent.getStringExtra("studentName");
//            orderStatus = intent.getStringExtra("orderStatus");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);
        initTitleView();
        requestTask(0);
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText(R.string.order_detail);
    }



    /**
     * 请求 用户信息
     */
    @Override
    public void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = null;
        RequestParam param = new RequestParam();
        switch (requestType){
            case 0://详情
                postParams = RequestHelp.getBaseParaMap("OrderDetail");
                postParams.put("orderId",orderId);
                param.setmParserClassName(OrderDetailBeanParse.class.getName());
                break;
        }

        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj)  {
        
        switch (requestType){
            case 0:
                 orderDetailInfo = ((JsonParserBase<OrderDetailInfo>)obj).getObj();
                if(orderDetailInfo != null){
                    if(orderDetailInfo != null){
                        courseName.setText(studentName);
                        course_duration.setText(orderDetailInfo.getCourseName());
                        count_time.setText(orderDetailInfo.getPayCount()+"");
                        dateTime.setText(TextUtils.isEmpty(orderDetailInfo.getPayTime())?"":orderDetailInfo.getPayTime());
//                accountDuration.setText(orderDetailInfo.get);
                        orderPrice.setText(String.format(getResources().getString(R.string.balance_has,orderDetailInfo.getOrderPrice())) );
                        discount.setText(String.format(getResources().getString(R.string.balance_has,orderDetailInfo.getDiscountPrice())) );
                        payPrice.setText(getResources().getString(R.string.balance_has,orderDetailInfo.getPayPrice()));
                        address.setText(orderDetailInfo.getRemark());
                        grade.setText(orderDetailInfo.getGradeName());
                        contact.setOnClickListener(this);
                        buy.setOnClickListener(this);
                    }
                }

                break;
        }
       
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.contact:
                if(flag == 1){//待支付(联系老师)
                }else if(flag == 2){//已支付(完成订单)
                    requestTask(3);
                }
                break;
            case R.id.buy:
                if(flag == 1){//待支付(立即支付)
                    if(orderDetailInfo != null){
                    }

                }else if(flag == 2){//已支付(申请退款)

                }else if(flag==4){//已完成(立即评价)
                }
                break;

        }
    }

//    @Override
//    public void paySucc() {
////      handler.sendEmptyMessage(OrderFragment.PAY_SUCC);
//        mActivity.setResult(Activity.RESULT_OK);
//        mActivity.finish();
//    }
//
//
//    @Override
//    public void payFail() {
//        toasetUtil.showInfo("支付失败");
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==Activity.RESULT_OK && requestCode==200){
           buy.setVisibility(View.GONE);
        }
    }
}
