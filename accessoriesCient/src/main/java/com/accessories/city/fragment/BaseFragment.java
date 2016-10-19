package com.accessories.city.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.accessories.city.R;
import com.accessories.city.activity.BaseActivity;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.AppLog;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.utils.WaitLayer;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.toast.ToasetUtil;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

/**
 * @desc 公共的Fragment，初始化公共标题部分；如果不需要公共标题，可隐藏
 * @creator caozhiqing
 * @data 2016/3/10
 */
public abstract class BaseFragment extends Fragment{

    private View titleView = null;
    private TextView headerTitle;
    private ImageView headerLeftIcon;
    private ImageView headerRightIcon;
    private ImageView heederRightMore;
    private TextView headerRightText;
    protected BaseActivity mActivity;

    protected RequsetListener requsetListener = null;
    private WaitLayer loadingDilog;//默认是与界面绑定对话框

    public ToasetUtil toasetUtil = null;
    protected final int CANCEL = -1;
    protected final int SHOW_DIALOG = 0;

    protected final int SHOW_SUCCESS = 1;
    protected final int SHOW_INFO = 2;
    protected final int SHOW_ERROR = 3;


    protected Handler handler = new Handler(){


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CANCEL:
                    dismissLoadingDilog();
                    break;
                case SHOW_DIALOG:
                    showLoadingDilog("");
                    break;
                case SHOW_SUCCESS:
                    if(msg != null && msg.obj !=null){
                        toasetUtil.showSuccess(msg.toString());
                    }else {
                        toasetUtil.showSuccess("");
                    }
                    break;
                case SHOW_INFO:
                    if(msg != null && msg.obj !=null){
                        toasetUtil.showInfo(msg.toString());
                    }
                    break;
                case SHOW_ERROR:
                    if(msg != null && msg.obj !=null){
                        toasetUtil.showErro(msg.toString());
                    }else {
                        toasetUtil.showErro("");
                    }
                    break;
            }

        }
    };


    protected void requestData(int requestTyep){
    }

    protected void requestTask(){
        requestTask(0);
    }

    protected void requestTask(int requestType){
        if (loadingDilog != null && !loadingDilog.isShow()){
            showLoadingDilog(null);
            handler.sendEmptyMessageDelayed(CANCEL,15000);
        }
        requestData(requestType);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity)getActivity();
        loadingDilog = new WaitLayer(mActivity, WaitLayer.DialogType.MODAL);
        toasetUtil = mActivity.toasetUtil;
    }




    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInitTitleView(view);

        if (this instanceof RequsetListener){
            requsetListener = (RequsetListener) this;
        }
    }

    protected void setLoadingDilog(WaitLayer.DialogType type){
        if(loadingDilog != null && loadingDilog.getDialogType()==type){
            return;
        }
        dismissLoadingDilog();
        loadingDilog = new WaitLayer(mActivity, type);
    }

    protected void showLoadingDilog(String msg){
        if(loadingDilog != null && !loadingDilog.isShow()){
            loadingDilog.show(msg);
        }
    }

    public void dismissLoadingDilog(){
        if(loadingDilog != null && loadingDilog.isShow()){
            loadingDilog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 设置头部是否可见
     *
     * @param visible
     */
    protected void setTitlteVisible(int visible) {
        titleView.setVisibility(visible);
    }

    private void onInitTitleView(View view) {
        headerTitle = (TextView) view.findViewById(R.id.header_title);
        headerLeftIcon = (ImageView) view.findViewById(R.id.header_left_icon);
        headerRightIcon = (ImageView) view.findViewById(R.id.header_right_icon);
        heederRightMore = (ImageView) view.findViewById(R.id.header_right_more);
        headerRightText = (TextView) view.findViewById(R.id.header_rignt_text);

    }

    /**
     *
     * @param stringId
     */
    protected void setTitleText(int stringId) {
        if (null != headerTitle) {
            headerTitle.setText(stringId);
        }
    }

    /**
     * @param string
     */
    protected void setTitleText(String string) {
        if (null != headerTitle) {
            headerTitle.setText(string);
        }
    }

    protected void setTitleOnClickListener(View.OnClickListener onClickListener) {
        if (null != headerTitle) {
            headerTitle.setClickable(true);
            headerTitle.setOnClickListener(onClickListener);
        }
    }

    /**
     * @param drawableId
     */
    protected void setLeftHeadIcon(int drawableId) {
        if (null != headerLeftIcon) {
            if (drawableId == -1) {
                headerLeftIcon.setVisibility(View.GONE);
            } else {
                headerLeftIcon.setVisibility(View.VISIBLE);
                if (drawableId != 0) {
                    headerLeftIcon.setImageResource(drawableId);
                }
                headerLeftIcon.setEnabled(true);
                headerLeftIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Log.e(">>>>>>>>>>>>","onCLicek");
                        mActivity.finish();
                    }
                });

            }
        }

    }

    /*
     * @param stringId
     */
    protected void setLeftHeadIcon(int drawableId, View.OnClickListener onClickListener) {
        if (null != headerLeftIcon) {
            headerLeftIcon.setVisibility(View.VISIBLE);
            if (drawableId != 0) {
                headerLeftIcon.setImageResource(drawableId);
            }
            headerLeftIcon.setOnClickListener(onClickListener);
        }

    }

    /**
     * @param drawableId
     */
    protected void setRightHeadIcon(int drawableId, View.OnClickListener listener) {

        if (null != headerRightIcon) {
            headerRightIcon.setVisibility(View.VISIBLE);
            headerRightIcon.setImageResource(drawableId);
            headerRightIcon.setOnClickListener(listener);
        }
    }

    /**
     * @param drawableId
     */
    public void setRightHeadIcon(int drawableId) {
        if (null != headerRightIcon) {
            headerRightIcon.setVisibility(View.VISIBLE);
            headerRightIcon.setImageResource(drawableId);
            headerRightIcon.setClickable(true);
        }
    }

    public View getRightHeadView() {
        if (null != heederRightMore) {
            return heederRightMore;
        }
        return null;
    }

    /**
     * @param drawableId
     */
    protected void setRightMoreIcon(int drawableId, View.OnClickListener listener) {
        if (null != heederRightMore) {
            heederRightMore.setVisibility(View.VISIBLE);
            heederRightMore.setImageResource(drawableId);
            heederRightMore.setOnClickListener(listener);
        }
    }

    protected void setHeaderRightText(String txt, View.OnClickListener listener){
        headerRightText.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(txt)){
            headerRightText.setText(txt);
        }
        if(listener != null){
            headerRightText.setOnClickListener(listener);
        }
    }
    protected void setHeaderRightText(int id, View.OnClickListener listener){
        headerRightText.setVisibility(View.VISIBLE);
        headerRightText.setText(id);
        if(listener != null){
            headerRightText.setOnClickListener(listener);
        }
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if(hidden){
//            dismissLoadingDilog();
//        }
//    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissLoadingDilog();
        toasetUtil.dismissToast();
    }

    /**
     * 普通的跳转页面，无需携带任何参数
     *
     * @param fragment
     * @param className
     */
    public static void toClassActivity(Fragment fragment, String className) {
        Intent intent = null;
        try {
            intent = new Intent(fragment.getActivity(), Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (intent != null) {
            fragment.startActivityForResult(intent, 0);
        }
    }


    protected Response.Listener<Object> createReqSuccessListener(final int requestType) {
        return new Response.Listener<Object>() {
            @Override
            public void onResponse(Object object) {
                dismissLoadingDilog();
                if (object != null){
                    if(object instanceof  JsonParserBase){
                        JsonParserBase jsonParserBase = (JsonParserBase) object;
                        if(jsonParserBase != null && URLConstants.SUCCESS_CODE.equals(jsonParserBase.getResult())){

                            if(requsetListener != null){
                                try {
                                    requsetListener.handleRspSuccess(requestType,jsonParserBase);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }else{
                            if(!isDetached()){
                                Toast.makeText(BaseApplication.getInstance(),jsonParserBase!=null?jsonParserBase.getMessage():"无有效数据!",Toast.LENGTH_SHORT).show();
//                            toasetUtil.showInfo(jsonParserBase!=null?jsonParserBase.getMessage():"无有效数据!");
                            }
                            failRespone();

                        }
                    }else {
                        JsonParserBase jsonParserBase =  ParserUtil.fromJsonBase(object.toString(), new TypeToken<JsonParserBase>() {
                        }.getType());
                        if(jsonParserBase != null && URLConstants.SUCCESS_CODE.equals(jsonParserBase.getResult())){
                            if(requsetListener != null){
                                    if(!isDetached() && getView() != null )
                                        requsetListener.handleRspSuccess(requestType,object.toString());
                            }

                        }else{
                            failRespone();
                            toasetUtil.showInfo( jsonParserBase.getMessage().toString());
                        }
                    }
                }
            }
        };
    }

    protected Response.Listener<Object> createReqSuccessListener() {
        return createReqSuccessListener(0);
    }

    protected Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.Loge(" data failed to load " + error.getMessage());
                dismissLoadingDilog();
                if(!isDetached()){
                    toasetUtil.showErro(R.string.loading_fail_server);
                }
                errorRespone();
            }
        };
    }

    /**
     * 当接口返回的code不是成功的code(200)时执行.
     */
   protected void failRespone(){

   }

    /**
     * 请求失败
     */
    protected void errorRespone(){

    }

}