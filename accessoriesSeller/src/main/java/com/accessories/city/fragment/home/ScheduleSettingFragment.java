package com.accessories.city.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.accessories.city.R;
import com.accessories.city.activity.home.MultiChooseActivity;
import com.accessories.city.bean.DataMapConstants;
import com.accessories.city.bean.QueryStudentInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.SmartToast;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.view.SingelPickerView;
import com.pickerview.TimePickerView;
import com.pickerview.listener.OnItemSelectedListener;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * @desc 课程设置
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class ScheduleSettingFragment extends BaseFragment implements View.OnClickListener,RequsetListener,OnItemSelectedListener,TimePickerView.OnTimeSelectListener{


    @Bind(R.id.studentName)
    TextView studentName;
    @Bind(R.id.studentNameRl)
    RelativeLayout studentNameRl;
    @Bind(R.id.subjectName)
    TextView subjectName;
    @Bind(R.id.subjectRL)
    RelativeLayout subjectRL;
    @Bind(R.id.joniorName)
    TextView joniorName;
    @Bind(R.id.joniorRl)
    RelativeLayout joniorRl;
    @Bind(R.id.frequencyName)
    TextView frequencyName;
    @Bind(R.id.frequencyRl)
    RelativeLayout frequencyRl;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.timeRl)
    RelativeLayout timeRl;
    @Bind(R.id.sure)
    TextView sure;

    private ArrayList<QueryStudentInfo> queryStudentInfos;
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> courseIdList = new ArrayList<String>();//课程ID
    private ArrayList<String> courseNameList = new ArrayList<String>();//课程名称列表

    private int nameSelect = 0;//选择的学生位置
    private int courseSelect = 0;//选择的课程位置
    private int wheelType = 1;//1 姓名  2课程


    private SingelPickerView singelPickerView ;
    private TimePickerView timePickerView;

    private ArrayList<String> selectList = new ArrayList<String>();//选择的频次

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_scheduel_setting, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle();
        initView(view);
        timePickerView = new TimePickerView(mActivity);
        String[]  ids = mActivity.getResources().getStringArray(R.array.frequency_id);
        selectList.add(ids[0]);
        frequencyName.setText(DataMapConstants.getFrequency().get(ids[0]));

        singelPickerView = new SingelPickerView(mActivity);
        singelPickerView.setOnItemSelectListener(this);
        singelPickerView.setOnItem(new SingelPickerView.SelectOnItem() {
            @Override
            public void itemSelect(int selectIndex) {
                if(wheelType == 1){
                    nameSelect = selectIndex;
                    studentName.setText(names.get(selectIndex));
                    joniorName.setText(queryStudentInfos.get(selectIndex).getGrade());
                    setCourseData(selectIndex);
                }else {
                    courseSelect = selectIndex;
                    subjectName.setText(courseNameList.get(selectIndex));
                }
            }
        });

        timePickerView.setOnTimeSelectListener(this);
        requestTask(1);
    }

    private void initView(View view) {
        subjectRL.setOnClickListener(this);
        studentNameRl.setOnClickListener(this);
        frequencyRl.setOnClickListener(this);
        timeRl.setOnClickListener(this);
        sure.setOnClickListener(this);
        sure.setClickable(false);
        setData();
    }


    private void initTitle() {
        setTitleText(R.string.course_setting);
        setLeftHeadIcon(0);
    }

    private void setData() {
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.studentNameRl:
                if(names!=null && names.size()>0){
                    wheelType = 1;
                    singelPickerView.setAdapter(names);
                    singelPickerView.setCurrent(nameSelect);
                    singelPickerView.pickShow();
                }else {
//                    toasetUtil.showInfo("无相关类容!");
                    SmartToast.showText("无相关类容!");
                }
                break;
            case R.id.subjectRL:
                if(courseNameList!=null && courseNameList.size()>0){
                    wheelType = 2;
                    singelPickerView.setAdapter(courseNameList);
                    singelPickerView.setCurrent(courseSelect);
                    singelPickerView.pickShow();
                }else {
                    SmartToast.showText("无相关类容!");

//                    toasetUtil.showInfo("无相关类容!");
                }
                break;
            case R.id.frequencyRl:
                intent = new Intent(mActivity, MultiChooseActivity.class);
                 intent.putStringArrayListExtra("selectId",selectList);
                startActivityForResult(intent,111);
                break;
            case R.id.timeRl:
                timePickerView.show();
                break;
            case R.id.sure:
                if(names == null || names.isEmpty()){
                    SmartToast.showText("数据不完整!");
//                    toasetUtil.showInfo("数据不完整");

                    return;
                }

                 if(TextUtils.isEmpty(getFreId())){
                     SmartToast.showText("请选择课程!");
//                     toasetUtil.showInfo("请选择课程");
                     return;
                 }

                if(TextUtils.isEmpty(time.getText())){
//                    toasetUtil.showInfo("请设置时间");
                    SmartToast.showText("请设置时间!");
                    return;
                }

                requestTask(2);
                break;
            default:
                break;
        }
    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        RequestParam param = new RequestParam();
        Map postParams = null;
        switch (requestType){
            case 1:
                postParams = RequestHelp.getBaseParaMap("QueryStudent");
//                param.setmParserClassName(new QueryStudentParse());
                break;
            case 2:
                postParams = RequestHelp.getBaseParaMap("ScheduleSetting");
                postParams.put("courseId",courseIdList.get(courseSelect));   //课程id
                postParams.put("studentId",queryStudentInfos.get(nameSelect).getStudentId());  //学生id
                postParams.put("studentName",queryStudentInfos.get(nameSelect).getStudentName());//学生姓名
                postParams.put("grade",queryStudentInfos.get(nameSelect).getGrade());      // 年级
                postParams.put("weeks",getFreId());      // 上课周
                postParams.put("startTime",time.getText().toString());  //开始时间
//                param.setmParserClassName(new BaseParse());

                break;
        }
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        switch (requestType){
            case 1:
                JsonParserBase<ArrayList<QueryStudentInfo>> jsonParserBase = (JsonParserBase<ArrayList<QueryStudentInfo>>) obj;
                if (jsonParserBase != null) {
                    queryStudentInfos = jsonParserBase.getObj();
                    names.clear();
                    if(queryStudentInfos != null){
                        sure.setClickable(true);
                        for(QueryStudentInfo info:queryStudentInfos){
                            names.add(TextUtils.isEmpty(info.getStudentName())?"":info.getStudentName());
                        }

                    }
                    if(names.size()>0){
                        nameSelect = 0;
                        studentName.setText(names.get(0));

                        setCourseData(nameSelect);
                        wheelType = 1;
                        singelPickerView.setAdapter(names);

                    }else {
                        studentName.setText("");
                        joniorName.setText("");
                    }

                }
                break;

            case 2:
                SmartToast.showText("课程设置成功");
//                toasetUtil.showInfo("课程设置成功");
                mActivity.setResult(Activity.RESULT_OK);
                mActivity.finish();
                break;
        }

    }


    private String getFreId(){
        String frequency = "";
        for(int i=0;i<selectList.size();i++){
            if(i==selectList.size()){
                frequency += DataMapConstants.getFrequency().get(selectList.get(i))+"";
            }else {
                frequency += DataMapConstants.getFrequency().get(selectList.get(i))+",";
            }
        }

        return frequency;
    }

    @Override
    public void onTimeSelect(String date) {
        time.setText(date);
    }

    private void setCourseData(int select){
        String ids = queryStudentInfos.get(select).getCourseId();

        courseIdList.clear();
        courseNameList.clear();
        if(!TextUtils.isEmpty(ids)){
            Collections.addAll(courseIdList,ids.split(","));
        }

        ids = queryStudentInfos.get(select).getCourseName();
        if(!TextUtils.isEmpty(ids)){
            Collections.addAll(courseNameList,ids.split(","));
        }

        joniorName.setText(queryStudentInfos.get(select).getGradeName());
        subjectName.setText(courseNameList.get(0));
    }

    @Override
    public void onItemSelected(int index) {
        if(wheelType==1){
//            setCourseData(nameSelect);
        }else {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode==111){
                selectList = data.getStringArrayListExtra("selectId");
                String frequency = "";
                if(selectList != null && selectList.size()>0){
                    Collections.sort(selectList);

                    for(int i=0;i<selectList.size();i++){
                        if(i != selectList.size()-1){
                            frequency += DataMapConstants.getFrequency().get(selectList.get(i))+" ";
                        }else {
                            frequency +=  DataMapConstants.getFrequency().get(selectList.get(i));
                        }
                    }
                }
                frequencyName.setText(frequency);
            }

          setData();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}