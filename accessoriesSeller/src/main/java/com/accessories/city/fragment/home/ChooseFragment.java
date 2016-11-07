package com.accessories.city.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.activity.ChooseCityActivity;
import com.accessories.city.activity.teacher.ChooseJoinorActivity;
import com.accessories.city.bean.DataMapConstants;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.URLConstants;

/**
 * @desc 筛选界面
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class ChooseFragment extends BaseFragment implements View.OnClickListener{

    private RelativeLayout chooseCityRl ;//城市

    private RelativeLayout  chooseJoniorRl;//年级

    private TextView city_name, school;

    private String joniorId = "";
    private  String cityId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(BaseApplication.getUserInfo() != null){
//            joniorId = BaseApplication.getUserInfo().getGrade();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_choose,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle();
        initView(view);
    }

    private void initView(View view){
        chooseCityRl = (RelativeLayout) view.findViewById(R.id.city_choose);
        chooseJoniorRl = (RelativeLayout)view.findViewById(R.id.choose_jonior);
        city_name = (TextView)view.findViewById(R.id.city_name);
        school = (TextView)view.findViewById(R.id.school);
        chooseCityRl.setOnClickListener(this);
        chooseJoniorRl.setOnClickListener(this);
//        joniorId =BaseApplication.getUserInfo().getGrade();
        cityId = BaseApplication.getInstance().location[0];
        setData();
    }


    private void initTitle(){
        setTitleText(R.string.choose);
        setLeftHeadIcon(0);
        setHeaderRightText(R.string.sure, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //todo
                Intent intent = new Intent();
                intent.putExtra("cityName",city_name.getText().toString());
                intent.putExtra("joniorId",joniorId);
                mActivity.setResult(Activity.RESULT_OK,intent);
                mActivity.finish();
            }
        });
    }

    private void setData(){
        city_name.setText(cityId);
        school.setText(DataMapConstants.getJoniorMap().get(joniorId));
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.city_choose:
                intent = new Intent(mActivity, ChooseCityActivity.class);
                startActivityForResult(intent, URLConstants.CHOOSE_CITY_REQUEST_CODE);
                break;
            case R.id.choose_jonior:
                intent = new Intent(mActivity, ChooseJoinorActivity.class);
                intent.putExtra("joniorId",joniorId);
                intent.setFlags(11);
                startActivityForResult(intent, URLConstants.CHOOSE_JOINOR_REQUEST_CODE);
                break;
            default:break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == URLConstants.CHOOSE_CITY_REQUEST_CODE){//城市
                city_name.setText(data.hasExtra("city")?data.getStringExtra("city"):"");
            }else if(requestCode == URLConstants.CHOOSE_JOINOR_REQUEST_CODE){//年级
                joniorId = data.hasExtra(URLConstants.CHOOSE)?data.getStringExtra(URLConstants.CHOOSE):"";
                school.setText(DataMapConstants.getJoniorMap().get(joniorId));
            }
        }

    }
}