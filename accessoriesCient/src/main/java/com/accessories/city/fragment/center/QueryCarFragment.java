package com.accessories.city.fragment.center;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.accessories.city.R;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.view.CustomListView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 资讯中心
 */
public class QueryCarFragment extends BaseFragment implements RequsetListener, CustomListView.OnLoadMoreListener {


    @Bind(R.id.search_edit)
    EditText searchEdit;
    @Bind(R.id.homeSearch)
    LinearLayout homeSearch;
    @Bind(R.id.close_btn)
    ImageView closeBtn;
    @Bind(R.id.search_btn)
    Button searchBtn;
    @Bind(R.id.searchLL)
    LinearLayout searchLL;

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
//        requestTask(0);
    }

    protected void requestData(int req) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.SHOPLIST);
        Map postParams = new HashMap<String, String>();

        postParams.put("lat", "" + (BaseApplication.getInstance().mapLocation != null ? BaseApplication.getInstance().mapLocation.getLatitude() : "0"));
        postParams.put("lng", "" + (BaseApplication.getInstance().mapLocation != null ? BaseApplication.getInstance().mapLocation.getLongitude() : "0"));
        RequestParam param = new RequestParam();
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(req), createMyReqErrorListener(), param);

    }

    @Override
    public void handleRspSuccess(int reqCode, Object obj) {
    }


    @Override
    public void onLoadMore() {
        requestData(0);
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

    private void initTitle(View view) {
        setLeftHeadIcon(0);
        setTitleText("车驾码查询");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}