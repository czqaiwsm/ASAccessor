package com.accessories.city.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.accessories.city.R;
import com.accessories.city.activity.teacher.ChooseJoinorActivity;
import com.accessories.city.bean.CertifyStatus;
import com.accessories.city.bean.DataMapConstants;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.SmartToast;
import com.accessories.city.utils.URLConstants;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.net.inferface.IParser;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;
import org.json.JSONObject;

import java.util.Map;

/**
 * @desc 教师首页
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class TeacherCourseSettingFragment extends BaseFragment implements View.OnClickListener ,RequsetListener,IParser{

    @Bind(R.id.idCertify_status)
    TextView subject;
    @Bind(R.id.idCertifyRl)
    RelativeLayout subjectRl;
    @Bind(R.id.schoolCertify_status)
    TextView jonior;
    @Bind(R.id.chooseCertifyRl)
    RelativeLayout joniorRl;

    private String joniorId = "";
    private String subjectId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_course_setting, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle();
        initView(view);
    }

    private void initView(View view) {
        subjectRl.setOnClickListener(this);
        joniorRl.setOnClickListener(this);
    }


    private void initTitle() {
        setTitleText(R.string.course_setting);
        setLeftHeadIcon(0);
        setHeaderRightText(R.string.sure, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestTask();
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.idCertifyRl://科目
                intent = new Intent(mActivity, ChooseJoinorActivity.class);
                intent.putExtra("joniorId",subjectId);
                intent.setFlags(14);
                startActivityForResult(intent, URLConstants.CHOOSE_SUBJECT_REQUEST_CODE);
                break;
            case R.id.chooseCertifyRl://年级
//                intent = new Intent(mActivity, ChooseJoinorActivity.class);
                intent = new Intent(mActivity, ChooseJoinorActivity.class);
                intent.putExtra("joniorId",joniorId);
                intent.setFlags(11);
                startActivityForResult(intent, URLConstants.CHOOSE_JOINOR_REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case URLConstants.CHOOSE_SUBJECT_REQUEST_CODE://科目
                    subjectId = data.getStringExtra(URLConstants.CHOOSE);
                    subject.setText(DataMapConstants.getCourse().get(subjectId));
                    break;
                case URLConstants.CHOOSE_JOINOR_REQUEST_CODE://年级
                    joniorId = data.getStringExtra(URLConstants.CHOOSE);
                    jonior.setText(DataMapConstants.getJoniorMap().get(joniorId));
                    break;
            }

        }

    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("CourseDesign") ;
                postParams.put("courseId",subjectId);
                postParams.put("grade",joniorId);
        RequestParam param = new RequestParam();
//        param.setmParserClassName(this);
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(),createMyReqErrorListener(), param);

    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        JsonParserBase<CertifyStatus> jsonParserBase = (JsonParserBase<CertifyStatus>)obj;
                           SmartToast.showText("设置成功");
        mActivity.setResult(Activity.RESULT_OK);
        mActivity.finish();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Object fromJson(JSONObject object) {
        return null;
    }

    @Override
    public Object fromJson(String json) {
        JsonParserBase result = ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase>() {
        }.getType());
        if(URLConstants.SUCCESS_CODE.equals(result.getResult())){
            return ParserUtil.fromJsonBase(json, new TypeToken<JsonParserBase<CertifyStatus>>() {
            }.getType());
        }
        return result;
    }
}