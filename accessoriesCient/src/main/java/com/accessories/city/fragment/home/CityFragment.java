package com.accessories.city.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.bean.City;
import com.accessories.city.bean.CityChoose;
import com.accessories.city.bean.CityList;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.CityChooseParse;
import com.accessories.city.utils.URLConstants;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;

/**
 * 资讯中心
 */
public class CityFragment extends BaseFragment implements RequsetListener{

    private ExpandableListView customListView = null;
    private TextView noData ;
    private LinearLayout noDataLL;

    ExpandListViewAdapter  expandListViewAdapter = null;
//    CityChoose cityChoose = null;
    ArrayList<CityList> list = new ArrayList<CityList>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_choose,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initTitle(view);
        requestTask(0);
    }
    protected  void requestData(int requestCode){
    	 HttpURL url = new HttpURL();
         url.setmBaseUrl(URLConstants.CITYLIST);
         RequestParam param = new RequestParam();
         param.setmParserClassName(CityChooseParse.class.getName());
         param.setmHttpURL(url);
         param.setPostRequestMethod();
         RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestCode), createMyReqErrorListener(), param);
    	
    }

    @Override
    public void handleRspSuccess(int reqCode,Object obj) {

        JsonParserBase<CityChoose> jsonParserBase =  (JsonParserBase<CityChoose>) obj;
        CityChoose cityChoose = jsonParserBase.getObj();
        list.clear();
        if(cityChoose != null){
            list.addAll(cityChoose.getArray());
        }
        expandListViewAdapter.notifyDataSetChanged();
        allExpand();

    }
    private void initView(View view){
    	noData = (TextView)view.findViewById(R.id.noData);
        noDataLL = (LinearLayout)view.findViewById(R.id.noDataLL);
        customListView = (ExpandableListView)view.findViewById(R.id.callListView);

        expandListViewAdapter = new ExpandListViewAdapter();
        customListView.setAdapter(expandListViewAdapter);
        customListView.setGroupIndicator(null);
        customListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                City city = list.get(groupPosition).getCityList().get(childPosition);
                if(city != null){
                    Intent intent = new Intent();
                    intent.putExtra("cityId",city.getCityId());
                    intent.putExtra("cityName",city.getCityName());
                    mActivity.setResult(Activity.RESULT_OK,intent);
                }
                mActivity.finish();
                return false;
            }
        });
    }

    private void initTitle(View view){
        setTitleText("城市");
        setLeftHeadIcon(0);
    }


    private void allExpand(){
        for(int i=0;i<list.size();i++){
            customListView.expandGroup(i);
        }

    }



    class ExpandListViewAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            int count = 0;
            if(list != null ){
                count = list.size();
            }
            return count;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            int count = 0;
            if(list.get(groupPosition) != null && list.get(groupPosition).getCityList()!=null){
                count = list.get(groupPosition).getCityList().size();
            }
            return count;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return list.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return list.get(groupPosition).getCityList().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.choose_city_adapter,null);
            TextView city_name = (TextView)convertView.findViewById(R.id.city_name);
            if(list != null && !list.isEmpty() )
            city_name.setText(list.get(groupPosition).getProName());
            onGroupExpanded(groupPosition);
            convertView.setClickable(true);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.choose_city_second_adapter,null);
            TextView city_name = (TextView)convertView.findViewById(R.id.city_name);
            if(list.get(groupPosition).getCityList() != null && !list.get(groupPosition).getCityList().isEmpty())
             city_name.setText(list.get(groupPosition).getCityList().get(childPosition).getCityName());

            if(isLastChild){
                convertView.findViewById(R.id.half_lien).setVisibility(View.GONE);
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        class ViewHolder{

            private TextView city_name;


        }

    }

  
}