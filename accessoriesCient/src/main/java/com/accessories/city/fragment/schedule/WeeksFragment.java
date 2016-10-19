package com.accessories.city.fragment.schedule;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.adapter.WeeksAdpter;
import com.accessories.city.bean.CourseInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.AlertDialogUtils;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.utils.WeekRefeListener;
import com.accessories.city.view.CustomListView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;

import java.util.ArrayList;
import java.util.Map;

/**
 * @desc 课程-->每天的课程
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class WeeksFragment extends BaseFragment implements WeekRefeListener,RequsetListener{

    private CustomListView customListView = null;
    private ArrayList<CourseInfo> list = new ArrayList<CourseInfo>();
    private WeeksAdpter adapter;
    private int position;
    private TextView noData ;

    public static ArrayList<WeekRefeListener> weekRefeListeners = new ArrayList<WeekRefeListener>();

//    private String weeks[] = new String[]{"周一","周二","周三","周四","周五","周六","周日"};

    View view;
    private boolean isPrepare = false;
    private boolean isVisible = false;
    private String id = "";

    int flag = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if(bundle != null ){
            list = (ArrayList<CourseInfo>) bundle.getSerializable("courList");
            flag = bundle.getInt("position");
        }
        weekRefeListeners.add(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_msg,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        isPrepare = true;
        onLazyLoad();

    }


    private void onLazyLoad(){

        if(!isPrepare || !isVisible){
            return;
        }
//        requestTask(1);
        if(ScheduleFragment.weekCourseList != null)
        notifyData(ScheduleFragment.weekCourseList.get(flag));
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


    private void initView(View view){

        customListView = (CustomListView)view.findViewById(R.id.callListView);
        noData = (TextView)view.findViewById(R.id.noData);
        customListView.setCanLoadMore(false);
        customListView.setCanRefresh(false);
        adapter = new WeeksAdpter(mActivity, list);
        customListView.setAdapter(adapter);

        customListView.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
        noData.setText("暂无课程安排");
        if(list == null || list.size()==0){
            customListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                AlertDialogUtils.displayMyAlertChoice(mActivity, "提示", "是否删除此课程安排?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id = list.get(i-1).getId();
                        requestTask(2);
                    }
                }, null);
            }
        });
    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        RequestParam param = new RequestParam();
        Map postParams = null;
        postParams = RequestHelp.getBaseParaMap("ScheduleDel");
        postParams.put("id",id);
//        param.setmParserClassName(new BaseParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {

                CourseInfo selectMsg = null;
                for (CourseInfo msgDetail : list) {
                    if (TextUtils.equals(id, msgDetail.getId())) {
                        selectMsg = msgDetail;
                        break;
                    }
                }

                if (selectMsg != null && ScheduleFragment.weekCourseList != null) {
                    ScheduleFragment.weekCourseList.get(flag).remove(selectMsg);
//                    list.remove(selectMsg);
                    notifyData(ScheduleFragment.weekCourseList.get(flag));
                }
//                adapter.notifyDataSetInvalidated();
//                adapter.notifyDataSetChanged();
                toasetUtil.showSuccess("删除成功!");

    }


    private void notifyData(ArrayList<CourseInfo> list){
        this.list = new ArrayList<CourseInfo>();
        if(list != null){
           this.list.addAll(list);
        }
//        initView(view);
        adapter = new WeeksAdpter(mActivity, list);
        customListView.setAdapter(adapter);
        customListView.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);
        if(list == null || list.size()==0){
            customListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void weekRefeListener() {
        onLazyLoad();
    }
}