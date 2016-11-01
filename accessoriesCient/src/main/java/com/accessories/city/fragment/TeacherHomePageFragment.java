package com.accessories.city.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.*;
import com.accessories.city.R;
import com.accessories.city.activity.ChooseCityActivity;
import com.accessories.city.activity.home.NewsActivity;
import com.accessories.city.adapter.ClassTypeAdpter;
import com.accessories.city.adapter.GuideViewPagerAdapter;
import com.accessories.city.bean.BannerImgInfo;
import com.accessories.city.bean.CateSubTypeEntity;
import com.accessories.city.bean.HomeInfo;
import com.accessories.city.bean.HomePagerBanner;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.HomePageBannerParse;
import com.accessories.city.utils.AppLog;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.DensityUtils;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.view.CustomListView;
import com.accessories.city.view.GridViewForScrollView;
import com.download.base.utils.ScreenUtils;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 产品中心
 * @creator caozhiqing
 * @data 2016/3/10
 * 13637055938    111111
 */
public class TeacherHomePageFragment extends BaseFragment implements View.OnClickListener, RequsetListener {
    CustomListView callListView;//

    public static HomeInfo homeInfo;
    private View converView;
    private TextView home_header_cityname;
    private FrameLayout search;
    private LinearLayout guideLL;
    private ViewPager viewpager = null;
    private GuideViewPagerAdapter guideAdapter = null;
    private int ids[] = {R.drawable.aot, R.drawable.aot, R.drawable.aot};
    public static ArrayList<BannerImgInfo> bannerImgInfos = new ArrayList<BannerImgInfo>();
    private GridViewForScrollView viewForScrollView = null;
    private ClassTypeAdpter adapter;
    private RadioGroup rgTab;

    private String cityId;
    private String cityName;
    private int type = 1;//1全车件 4单项 2汽车用品 3商用车
    private List<CateSubTypeEntity> vehicleList = new ArrayList<>();
    private List<CateSubTypeEntity> singleList = new ArrayList<>();
    private List<CateSubTypeEntity> carList = new ArrayList<>();
    private List<CateSubTypeEntity> commerciaList = new ArrayList<>();


    private boolean prepare = false;
    private boolean isShow = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityName = BaseApplication.getInstance().location[0];
        cityId = BaseApplication.getInstance().location[1];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        converView = inflater.inflate(R.layout.fragment_teacher_home, null);
        View view = inflater.inflate(R.layout.fragment_home, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callListView = (CustomListView)view.findViewById(R.id.callListView);
        home_header_cityname = (TextView)view.findViewById(R.id.home_header_cityname);
        search = (FrameLayout) view.findViewById(R.id.search);
        search.setVisibility(View.VISIBLE);
        search.setOnClickListener(this);
        home_header_cityname.setOnClickListener(this);
        initView(converView);


        guideLL.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int height = (guideLL.getWidth()*27/39);
                ViewGroup.LayoutParams layoutParams = guideLL.getLayoutParams();
                layoutParams.height = height;
                guideLL.setLayoutParams(layoutParams);
                return true;
            }
        });
        prepare = true;

        onLoade();
    }

    private void onLoade(){
        if(prepare && isShow){
            requestTask(type);
            prepare = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isShow = !hidden;
        if(hidden){
            dismissLoadingDilog();
        }
        onLoade();
    }



    private void initView(View view) {
//        homeSearch = (LinearLayout) view.findViewById(R.id.homeSearch);
//        head_seach_txt = (EditText) view.findViewById(R.id.head_seach_txt);
        viewpager = (ViewPager) view.findViewById(R.id.id_guide_viewpager);
        rgTab = (RadioGroup)view.findViewById(R.id.rgTab);
        guideLL = (LinearLayout) view.findViewById(R.id.guideLL);
        onInitTabConfig();
        viewForScrollView = (GridViewForScrollView) view.findViewById(R.id.callGridView);
        ArrayAdapter mArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new Object[]{});
//        callListView.setRefreshBackGround(getResources().getColor(R.color.bg));
        callListView.setAdapter(mArrayAdapter);
        callListView.addHeaderView(converView);
        callListView.setCanLoadMore(false);
        callListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(type);
            }
        });
        home_header_cityname.setText(cityName);
        adapter = new ClassTypeAdpter(new SoftReference<Context>(getActivity()));
        viewForScrollView.setAdapter(adapter);

        viewForScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity,NewsActivity.class);
                intent.putExtra("cityId",cityId);
                intent.putExtra("cateId",((CateSubTypeEntity)adapter.getItem(position)).getId());
                startActivity(intent);
            }
        });

//        view.findViewById(R.id.login).setTag(cityId);

    }

    private void onInitTabConfig() {

        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                List tepList = null;
                switch (checkedId) {
                    case R.id.login:
                        type = 1 ;
                        tepList = vehicleList;
                        break;
                    case R.id.register:
                        tepList = singleList;
                        type = 4 ;
                        break;
                    case R.id.carList:
                        type = 2;
                        tepList = carList;
                        break;
                    case R.id.commerciaList:
                        type = 3;
                        tepList = commerciaList;
                        break;

                }
                setCate(type);
                if(tepList.size() == 0 || !cityId.equals(converView.findViewById(checkedId).getTag())){
                    requestTask(type);
                    converView.findViewById(checkedId).setTag(cityId);
                }
                tepList = null;
            }
        });
    }



    private void setCate(int reqType){
        if(homeInfo != null ){
            switch (reqType){
                case 1:
                    adapter.restList(vehicleList);
                    break;
                case 4:
                    adapter.restList(singleList);
                    break;
                case 2:
                    adapter.restList(carList);
                    break;
                case 3:
                    adapter.restList(commerciaList);
                    break;
            }
        }
    }


    /**
     * 初始化轮播图
     */
    private void initGuidBanner(View view,ArrayList<HomePagerBanner> pagerBanners) {
        if (view == null) {
            return;
        }
        BannerImgInfo imgInfo = null;
        bannerImgInfos.clear();
        if (pagerBanners != null && pagerBanners.size() > 0) {
            for (HomePagerBanner banner : pagerBanners) {
                imgInfo = new BannerImgInfo();
                imgInfo.setId(banner.getId());
                imgInfo.setUrl(banner.getPicUrl());
                imgInfo.setRedirect(banner.getRedirect());
                imgInfo.setTitle(imgInfo.getTitle());
                bannerImgInfos.add(imgInfo);
            }
        }
        pagerBanners.clear();
        pagerBanners = null;
        guideAdapter = new GuideViewPagerAdapter(bannerImgInfos, view, mActivity,true);
        guideAdapter.setDotAlignBottom((int) DensityUtils.px2dp(mActivity, 10f));
        if(!GuideViewPagerAdapter.isAutoPlay){
            guideAdapter.setAutoPlay(viewpager, true);
        }
        viewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                guideAdapter.moveCursorTo(position);// 点的移动
            }
        });
        viewpager.setTag(cityId);

        viewpager.setAdapter(guideAdapter);
        if (bannerImgInfos != null && bannerImgInfos.size() > 1) {
            viewpager.setCurrentItem(bannerImgInfos.size() * 30);
        }
    }

    @Override
    protected void requestData(int requestType) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.Home);
        Map postParams = new HashMap();
        RequestParam param = new RequestParam();
        postParams.put("cityId", cityId);
        postParams.put("type", requestType+"");
        param.setmParserClassName(HomePageBannerParse.class.getName());
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType,Object obj) {
        callListView.onRefreshComplete();
        JsonParserBase<HomeInfo> jsonParserBase = (JsonParserBase<HomeInfo>) obj;
        if (jsonParserBase != null) {
            homeInfo = jsonParserBase.getObj();
            View view = null;
            switch (requestType){
                case 1:
                    vehicleList.addAll(homeInfo.getCatAry());
                    view = converView.findViewById(R.id.login);
                    break;
                case 4:
                    view = converView.findViewById(R.id.register);
                    singleList.addAll(homeInfo.getCatAry());
                    break;
                case 2:
                    view = converView.findViewById(R.id.carList);
                    carList.addAll(homeInfo.getCatAry());
                    break;
                case 3:
                    view = converView.findViewById(R.id.commerciaList);
                    commerciaList.addAll(homeInfo.getCatAry());
                    break;
            }
            if(!cityId.equals(view.getTag())){
                view.setTag(cityId);
            }
            if(viewpager == null ||  !cityId.equals(viewpager.getTag())){
                initGuidBanner(converView,homeInfo.getBannerAry());
                viewpager.setTag(cityId);
            }
            homeInfo.getCatAry().clear();
            homeInfo.setCatAry(null);
            setCate(requestType);
        }

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.home_header_cityname:
                toClassActivity(this,ChooseCityActivity.class.getName());
                break;
            case R.id.search:
                Intent intent = new Intent(mActivity,NewsActivity.class);
                intent.putExtra("cityId",cityId);
                intent.setFlags(2);//搜索
                startActivity(intent);
                break;
            default:
                break;
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
              cityId = data.getStringExtra("cityId");
              cityName = data.getStringExtra("cityName");
              home_header_cityname.setText(cityName);
              requestTask(type);
        }
    }
}