package com.accessories.city.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.accessories.city.R;
import com.accessories.city.adapter.MultiChooseAdapter;
import com.accessories.city.bean.DataMapConstants;
import com.accessories.city.bean.IdInfo;
import com.accessories.city.fragment.BaseFragment;
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
public class MultiChooseFragment extends BaseFragment {

    private CustomListView customListView = null;
    private List<IdInfo> list = new ArrayList<IdInfo>();
    private MultiChooseAdapter adapter;
    private JoniorType joniorType =  JoniorType.JONIOR;
    private ArrayList<String> selectList = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if(intent != null){

            selectList = intent.getStringArrayListExtra("selectId");

            if(intent.hasExtra("joniorId"))
//            selectId =intent.getStringExtra("joniorId");
            switch (intent.getFlags()){
                case 11:
                    joniorType = JoniorType.JONIOR;
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
        setTitleText("选择上课频次");
        setLeftHeadIcon(0);

        setHeaderRightText(R.string.sure, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("selectId",selectList);
                mActivity.setResult(Activity.RESULT_OK,intent);
                mActivity.finish();
            }
        });

    }

    private IdInfo idInfo = null;
    private void initView(View view){

        customListView = (CustomListView)view.findViewById(R.id.callListView);

        customListView.setCanLoadMore(false);
        customListView.setCanRefresh(false);
        Map<String,String>  map = new HashMap<String, String>();
        String[]  ids = null;
        map = DataMapConstants.getFrequency();
        ids = mActivity.getResources().getStringArray(R.array.frequency_id);
        for(int i=0;i<ids.length;i++){
            idInfo = new IdInfo();
            idInfo.setIdCode(ids[i]);
            idInfo.setName(map.get(ids[i]));
            list.add(idInfo);
        }
        adapter = new MultiChooseAdapter(mActivity,list,selectList);
        customListView.setAdapter(adapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                idInfo  = (IdInfo) arg0.getItemAtPosition(arg2);
                if(selectList.contains(idInfo.getIdCode())){
                    selectList.remove(idInfo.getIdCode());
                }else {
                    selectList.add(idInfo.getIdCode());
                }
                adapter.notifyDataSetChanged();
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
    }

}