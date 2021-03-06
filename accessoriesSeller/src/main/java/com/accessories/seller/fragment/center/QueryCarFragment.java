package com.accessories.seller.fragment.center;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accessories.seller.R;
import com.accessories.seller.bean.VinBean;
import com.accessories.seller.fragment.BaseFragment;
import com.accessories.seller.utils.SmartToast;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.ParserUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 资讯中心
 */
public class QueryCarFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.search_edit)
    EditText searchEdit;
    @Bind(R.id.homeSearch)
    LinearLayout homeSearch;
    @Bind(R.id.close_btn)
    ImageView closeBtn;
    @Bind(R.id.search_btn)
    LinearLayout searchBtn;
    @Bind(R.id.searchLL)
    LinearLayout searchLL;
    @Bind(R.id.brandNameTv)
    TextView brandNameTv;
    @Bind(R.id.manufacturerTv)
    TextView manufacturerTv;
    @Bind(R.id.carTypeTv)
    TextView carTypeTv;
    @Bind(R.id.madeYearTv)
    TextView madeYearTv;
    @Bind(R.id.engineTypeTv)
    TextView engineTypeTv;
    @Bind(R.id.transmissionTypeTv)
    TextView transmissionTypeTv;
    @Bind(R.id.outputVolumeTv)
    TextView outputVolumeTv;
    @Bind(R.id.gearsNumTv)
    TextView gearsNumTv;
    @Bind(R.id.powerTv)
    TextView powerTv;
    @Bind(R.id.modelNameTv)
    TextView modelNameTv;
    @Bind(R.id.saleNameTv)
    TextView saleNameTv;
    @Bind(R.id.jetTypeTv)
    TextView jetTypeTv;
    @Bind(R.id.fuelTypeTv)
    TextView fuelTypeTv;
    @Bind(R.id.cylinderNumberTv)
    TextView cylinderNumberTv;
    @Bind(R.id.cylinderFormTv)
    TextView cylinderFormTv;
    @Bind(R.id.airBagT)
    TextView airBagT;
    @Bind(R.id.seatNumT)
    TextView seatNumT;
    @Bind(R.id.vehicleLevelTv)
    TextView vehicleLevelTv;
    @Bind(R.id.doorNumTv)
    TextView doorNumTv;
    @Bind(R.id.carBodyTv)
    TextView carBodyTv;
    @Bind(R.id.carWeight)
    TextView carWeight;
    @Bind(R.id.assemblyFactoryTv)
    TextView assemblyFactoryTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query_car, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle(view);
        closeBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
    }

    private void initTitle(View view) {
        setLeftHeadIcon(0);
        setTitleText("车驾码查询");
    }

    protected void requestData(int req) {
        showLoadingDilog("");
        String strUrl = "https://route.showapi.com/1142-1?" +
                "showapi_appid=25683&showapi_sign=" +
                "091582443591405e8f0c1387b74ee566" +
                "&vin=" + searchEdit.getText().toString(); //
        HttpURL url = new HttpURL();
        url.setmBaseUrl(strUrl);
        RequestParam param = new RequestParam();
        param.setmHttpURL(url);
        RequestManager.getRequestData(getActivity(), new Response.Listener<Object>() {
            @Override
            public void onResponse(Object object) {
                dismissLoadingDilog();
                System.out.println("vinRes==>" + object.toString());
                VinBean jsonParserBase = ParserUtil.fromJson(object.toString(), new TypeToken<VinBean>() {
                }.getType());
                VinBean.VinInfo vinInfo = null;

                if ("0".equals(jsonParserBase.showapi_res_code) && (vinInfo = jsonParserBase.showapi_res_body) != null) {
                    if ("0".equals(vinInfo.ret_code)) {
                        brandNameTv.setText(vinInfo.brand_name);
                        manufacturerTv.setText(vinInfo.manufacturer);
                        carTypeTv.setText(vinInfo.car_type);
                        madeYearTv.setText(vinInfo.made_year);
                        engineTypeTv.setText(vinInfo.engine_type);
                        transmissionTypeTv.setText(vinInfo.transmission_type);
                        outputVolumeTv.setText(vinInfo.output_volume);
                        gearsNumTv.setText(vinInfo.gears_num);
                        powerTv.setText(vinInfo.power);
                        gearsNumTv.setText(vinInfo.gears_num);

                        modelNameTv.setText(vinInfo.model_name);
                        saleNameTv.setText(vinInfo.sale_name);
                        jetTypeTv.setText(vinInfo.jet_type);
                        fuelTypeTv.setText(vinInfo.fuel_Type);
                        cylinderNumberTv.setText(vinInfo.cylinder_number);
                        cylinderFormTv.setText(vinInfo.cylinder_form);
                        airBagT.setText(vinInfo.air_bag);
                        seatNumT.setText(vinInfo.seat_num);
                        vehicleLevelTv.setText(vinInfo.vehicle_level);
                        doorNumTv.setText(vinInfo.door_num);
                        carBodyTv.setText(vinInfo.car_body);
                        carWeight.setText(vinInfo.car_weight);
                        assemblyFactoryTv.setText(vinInfo.assembly_factory);
                    } else {
                        SmartToast.showText(vinInfo.remark);
                    }

                } else {
                    brandNameTv.setText("");
                    manufacturerTv.setText("");
                    carTypeTv.setText("");
                    madeYearTv.setText("");
                    engineTypeTv.setText("");
                    transmissionTypeTv.setText("");
                    outputVolumeTv.setText("");
                    gearsNumTv.setText("");
                    powerTv.setText("");
                    gearsNumTv.setText("");
                    carBodyTv.setText("");

                    modelNameTv.setText("");
                    saleNameTv.setText("");
                    jetTypeTv.setText("");
                    fuelTypeTv.setText("");
                    cylinderNumberTv.setText("");
                    cylinderFormTv.setText("");
                    airBagT.setText("");
                    seatNumT.setText("");
                    vehicleLevelTv.setText("");
                    doorNumTv.setText("");
                    carBodyTv.setText("");
                    carWeight.setText("");
                    assemblyFactoryTv.setText("");
                    SmartToast.showText(jsonParserBase.showapi_res_error);
                }

            }
        }, createMyReqErrorListener(), param);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_btn:
                searchEdit.setText("");
                break;
            case R.id.search_btn:
                if (!TextUtils.isEmpty(searchEdit.getText())) {
                    requestData(1);
                } else {
                    SmartToast.showText("请输入要搜索的VIN码!");
                }
                break;
        }
    }

    @Override
    protected void failRespone() {
        super.failRespone();
    }

    @Override
    protected void errorRespone() {
        super.errorRespone();
        failRespone();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}