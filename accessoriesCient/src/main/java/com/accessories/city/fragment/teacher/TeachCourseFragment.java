package com.accessories.city.fragment.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.accessories.city.R;
import com.accessories.city.activity.center.PurchaseCourseActivity;
import com.accessories.city.adapter.TeacherCourseAdapter;
import com.accessories.city.bean.CourseInfo;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.view.CustomListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 老师信息-->课程
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class TeachCourseFragment extends BaseFragment {

    private CustomListView customListView = null;
    private List<CourseInfo> list = new ArrayList<CourseInfo>();
    TeacherCourseAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null ){
            list = (ArrayList<CourseInfo>) bundle.getSerializable("courses");
        }
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
    }


    private void initView(View view){

        customListView = (CustomListView)view.findViewById(R.id.callListView);

        customListView.setCanLoadMore(false);
        customListView.setCanRefresh(false);
         adapter = new TeacherCourseAdapter(mActivity, list);
        customListView.setAdapter(adapter);

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mActivity,PurchaseCourseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("course",list.get(i-1));
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
    }

}