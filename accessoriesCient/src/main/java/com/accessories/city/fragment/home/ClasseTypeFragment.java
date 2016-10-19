package com.accessories.city.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.adapter.ClassTypeAdpter;
import com.accessories.city.bean.CateSubTypeEntity;
import com.accessories.city.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ClasseTypeFragment extends BaseFragment implements OnClickListener{

    private GridView viewForScrollView = null;
    private ClassTypeAdpter adapter;
    private String type = "";
    private TextView noData = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classes_type, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        viewForScrollView = (GridView) view.findViewById(R.id.callGridView);
        noData = (TextView)view.findViewById(R.id.noData);
        List<CateSubTypeEntity> list = new ArrayList<CateSubTypeEntity>();
        CateSubTypeEntity cateSubTypeEntity = null;
        for(int i=0;i<20;i++){
            cateSubTypeEntity = new CateSubTypeEntity();
            cateSubTypeEntity.setName("嘿嘿I"+i);
            list.add(cateSubTypeEntity);
        }

        adapter = new ClassTypeAdpter(getActivity(),list);
        viewForScrollView.setAdapter(adapter);
        viewForScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                CateSubTypeEntity type = (CateSubTypeEntity) arg0.getItemAtPosition(arg2);
//
//                if("2".equalsIgnoreCase(type.getType()+"")){//海外疫苗类
//                    Bundle bundle = new Bundle();
//                    bundle.putString(URLConstants.TITLE, "海外疫苗");
//                    bundle.putString(URLConstants.URL,URLConstants.MARIN_INJECT);
//                    startFragment(new ServiceProtocolFragment(), bundle, false);
//                    return;
//                }
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(URLConstants.TYPE, type);
//                startFragment(new NurseTypeListFragment(), bundle, false);
            }
        });
        viewForScrollView.setVisibility(View.VISIBLE);
        noData.setVisibility(View.GONE);

        setPullLvHeight(viewForScrollView);
//        if(cateType.getSubTypes().isEmpty()){
////        	toasetUtil.showInfo(R.string.loading_fail_nodata);
//            viewForScrollView.setVisibility(View.GONE);
//            noData.setVisibility(View.VISIBLE);
//        }
    }
    private void setPullLvHeight(GridView pull){
        int totalHeight = 0;
        ListAdapter adapter= pull.getAdapter();
        for (int i = 0, len = adapter.getCount(); i < len; i++) { //listAdapter.getCount()返回数据项的数目
            View listItem = adapter.getView(i, null, pull);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = pull.getLayoutParams();
        params.height = totalHeight ;
        pull.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {

    }
}
