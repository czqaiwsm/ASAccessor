package com.accessories.city.fragment.msg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.*;
import com.accessories.city.R;
import com.accessories.city.activity.home.NewsActivity;
import com.accessories.city.adapter.ClassTypeAdpter;
import com.accessories.city.adapter.GuideViewPagerAdapter;
import com.accessories.city.bean.BannerImgInfo;
import com.accessories.city.bean.CateListBean;
import com.accessories.city.bean.CateSubTypeEntity;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.fragment.TeacherHomePageFragment;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.CateListParse;
import com.accessories.city.utils.BaseApplication;
import com.accessories.city.utils.DensityUtils;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.view.CustomListView;
import com.accessories.city.view.GridViewForScrollView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsgInfosFragment extends BaseFragment implements RequsetListener {

    private CustomListView callListView;//
    private GridViewForScrollView viewForScrollView = null;
    private View converView;
    private ImageView banner;
    private LinearLayout guideLL;
    private List<CateSubTypeEntity> list = null;
    private ClassTypeAdpter adapter;

    private ViewPager viewpager = null;
    private GuideViewPagerAdapter guideAdapter = null;
    private ArrayList<BannerImgInfo> bannerImgInfos = new ArrayList<BannerImgInfo>();

    private boolean prepare = false;
    private boolean isInit = false;

    private int type = 0;//1 汽车配件 2 汽车用品 3 商用车配件 4

    public MsgInfosFragment(int type){
        this.type = type;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // startReqTask(this);
        // mLoadHandler.sendEmptyMessageDelayed(Constant.NET_SUCCESS, 100);// 停止加载框
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_msg_infos, container, false);
        converView = inflater.inflate(R.layout.center_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        callListView = (CustomListView)view.findViewById(R.id.callListView);
        initView(converView);
        if (type == 2){
            setTitleText("汽车用品");
        }else if(type == 3){
            setTitleText("商用车配件");
        }
        prepare = true;
        isInit = true;

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
    }

    private void onLoade(){
        if(prepare && isVisible()){
            requestTask(1);
            prepare = false;
        }
        if(isInit && isVisible()){
            initGuidBanner(converView);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            dismissLoadingDilog();
        }
        onLoade();
    }

    private void initView(View view) {
        viewpager = (ViewPager) view.findViewById(R.id.id_guide_viewpager);
        guideLL = (LinearLayout) view.findViewById(R.id.guideLL);
        viewForScrollView = (GridViewForScrollView) view.findViewById(R.id.callGridView);
        banner = (ImageView)view.findViewById(R.id.banner);
        ArrayAdapter mArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new Object[]{});
//        callListView.setRefreshBackGround(getResources().getColor(R.color.bg));
        callListView.setAdapter(mArrayAdapter);
        callListView.addHeaderView(converView);
        callListView.setCanLoadMore(false);

        callListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(1);
            }
        });
        setSingle();

        viewForScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity,NewsActivity.class);
                intent.putExtra("cityId", BaseApplication.getInstance().location[1]);
                intent.putExtra("cateId",list.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void setSingle(){
        list = new ArrayList<CateSubTypeEntity>();
        adapter = new ClassTypeAdpter(getActivity(),list);
        viewForScrollView.setAdapter(adapter);

    }
    /**
     * 初始化轮播图
     */
    private void initGuidBanner(View view) {
        if (view == null) {
            return;
        }
        bannerImgInfos.clear();
        bannerImgInfos.addAll(TeacherHomePageFragment.bannerImgInfos);
        guideAdapter = new GuideViewPagerAdapter(bannerImgInfos, view, mActivity,true);
        guideAdapter.setDotAlignBottom((int) DensityUtils.px2dp(mActivity, 10f));
        if(isInit){
            guideAdapter.setAutoPlay(viewpager, true);
        }
        viewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                guideAdapter.moveCursorTo(position);// 点的移动
            }
        });

        viewpager.setAdapter(guideAdapter);
        if (bannerImgInfos != null && bannerImgInfos.size() > 1) {
            viewpager.setCurrentItem(bannerImgInfos.size() * 30);
        }
        isInit = false;
    }

    @Override
    protected void requestData(int requestCode) {
        HttpURL url = new HttpURL();
        Map postParams = new HashMap<String,String>();
        url.setmBaseUrl(URLConstants.CATEGORYLIST);
        postParams.put("type", ""+type);
        RequestParam param = new RequestParam();
        param.setmParserClassName(CateListParse.class.getName());
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestCode), createMyReqErrorListener(), param);
    }
    @Override
    public void handleRspSuccess(int reqCode,Object obj) {
        switch (reqCode){
            case 1:
                callListView.onRefreshComplete();
                JsonParserBase<CateListBean> jsonParserBase = (JsonParserBase<CateListBean>) obj;
                ArrayList<CateSubTypeEntity> chooseTeachBean = jsonParserBase.getObj().getArray();
                    refresh(chooseTeachBean);
                break;
        }
    }

    @Override
    protected void failRespone() {
        super.failRespone();
        callListView.onRefreshComplete();
    }

    @Override
    protected void errorRespone() {
        super.errorRespone();
        failRespone();
    }
    /**
     * 页数为1时使用
     *
     * @param teacherInfos
     */
    private void refresh(ArrayList<CateSubTypeEntity> teacherInfos) {
        list.clear();
        if(teacherInfos != null){
            list.addAll(teacherInfos);
        }
        adapter.notifyDataSetChanged();
    }

    public enum CateType{
        

    }

}
