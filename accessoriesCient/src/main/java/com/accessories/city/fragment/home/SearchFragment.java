package com.accessories.city.fragment.home;

/*
 * 
 * 搜索商品
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.accessories.city.R;
import com.accessories.city.activity.teacher.TeacherDetailActivity;
import com.accessories.city.adapter.ChooseTeacherAdpter;
import com.accessories.city.bean.TeacherInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequestHelp;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.view.XListView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SearchFragment extends BaseFragment implements OnClickListener, RequsetListener {

    // private List<String> mTags;

    private EditText mSearchEdit;
    private TextView mSearchBtn;//搜索按钮
    private ImageView mCloseImg;
    private TextView mNoHintText;
    private RelativeLayout mLayoutItem;

    private String mKeyword;
    private XListView xListView;
    private List<TeacherInfo> list = new ArrayList<TeacherInfo>();
    private ChooseTeacherAdpter adapter;


    @Override
    protected void requestData(int requestType) {

        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.BASE_URL);
        Map postParams = RequestHelp.getBaseParaMap("SearchTeacher");
//        @"cityName", [ @"pageNo",@"courseId",@"grade", @"cmd",@"123456",@"vcode",@"",@"fInviteCode",@"000000",@"deviceId",@"10",@"appversion",@"4",@"clientType",[[UserInfoManage shareInstance] token],@"accessToken", nil];
        postParams.put("teacherName", mSearchEdit.getText().toString());
        RequestParam param = new RequestParam();
//        param.setmParserClassName(SearchParse.class.getName());
//        param.setmParserClassName(new SearchParse());
        param.setmPostarams(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(), createMyReqErrorListener(), param);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * mTags = new ArrayList<String>(); mTags.add(getActivity().getResources( ).getString(R.string.hall_name));
		 * mTags.add(getActivity().getResources ().getString(R.string.hall_tag1)); mTags.add(getActivity().getResources
		 * ().getString(R.string.hall_tag2));
		 */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_list, container, false);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        JsonParserBase<List<TeacherInfo>> jsonParserBase = (JsonParserBase<List<TeacherInfo>>) obj;
        List<TeacherInfo> teacherInfos = jsonParserBase.getObj();
        list.clear();
        if (teacherInfos != null) {
            list.addAll(teacherInfos);
        }
        adapter.notifyDataSetChanged();
        if(list.size() == 0){
            xListView.setVisibility(View.GONE);
            mNoHintText.setVisibility(View.VISIBLE);
        }else {
            xListView.setVisibility(View.VISIBLE);
            mNoHintText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoHintText = (TextView) view.findViewById(R.id.hall_hint_text);
        mNoHintText.setText(R.string.no_product);
        mNoHintText.setVisibility(View.GONE);
        onInitView(view);
        xListView = (XListView) view.findViewById(R.id.search_data_list);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(false);
        mSearchEdit = (EditText) view.findViewById(R.id.search_edit);
        mSearchEdit.setHint(R.string.hint_search_product);
        mCloseImg = (ImageView) view.findViewById(R.id.close_btn);
        mSearchBtn = (TextView) view.findViewById(R.id.search_btn);
        mSearchEdit.setOnClickListener(this);
        mSearchBtn.setOnClickListener(this);
        mCloseImg.setOnClickListener(this);
        mSearchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (arg1 == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) arg0.getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);

                    if (imm.isActive()) {

                        imm.hideSoftInputFromWindow(arg0.getApplicationWindowToken(), 0);
                        if (!TextUtils.isEmpty(mSearchEdit.getText().toString()) || mSearchEdit.getText().toString().equals(mKeyword)) {
                            xListView.setVisibility(View.VISIBLE);
                            mKeyword = mSearchEdit.getText().toString();
                            requestTask();
                        }
                    }
                    return true;

                }

                return false;

            }
        });

        adapter = new ChooseTeacherAdpter(mActivity, list);
        xListView.setAdapter(adapter);
        xListView.setVisibility(View.VISIBLE);


        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo to TeacherDtailAct
                Intent intent = new Intent(mActivity, TeacherDetailActivity.class);
                intent.putExtra("teacherId",list.get(position-1).getId());
                startActivity(intent);
            }
        });
    }

    private void onInitView(View view) {
        setTitleText(R.string.search);
        setLeftHeadIcon(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_btn:
                mSearchEdit.setText("");
                break;
            case R.id.search_btn:

                if (TextUtils.isEmpty(mSearchEdit.getText().toString())) {
                    toasetUtil.showInfo(R.string.hint_search_product);
                    return;
                }
                xListView.setVisibility(View.VISIBLE);
                mKeyword = mSearchEdit.getText().toString();
                requestTask();
                break;
            case R.id.search_edit:
                xListView.setVisibility(View.GONE);
                break;
        }
    }

}
