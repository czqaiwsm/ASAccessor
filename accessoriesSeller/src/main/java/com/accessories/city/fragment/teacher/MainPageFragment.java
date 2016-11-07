package com.accessories.city.fragment.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.activity.teacher.ChatMsgActivity;
import com.accessories.city.bean.ChatMsgEntity;
import com.accessories.city.bean.TeacherDetailInfo;
import com.accessories.city.bean.UserInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.utils.WaitLayer;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;

import java.util.Map;

/**
 * @desc 教师首页
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class MainPageFragment extends BaseFragment implements View.OnClickListener,RequsetListener {
    private TeacherDetailInfo teacherDetailInfo;
    private TextView school, simpe_indu, address, certify, apputation;
    private RelativeLayout ask_rl ;//咨询
    private RelativeLayout seek_rl;//关注
    private Button  ask_btn ;
    private Button  seek_btn ;
    private TextView ask_txt;
    private TextView seek_txt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null ){
            teacherDetailInfo = (TeacherDetailInfo) bundle.getSerializable("teacherInfo");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main_page,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);

    }

    private void initView(View view){
        school = (TextView)view.findViewById(R.id.school);
        simpe_indu = (TextView)view.findViewById(R.id.simpe_indu);
        address = (TextView)view.findViewById(R.id.address);
        certify = (TextView)view.findViewById(R.id.certify);
        apputation = (TextView)view.findViewById(R.id.apputation);
        ask_rl = (RelativeLayout)view.findViewById(R.id.ask_rl);
        ask_txt = (TextView)view.findViewById(R.id.ask_txt);
        ask_btn = (Button)view.findViewById(R.id.ask_btn);
        seek_rl = (RelativeLayout)view.findViewById(R.id.seek_rl);
        seek_txt = (TextView)view.findViewById(R.id.seek_txt);
        seek_btn = (Button)view.findViewById(R.id.seek_btn);
        seek_btn.setClickable(false);
        seek_txt.setClickable(false);
        seek_rl.setOnClickListener(this);
        ask_btn.setClickable(false);
        ask_txt.setClickable(false);
        ask_rl.setOnClickListener(this);

        if(teacherDetailInfo != null){
            school.setText(teacherDetailInfo.getCollege());
            simpe_indu.setText(teacherDetailInfo.getIntroduction());
            address.setText(teacherDetailInfo.getHname()+" "+teacherDetailInfo.getAname());
            String str = "";//认证
            if("1".equalsIgnoreCase(teacherDetailInfo.getIsIdCardVip())){
                str = "身份认证";
            }
            if("1".equalsIgnoreCase(teacherDetailInfo.getIsSchoolVip())){
                str += " 学历认证";
            }
            certify.setText(str);
            apputation.setText(teacherDetailInfo.getExperience());

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ask_rl:
                UserInfo userInfo = BaseApplication.getUserInfo();
                Intent intent = new Intent(mActivity, ChatMsgActivity.class);

                intent.putExtra("teacherId",teacherDetailInfo.getId());
                ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
                chatMsgEntity.setDirection("2");
                chatMsgEntity.setReceiverId(teacherDetailInfo.getId());
//                chatMsgEntity.setSenderId(userInfo.getId());

                chatMsgEntity.setStudentName(teacherDetailInfo.getNickName());
                chatMsgEntity.setTeacherImg(teacherDetailInfo.getHeadImg());
                intent.putExtra("bundle",chatMsgEntity);
                startActivity(intent);
                break;
            case R.id.seek_rl:
                cliclAble(false);
                requestTask();
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
        Map postParams = RequestHelp.getBaseParaMap("Attention");//关注
        postParams.put("teacherId",teacherDetailInfo.getId());
        postParams.put("isCheck",ask_txt.isSelected()?1:2);//Int	1-取消，2-关注
        RequestParam param = new RequestParam();
//        param.setmParserClassName(TeacherDetailParse.class.getName());
//        param.setmParserClassName(new TeacherDetailParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj)  {
        cliclAble(true);
        seek_btn.setSelected(!seek_btn.isSelected());
        seek_txt.setSelected(!seek_txt.isSelected());

    }

    @Override
    protected void failRespone() {
        super.failRespone();
        cliclAble(true);
    }

    @Override
    protected void errorRespone() {
        super.errorRespone();
        cliclAble(true);
    }

    private void cliclAble(boolean isClik){
        seek_rl.setClickable(isClik);

    }

}