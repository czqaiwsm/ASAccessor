package com.accessories.city.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.accessories.city.R;
import com.accessories.city.adapter.ChooseJoinorAdapter;
import com.accessories.city.bean.DataMapConstants;
import com.accessories.city.bean.IdInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.view.CustomListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 筛选界面
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class ChooseJoinorFragment extends BaseFragment {

    private CustomListView customListView = null;
    private List<IdInfo> list = new ArrayList<IdInfo>();
    private ChooseJoinorAdapter adapter;
    private JoniorType joniorType =  JoniorType.JONIOR;
    private String selectId = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if(intent != null){

            if(intent.hasExtra("joniorId"))
            selectId =intent.getStringExtra("joniorId");

            switch (intent.getFlags()){
                case 11://年级
                    joniorType = JoniorType.JONIOR;
                    break;
                case 12://学历
                    joniorType = JoniorType.DEGREE;//学历
                    break;
                case 14://科目
                    joniorType = JoniorType.SUBJECT;//学历
                    break;
            }
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
    }

    private void initTitle(){
        setTitleText(R.string.jonior);
        setLeftHeadIcon(0);


    }

    private IdInfo idInfo = null;
    private void initView(View view){

        customListView = (CustomListView)view.findViewById(R.id.callListView);

        customListView.setCanLoadMore(false);
        customListView.setCanRefresh(false);
        Map<String,String>  map = new HashMap<String, String>();
        String[]  ids = null;
        switch (joniorType){
            case JONIOR:
                map = DataMapConstants.getJoniorMap();//年级
                ids = mActivity.getResources().getStringArray(R.array.jonior_id);
                break;
            case DEGREE:
                map = DataMapConstants.getDegree();//学历
                ids = mActivity.getResources().getStringArray(R.array.degree_id);
                break;
            case SUBJECT:
                map = DataMapConstants.getCourse();//学历
                ids = mActivity.getResources().getStringArray(R.array.course_id);
                break;
        }
        for(int i=0;i<ids.length;i++){
            idInfo = new IdInfo();
            idInfo.setIdCode(ids[i]);
            idInfo.setName(map.get(ids[i]));
            list.add(idInfo);
        }
        adapter = new ChooseJoinorAdapter(mActivity, list,selectId);
        customListView.setAdapter(adapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                idInfo  = (IdInfo) arg0.getItemAtPosition(arg2);
                 intent.putExtra(URLConstants.CHOOSE, idInfo.getIdCode());
                mActivity.setResult(Activity.RESULT_OK,intent);
                mActivity.finish();

            }
        });
    }

    public void setJoniorType(JoniorType joniorType) {
        this.joniorType = joniorType;
    }

    public enum JoniorType{
        JONIOR,//年级选择
        DEGREE,//学历
        MAJOR,//专业
        TEACH_AGE,//老师学历
        SUBJECT,//科目
    }

}