package com.accessories.city.fragment.msg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.activity.teacher.ChatMsgActivity;
import com.accessories.city.adapter.MsgAdpter;
import com.accessories.city.bean.ChatMsgEntity;
import com.accessories.city.bean.MsgDetail;
import com.accessories.city.bean.UserInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.PullRefreshStatus;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.AlertDialogUtils;
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
 * @desc 消息--》消息
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class MsgFragment extends BaseFragment implements RequsetListener {

    private CustomListView customListView = null;
    private List<MsgDetail> list = new ArrayList<MsgDetail>();
    MsgAdpter adapter;
    private TextView noData;

    private String senderId;
    private String receiverId;

    private boolean isPrepare = false;
    private boolean isVisible = false;

    private PullRefreshStatus status = PullRefreshStatus.NORMAL;

    private int flag = 1; //1 list 2删除

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, null);
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

    private void onLazyLoad() {

        if (!isPrepare || !isVisible) {
            return;
        }
        flag = 1;
        requestTask();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
        } else {
            isVisible = false;
            if (isPrepare) {
                dismissLoadingDilog();
            }
        }

        onLazyLoad();
    }

    private void initView(View view) {

        customListView = (CustomListView) view.findViewById(R.id.callListView);
        noData = (TextView) view.findViewById(R.id.noData);

        customListView.setCanRefresh(true);
        customListView.setCanLoadMore(false);
        adapter = new MsgAdpter(mActivity, list);
        customListView.setAdapter(adapter);


        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity, ChatMsgActivity.class);
                MsgDetail msgDetail = list.get(i-1);
                UserInfo userInfo = BaseApplication.getUserInfo();
                if( userInfo !=  null){
                     String teacherId = "";
                    if(TextUtils.equals(userInfo.getId(),list.get(i-1).getReceiverId())){
                        teacherId = list.get(i-1).getSenderId();
                        intent.putExtra("teacherId",list.get(i-1).getSenderId());
                    }else {
                        teacherId = list.get(i-1).getReceiverId();
                    }
                    intent.putExtra("teacherId",teacherId);

                    ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
                    chatMsgEntity.setDirection("2");
                    chatMsgEntity.setReceiverId(teacherId);
//                    chatMsgEntity.setSenderId(userInfo.getId());

                    chatMsgEntity.setStudentName(msgDetail.getStudentName());
                    chatMsgEntity.setStudentImg(msgDetail.getHeadImg());
//                    chatMsgEntity.setTeacherImg(userInfo.getHeadImg());
                    intent.putExtra("bundle",chatMsgEntity);
                    startActivity(intent);
                }
            }
        });


        customListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialogUtils.displayMyAlertChoice(mActivity, "提示", "是否删除此信息?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flag = 2;
                        senderId = list.get(position-1).getSenderId();
                        receiverId = list.get(position-1).getReceiverId();
                        requestTask();
                    }
                }, null);
                return true;
            }
        });

        customListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                flag = 1;
                requestData(0);
            }
        });
    }


    @Override
    protected void requestData(int requestType) {

        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        RequestParam param = new RequestParam();

        Map postParams = null;
        if (flag == 1) {
            customListView.onRefreshComplete();
            customListView.onLoadMoreComplete();
            postParams = RequestHelp.getBaseParaMap("MessageList");
//            param.setmParserClassName(new MsgDetailParse());

        } else if (flag == 2) {
            postParams = RequestHelp.getBaseParaMap("MessageDel");
//            postParams.put("senderId", senderId);
            UserInfo userInfo = BaseApplication.getUserInfo();
            String tepId= receiverId;
            if(userInfo != null){
                if(receiverId.equalsIgnoreCase(userInfo.getId())){
                    tepId = senderId;
                }
            }
            postParams.put("receiverId", tepId);
//            param.setmParserClassName(new BaseParse());

        }
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        if (flag == 1) {
        JsonParserBase<ArrayList<MsgDetail>> jsonParserBase = (JsonParserBase<ArrayList<MsgDetail>>) obj;
        ArrayList<MsgDetail> chooseTeachBean = jsonParserBase.getObj();
        if (chooseTeachBean != null) {

                refresh(chooseTeachBean);
            }

        }
        else if (flag == 2) {

            int positon = -1;
            MsgDetail selectMsg = null;
            for (MsgDetail msgDetail : list) {
                if (TextUtils.equals(receiverId, msgDetail.getReceiverId()) &&
                        TextUtils.equals(senderId, msgDetail.getSenderId())) {
                    selectMsg = msgDetail;
                    break;
                }
            }

            if (selectMsg != null) {
                list.remove(selectMsg);
            }
            adapter.notifyDataSetChanged();
            toasetUtil.showSuccess("删除成功!");

        }
    }

    @Override
    protected void failRespone() {
        super.failRespone();
        customListView.onRefreshComplete();
        switch (status) {
            case PULL_REFRESH:
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
     *
     * @param teacherInfos
     */
    private void refresh(ArrayList<MsgDetail> teacherInfos) {
        if (teacherInfos == null || teacherInfos.size() == 0) {//显示无数据
            if (list.size() == 0) {
                noData.setVisibility(View.VISIBLE);
            }
        } else {
            noData.setVisibility(View.GONE);
            list.clear();
            list.addAll(teacherInfos);
            adapter.notifyDataSetChanged();

        }
    }
}