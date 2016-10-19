package com.accessories.city.fragment.center;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.adapter.SystemMsgAdapter;
import com.accessories.city.bean.SystemMsg;
import com.accessories.city.bean.SystemMsgListBean;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.PullRefreshStatus;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.BaseParse;
import com.accessories.city.parse.SystemMsgParse;
import com.accessories.city.utils.AlertDialogUtils;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.view.CustomListView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @desc 系统消息
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class SystemMsgFragment extends BaseFragment implements RequsetListener ,CustomListView.OnLoadMoreListener {

    public static int MsgType = 1;//系统消息  2个人消息
    private CustomListView customListView = null;
    private List<SystemMsg> list = new ArrayList<SystemMsg>();
    private SystemMsgAdapter adapter;
    private TextView noData;


    private int pageNo = 1;
    private int pageCount = 0;//总页数
    private int pageSize = 10;//每页的数据量
    private PullRefreshStatus status = PullRefreshStatus.NORMAL;
    private int requestType = 1;//请求信息 1  \ 2删除信息
    private String delMsgId = "";

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
        requestTask(1);
    }

    private void initTitle(){

        if(MsgType == 1){
            setTitleText(R.string.sys_msg);
        }else {
            setTitleText("个人消息");
        }
        setLeftHeadIcon(0);
    }


    private void initView(View view){

        customListView = (CustomListView)view.findViewById(R.id.callListView);
        noData = (TextView)view.findViewById(R.id.noData);

        customListView.setCanRefresh(true);
        adapter = new SystemMsgAdapter(mActivity, list,SystemMsgFragment.this);
        customListView.setAdapter(adapter);

        if(MsgType == 2){
            customListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long l) {
                    AlertDialogUtils.displayMyAlertChoice(mActivity, "提示", "您确定删除信息?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delMsgReq(list.get(i-1).getId().toString());
                        }
                    },null);
                    return false;
                }
            });
        }
        customListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status = PullRefreshStatus.PULL_REFRESH;
                pageNo = 1;
                requestType = 1;
                requestData(1);
            }
        });


    }

    @Override
    public void onLoadMore() {
        status = PullRefreshStatus.LOAD_MORE;
        pageNo++;
        requestType = 1;
        requestData(1);
    }

    public void delMsgReq(String id){
        if(!TextUtils.isEmpty(id)){
            delMsgId = id;
            requestType = 2;
            requestTask(2);
        }

    }

    private void delMsgSucc(){
        Iterator<SystemMsg> msgIterator = list.iterator();
        while (msgIterator.hasNext()){
            SystemMsg systemMsg = msgIterator.next();
            if (systemMsg.getId().equalsIgnoreCase(delMsgId)){
                msgIterator.remove();
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        RequestParam param = new RequestParam();
        Map postParams  = null;
        switch (requestType){
            case 1:
                if(MsgType == 1){
                    postParams = RequestHelp.getBaseParaMap("SysMsgList");
                }else if(MsgType == 2) {
                    postParams = RequestHelp.getBaseParaMap("PersonMsgList");

                }
//        param.setmParserClassName(SystemMsgParse.class.getName());
                postParams.put("pageNo",pageNo);
                param.setmParserClassName(SystemMsgParse.class.getName());
                break;
            case 2:
                if(MsgType == 1){
                    postParams = RequestHelp.getBaseParaMap("DelSysMsg");
                }else if(MsgType == 2) {
                    postParams = RequestHelp.getBaseParaMap("DelPersonMsg");
                }
//        param.setmParserClassName(SystemMsgParse.class.getName());
                postParams.put("msgId",delMsgId);
                param.setmParserClassName(BaseParse.class.getName());
                break;
        }


        url.setmBaseUrl(URLConstants.BASE_URL);
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        switch (requestType){
            case 1:
                JsonParserBase<SystemMsgListBean> jsonParserBase = (JsonParserBase<SystemMsgListBean>)obj;
                SystemMsgListBean systemMsgs = jsonParserBase.getObj();
                if(systemMsgs != null){
                    pageCount = systemMsgs.getTotalPages();
                    pageSize = systemMsgs.getPageSize();
                    ArrayList<SystemMsg> teacherInfos = systemMsgs.getElements();

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
                break;
            case 2:
                delMsgSucc();
                toasetUtil.showSuccess("信息删除成功");
                break;
        }

    }

    @Override
    protected void failRespone() {
        super.failRespone();
        if(requestType == 1){
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
    private void refresh(ArrayList<SystemMsg> teacherInfos){
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