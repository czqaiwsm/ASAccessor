package com.accessories.city.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.accessories.city.R;
import com.accessories.city.adapter.MainAdapter;
import com.accessories.city.adapter.OrderPayAdpter;
import com.accessories.city.bean.OrderInfo;
import com.accessories.city.bean.OrderListBean;
import com.accessories.city.help.PullRefreshStatus;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.utils.WaitLayer;
import com.accessories.city.view.CustomListView;
import com.accessories.city.view.PayPopupwidow;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc
 * @creator caozhiqing
 * @data 2016/3/10
 */
@SuppressLint("ValidFragment")
public class PullRefreshFragment extends BaseFragment implements RequsetListener,CustomListView.OnLoadMoreListener {

    private CustomListView customListView = null;
    private MainAdapter adapter;
    private TextView noData ;

    private int pageCount = 0;//总页数
    private int pageNo = 1;
    private int pageSize = 10;//每页的数据量
    private PullRefreshStatus status = PullRefreshStatus.NORMAL;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }




    private void initView(View view){

        customListView = (CustomListView)view.findViewById(R.id.callListView);
        noData = (TextView)view.findViewById(R.id.noData);
        customListView.setCanRefresh(true);
        adapter = new MainAdapter(new SoftReference<Context>(mActivity));
        customListView.setAdapter(adapter);

        customListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status = PullRefreshStatus.PULL_REFRESH;
                pageNo = 1;
                requestData(1);

            }
        });
    }

    @Override
    public void onLoadMore() {
        status = PullRefreshStatus.LOAD_MORE;
        pageNo++;
        requestData(1);
    }


    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        switch (requestType){
            case 1:
                JsonParserBase<OrderListBean> jsonParserBase = (JsonParserBase<OrderListBean>)obj;
                OrderListBean chooseTeachBean = jsonParserBase.getObj();
                if(chooseTeachBean != null){
                    pageCount = chooseTeachBean.getTotalPages();
                    pageSize = chooseTeachBean.getPageSize();
                    ArrayList<OrderInfo> teacherInfos = chooseTeachBean.getElements();
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
                                adapter.getList().addAll(teacherInfos);
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
    private void refresh(ArrayList<OrderInfo> teacherInfos){
        List list = adapter.getList();
        list.clear();
        if(teacherInfos != null){
            list.addAll(teacherInfos);
        }

        if(teacherInfos==null || teacherInfos.size()==0){//显示无数据
            if(list.size()==0){
                noData.setVisibility(View.VISIBLE);
            }
        }else {
            noData.setVisibility(View.GONE);
            if(teacherInfos.size()>=pageSize){//有足够的数据,可以下拉刷新
                customListView.setCanLoadMore(true);
                customListView.setOnLoadListener(this);
            }else {
                customListView.setCanLoadMore(false);
            }

        }
        adapter.notifyDataSetChanged();

    }
}