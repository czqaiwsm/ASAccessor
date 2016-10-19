package com.accessories.city.fragment.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.activity.home.TeaCourseSettingActivity;
import com.accessories.city.adapter.CourseItemAdpter;
import com.accessories.city.bean.CourseItem;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.PullRefreshStatus;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.AlertDialogUtils;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.utils.WaitLayer;
import com.accessories.city.view.CustomListView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.Map;

/**
 * @desc 老师信息-->课程列表
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class CourseListFragment extends BaseFragment implements RequsetListener,CustomListView.OnLoadMoreListener {

    private CustomListView customListView = null;
    private TextView noData;
    private ArrayList<CourseItem> list = new ArrayList<CourseItem>();
    private CourseItemAdpter adapter;

    private boolean isPrepare = false;
    private boolean isVisible = false;
    private int pageNo = 1;
    private int courseId = 100;//课程
    private int pageCount = 0;//总页数
    private int pageSize = 10;//每页的数据量
    private PullRefreshStatus status = PullRefreshStatus.NORMAL;

    private String id = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);
        isPrepare = true;
//        onLazyLoad();
        requestTask(1);
    }


    private void initView(View view){

        customListView = (CustomListView)view.findViewById(R.id.callListView);
        noData = (TextView)view.findViewById(R.id.noData);

        customListView.setCanRefresh(true);
        customListView.setCanLoadMore(false);
        adapter = new CourseItemAdpter(mActivity, list);
        customListView.setAdapter(adapter);
        customListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status = PullRefreshStatus.PULL_REFRESH;
                pageNo = 1;
                requestData(1);
            }
        });

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                AlertDialogUtils.displayMyAlertChoice(mActivity, "提示", "是否删除此课程?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id = list.get(i-1).getId();
                        requestTask(2);
                    }
                }, null);
            }
        });
    }


    private void initTitle(){
        setTitleText("我设置的课程");
        setLeftHeadIcon(0);
        setRightHeadIcon(R.drawable.add_course, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toClassActivity(CourseListFragment.this, TeaCourseSettingActivity.class.getName());
            }
        });

    }

    private void onLazyLoad(){

        if(!isPrepare || !isVisible){
            return;
        }
        requestTask(1);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
        }else {
            isVisible = false;
            if(isPrepare){
                dismissLoadingDilog();
            }
        }

        onLazyLoad();
    }

    @Override
    public void onLoadMore() {
        status = PullRefreshStatus.LOAD_MORE;
        pageNo++;
        requestData(1);
    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        RequestParam param = new RequestParam();
        Map postParams = null;
        switch (requestType){
            case 1:
                postParams = RequestHelp.getBaseParaMap("CourseList");
//                param.setmParserClassName(new CourseListParse());
                break;
            case 2:
                postParams = RequestHelp.getBaseParaMap("DeleteCourse");
                postParams.put("id",id);
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

            case  1:
                JsonParserBase<ArrayList<CourseItem>> jsonParserBase = (JsonParserBase<ArrayList<CourseItem>>)obj;
                ArrayList<CourseItem> courseItems = jsonParserBase.getObj();
                if(courseItems != null){

                    switch (status){
                        case NORMAL:
                            refresh(courseItems);
                            break;

                        case PULL_REFRESH:
                            refresh(courseItems);
                            customListView.onRefreshComplete();
                            break;
                        case LOAD_MORE:
                            if(courseItems != null && courseItems.size()>0){//有数据
                                list.addAll(courseItems);
                                customListView.onLoadMoreComplete();
                                adapter.notifyDataSetInvalidated();
                            }else {
                                pageNo--;
                                customListView.onLoadMoreComplete();
                            }
                            break;
                        default:break;
                    }

                }
                break;
            case 2:
                CourseItem selectMsg = null;
                for (CourseItem msgDetail : list) {
                    if (TextUtils.equals(id, msgDetail.getId())) {
                        selectMsg = msgDetail;
                        break;
                    }
                }

                if (selectMsg != null) {
                    list.remove(selectMsg);
                }
                adapter.notifyDataSetChanged();
                toasetUtil.showSuccess("删除成功!");
                break;

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
                customListView.onLoadMoreComplete();
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
     * @param courseItems
     */
    private void refresh(ArrayList<CourseItem> courseItems){
        if(courseItems==null || courseItems.size()==0){//显示无数据
            if(list.size()==0){
                noData.setVisibility(View.VISIBLE);
            }
        }else {
            noData.setVisibility(View.GONE);
            list.clear();
            list.addAll(courseItems);
//            if(courseItems.size()>=pageSize){//有足够的数据,可以下拉刷新
//                customListView.setCanLoadMore(true);
//                customListView.setOnLoadListener(this);
//            }else {
//                customListView.setCanLoadMore(false);
//            }
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            requestTask(1);
        }
    }
}