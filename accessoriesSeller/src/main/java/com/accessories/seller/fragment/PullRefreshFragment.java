package com.accessories.seller.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.accessories.seller.R;
import com.accessories.seller.adapter.MainAdapter;
import com.accessories.seller.bean.OrderInfo;
import com.accessories.seller.bean.OrderListBean;
import com.accessories.seller.help.PullRefreshStatus;
import com.accessories.seller.help.RequsetListener;
import com.accessories.seller.view.CustomListView;
import com.volley.req.parser.JsonParserBase;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc
 * @creator caozhiqing
 * @data 2016/3/10
 */
@SuppressLint("ValidFragment")
public abstract class PullRefreshFragment extends BaseFragment implements RequsetListener,CustomListView.OnLoadMoreListener {

    private CustomListView customListView = null;
    private MainAdapter adapter;
    private TextView noData ;

    private int pageCount = 0;//总页数
    protected int pageNo = 1;
    protected int pageSize = 10;//每页的数据量
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

        customListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status = PullRefreshStatus.PULL_REFRESH;
                pageNo = 1;
                requestData(1);

            }
        });
    }


    protected void initAdapater(MainAdapter mainAdapter){
        this.adapter = mainAdapter;
        customListView.setAdapter(this.adapter);

    }

    @Override
    public void onLoadMore() {
        status = PullRefreshStatus.LOAD_MORE;
        pageNo++;
        requestData(1);
    }

    protected  abstract List parseList(String json);


    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        switch (requestType){
            case 1:
                System.out.println("resJson=="+obj.toString());
                ArrayList teacherInfos = (ArrayList) parseList(obj.toString());
                if(teacherInfos != null){
//                    pageCount = chooseTeachBean.getTotalPages();
//                    pageSize = chooseTeachBean.getPageSize();
//                    ArrayList<OrderInfo> teacherInfos = chooseTeachBean.getElements();
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
    private void refresh(ArrayList teacherInfos){
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