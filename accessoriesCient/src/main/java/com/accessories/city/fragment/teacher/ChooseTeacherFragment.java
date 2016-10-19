package com.accessories.city.fragment.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.activity.home.ChooseActivity;
import com.accessories.city.adapter.ChooseTeacherAdpter;
import com.accessories.city.bean.ChooseTeachBean;
import com.accessories.city.bean.TeacherInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.PullRefreshStatus;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.utils.WaitLayer;
import com.accessories.city.view.CustomListView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc 首页--》选项选择老师
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class ChooseTeacherFragment extends BaseFragment implements RequsetListener,CustomListView.OnLoadMoreListener {

    private CustomListView customListView = null;
    private List<TeacherInfo> list = new ArrayList<TeacherInfo>();
    private TextView noData ;
    private ChooseTeacherAdpter adapter;
    private int pageNo = 1;
    private int courseId = 100;//课程
    private int pageCount = 0;//总页数
    private int pageSize = 10;//每页的数据量
    private PullRefreshStatus status = PullRefreshStatus.NORMAL;

    private String cityName;
    private String joniorId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if(intent != null && intent.hasExtra(URLConstants.COURSEID)){
            courseId = intent.getIntExtra(URLConstants.COURSEID,100);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title_list,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initTitle();
        cityName = BaseApplication.getInstance().location[0];
//        joniorId = BaseApplication.getUserInfo()!=null
//        ?BaseApplication.getUserInfo().getGrade():"";
        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);
        requestTask();
    }

    private void initTitle(){
        setTitleText(BaseApplication.getInstance().location[0]);
        setLeftHeadIcon(0);
        setHeaderRightText(R.string.choose, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toClassActivity(ChooseTeacherFragment.this, ChooseActivity.class.getName());
            }
        });
    }

    private void initView(View view){

        customListView = (CustomListView)view.findViewById(R.id.callListView);
        noData = (TextView)view.findViewById(R.id.noData);
        customListView.setCanRefresh(true);
        adapter = new ChooseTeacherAdpter(mActivity, list);
        customListView.setAdapter(adapter);

        customListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status = PullRefreshStatus.PULL_REFRESH;
                pageNo = 1;
                requestData(0);
            }
        });
    }

    @Override
    public void onLoadMore() {
        status = PullRefreshStatus.LOAD_MORE;
        pageNo++;
        requestData(0);
    }


    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("TeacherList");
//        @"cityName", [ @"pageNo",@"courseId",@"grade", @"cmd",@"123456",@"vcode",@"",@"fInviteCode",@"000000",@"deviceId",@"10",@"appversion",@"4",@"clientType",[[UserInfoManage shareInstance] token],@"accessToken", nil];
        postParams.put("cityName", cityName);
        postParams.put("pageNo",pageNo);
        postParams.put("courseId",courseId);
        postParams.put("grade",joniorId);
        RequestParam param = new RequestParam();
//        param.setmParserClassName(ChooseTeaBeanParse.class.getName());
//        param.setmParserClassName(new ChooseTeaBeanParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        JsonParserBase<ChooseTeachBean> jsonParserBase = (JsonParserBase<ChooseTeachBean>)obj;
        ChooseTeachBean chooseTeachBean = jsonParserBase.getObj();
        if(chooseTeachBean != null){
            pageCount = chooseTeachBean.getTotalPages();
            pageSize = chooseTeachBean.getPageSize();
            ArrayList<TeacherInfo> teacherInfos = chooseTeachBean.getElements();

            switch (status){
                case NORMAL:
                    refresh(teacherInfos);
                    break;

                case PULL_REFRESH:
                    refresh(teacherInfos);
                    customListView.onRefreshComplete();
                    break;
                case LOAD_MORE:
                    if(teacherInfos != null && teacherInfos.size()>0){//有数据
                        list.addAll(teacherInfos);
                        customListView.onLoadMoreComplete(CustomListView.ENDINT_MANUAL_LOAD_DONE);
                        adapter.notifyDataSetInvalidated();
                    }else {
                        pageNo--;
                        customListView.onLoadMoreComplete(CustomListView.ENDINT_AUTO_LOAD_NO_DATA);
                    }
                    break;
                default:break;
            }


        }
    }

    @Override
    protected void failRespone() {
        super.failRespone();
        switch (status) {
            case PULL_REFRESH:
                customListView.onRefreshComplete();
                break;
            case LOAD_MORE:
                pageNo--;
                customListView.onLoadMoreComplete(CustomListView.ENDINT_MANUAL_LOAD_DONE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void errorRespone() {
        super.errorRespone();
        failRespone();
    }

    /**
     * 页数为1时使用
     * @param teacherInfos
     */
    private void refresh(ArrayList<TeacherInfo> teacherInfos){
        if(teacherInfos==null || teacherInfos.size()==0){//显示无数据
            if(list.size()==0){
                noData.setVisibility(View.VISIBLE);
            }
        }else {
            noData.setVisibility(View.GONE);
            list.clear();
            list.addAll(teacherInfos);
            if(teacherInfos.size()>=pageSize){//有足够的数据,可以下拉刷新
                customListView.setCanLoadMore(true);
                customListView.setOnLoadListener(this);
            }else {
                customListView.setCanLoadMore(false);
            }
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(data != null){

                cityName = data.hasExtra("cityName")?data.getStringExtra("cityName"):"";
                joniorId = data.hasExtra("joniorId")?data.getStringExtra("joniorId"):"";
                setTitleText(cityName);
                status = PullRefreshStatus.NORMAL;
                requestTask();
            }


        }
    }
}