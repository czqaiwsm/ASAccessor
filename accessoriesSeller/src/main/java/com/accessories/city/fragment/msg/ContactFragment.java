package com.accessories.city.fragment.msg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.adapter.ContactAdpter;
import com.accessories.city.bean.Contactor;
import com.accessories.city.bean.ContactorBean;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.PullRefreshStatus;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
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
 * @desc 联系人
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class ContactFragment extends BaseFragment implements RequsetListener,CustomListView.OnLoadMoreListener {

    private CustomListView customListView = null;
    private List<Contactor> list = new ArrayList<Contactor>();
    private ContactAdpter adapter;
    private TextView noData;


    private boolean isPrepare = false;
    private boolean isVisible = false;

    private int pageCount = 0;//总页数
    private int pageNo = 1;
    private int pageSize = 10;//每页的数据量
    private PullRefreshStatus status = PullRefreshStatus.NORMAL;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setLoadingDilog(WaitLayer.DialogType.NOT_NOMAL);
        isPrepare = true;
        onLazyLoad();
    }




    private void onLazyLoad(){

        if(!isPrepare || !isVisible){
            return;
        }
        requestTask();

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
        customListView.setCanRefresh(true);
        adapter = new ContactAdpter(mActivity, list);
        customListView.setAdapter(adapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(mActivity,TeacherDetailActivity.class);
//                intent.putExtra("teacherId",list.get(i-1).getStudentId());
//                startActivity(intent);
            }
        });

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
        Map postParams = RequestHelp.getBaseParaMap("ContactList");
        RequestParam param = new RequestParam();
        postParams.put("pageNo",pageNo);
//        param.setmParserClassName(new ContactorBeanParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        JsonParserBase<ContactorBean> jsonParserBase = (JsonParserBase<ContactorBean>)obj;
        ContactorBean chooseTeachBean = jsonParserBase.getObj();
        if(chooseTeachBean != null){
            pageCount = chooseTeachBean.getTotalPages();
            pageSize = chooseTeachBean.getPageSize();
            ArrayList<Contactor> teacherInfos = chooseTeachBean.getElements();

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
    private void refresh(ArrayList<Contactor> teacherInfos){
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

}