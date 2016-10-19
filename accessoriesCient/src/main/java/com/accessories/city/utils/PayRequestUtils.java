//package com.accessories.city.utils;
//
//import android.view.View;
//import com.alipay.sdk.pay.demo.AlipayUtil;
//import com.alipay.sdk.pay.demo.PayCallBack;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.accessories.city.R;
//import CourseInfo;
//import PayCourseInfo;
//import BaseFragment;
//import RequestHelp;
//import PayCourseInfoParse;
//import PayPopupwidow;
//import com.volley.req.net.HttpURL;
//import com.volley.req.net.RequestManager;
//import com.volley.req.net.RequestParam;
//import com.volley.req.parser.JsonParserBase;
//
//import java.util.Map;
//
///**
// * @author czq
// * @desc 请用一句话描述它
// * @date 16/4/27
// */
//public class PayRequestUtils implements View.OnClickListener{
//
//    private PayPopupwidow payPopupwidow;
//    private int payType = 1;//1 alipay,  2 weixin
//    private BaseFragment baseFragment;
//    private CourseInfo courseInfo;
//
//    private String orderPay;
//    private String trueMoney;
//    private String payCont;
//    private String remark;
//    private PayCallBack payCallBack;
//    public int reqestType = 1;//1 需要生成订单,2 不要生成订单
//
//
//    public PayRequestUtils(BaseFragment baseFragment,CourseInfo courseInfo,PayCallBack payCallBack){
//        this.payCallBack = payCallBack;
//        this.courseInfo = courseInfo;
//        this.baseFragment = baseFragment;
//        payPopupwidow = new PayPopupwidow(baseFragment.getActivity(),this,null);
//    }
//
//    public void payPopShow(View v,String orderPay,String trueMoey,String payCount,String remark){
//        payPopupwidow.payPopShow(v,null);
//        this.orderPay = orderPay;
//        this.trueMoney =trueMoey;
//        this.payCont = payCount;
//        this.remark = remark;
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getHeight()){
//
//        case R.id.alipay://支付宝支付
//        payType = 1;
//        break;
//        case R.id.wxPay://微信支付
//        payType = 2;
//        break;
//        }
//        if(reqestType == 1){
//            requestData();
//            payPopupwidow.dimiss();
//        }
//    }
//
//    public void requestData() {
//        // TODO Auto-generated method stub
//        HttpURL url = new HttpURL();
//        url.setmBaseUrl(URLConstants.BASE_URL);
//
//        Map postParams = RequestHelp.getBaseParaMap("PayCourseOrder");
//        postParams.put("payType", payType);
//        postParams.put("studentName", BaseApplication.getUserInfo().getNickName());
//        postParams.put("teacherId", courseInfo.getTeacherId());
//        postParams.put("teacherName", courseInfo.getTeacherName());
//        postParams.put("courseId", courseInfo.getCourseId());
//        postParams.put("orderPrice", orderPay);
//        postParams.put("payPrice", trueMoney);
//        postParams.put("payCount", payCont);
//        postParams.put("remark", remark);
//
//        RequestParam param = new RequestParam();
////        param.setmParserClassName(HomePageBannerParse.class.getName());
//        param.setmParserClassName(new PayCourseInfoParse());
//        param.setmPostarams(postParams);
//        param.setmHttpURL(url);
//        param.setPostRequestMethod();
//        RequestManager.getRequestData(baseFragment.getContext(), createReqSuccessListener(), createMyReqErrorListener(), param);
//    }
//
//
//    public void handleRspSuccess(int requestType,Object obj) {
//        PayCourseInfo payCourseInfo =( (JsonParserBase<PayCourseInfo>)obj).getData();
//        if(payCourseInfo != null){
//
//            if(payType == 1){
//                //todo 弹出对话框,选择支付方式
//                AlipayUtil alipayUtil = new AlipayUtil(baseFragment.getActivity(),payCourseInfo.getOrderCode(),"test",payCourseInfo.getCourseName(),payCourseInfo.getPayPrice(),null);
//                alipayUtil.alipay();
//            }else {
//                //账号支付
//            }
//
//        }
//    }
//
//    protected Response.Listener<Object> createReqSuccessListener(final int requestType) {
//        return new Response.Listener<Object>() {
//            @Override
//            public void onResponse(Object object) {
//                baseFragment.dismissLoadingDilog();
//                if (object != null){
//                    JsonParserBase jsonParserBase = (JsonParserBase) object;
//                    if(jsonParserBase != null && URLConstants.SUCCESS_CODE.equals(jsonParserBase.getResult())){
//
//                            try {
//                                handleRspSuccess(requestType,jsonParserBase);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                    }else{
//                            baseFragment.toasetUtil.showInfo( jsonParserBase.getMessage());
//                    }
//
//
//                }
//            }
//        };
//    }
//
//    protected Response.Listener<Object> createReqSuccessListener() {
//        return createReqSuccessListener(0);
//    }
//
//    protected Response.ErrorListener createMyReqErrorListener() {
//        return new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                AppLog.Loge(" data failed to load " + error.getMessage());
//                baseFragment.dismissLoadingDilog();
//                    baseFragment.toasetUtil.showErro(R.string.loading_fail_server);
//            }
//        };
//    }
//
//}
