package com.accessories.seller.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.accessories.seller.R;
import com.accessories.seller.activity.center.SellerInfoActivity;
import com.accessories.seller.adapter.NewsAdpter;
import com.accessories.seller.bean.NewsEntity;
import com.accessories.seller.bean.SellerList;
import com.accessories.seller.fragment.BaseFragment;
import com.accessories.seller.help.PullRefreshStatus;
import com.accessories.seller.help.RequsetListener;
import com.accessories.seller.utils.BaseApplication;
import com.accessories.seller.utils.URLConstants;
import com.accessories.seller.view.CustomListView;
import com.google.gson.reflect.TypeToken;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资讯中心
 */
public class NewsFragment extends BaseFragment implements RequsetListener, CustomListView.OnLoadMoreListener {

    private CustomListView customListView = null;
    private TextView noData;
    private List<NewsEntity> list = new ArrayList<NewsEntity>();
    private LinearLayout searchLL;
    private EditText search_edit;
    private Button search_btn;
    NewsAdpter adapter;
    int pageSize = 20, pageNo = 1;
    private PullRefreshStatus status = PullRefreshStatus.NORMAL;

    private String cityId, cateId;


    int flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            cityId = intent.hasExtra("cityId") ? intent.getStringExtra("cityId") : "";
            cateId = intent.hasExtra("cateId") ? intent.getStringExtra("cateId") : "";
            flag = intent.getFlags();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle(view);
        initView(view);
        if(flag != 2)
        requestTask(0);
    }

    protected void requestData(int req) {
        HttpURL url = new HttpURL();
        url.setmBaseUrl(URLConstants.SHOPLIST);
        Map postParams = new HashMap<String, String>();

        postParams.put("page", "" + pageNo);
        postParams.put("pageSize", "" + pageSize);
        postParams.put("cityId", "" + cityId);
        postParams.put("lat", "" + (BaseApplication.getInstance().mapLocation != null? BaseApplication.getInstance().mapLocation.getLatitude():"0"));
        postParams.put("lng", "" + (BaseApplication.getInstance().mapLocation != null? BaseApplication.getInstance().mapLocation.getLongitude():"0"));
        if (flag == 2) {
            postParams.put("seach", search_edit.getText().toString());//分类ID
        } else {
            postParams.put("cateId", cateId);//分类ID
        }
        RequestParam param = new RequestParam();
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(req), createMyReqErrorListener(), param);

    }

    @Override
    public void handleRspSuccess(int reqCode, Object obj) {
        if (status == PullRefreshStatus.PULL_REFRESH) {
            list.clear();
            customListView.onRefreshComplete();
            status = PullRefreshStatus.NORMAL;
        } else if (status == PullRefreshStatus.LOAD_MORE) {
            customListView.onLoadMoreComplete();
        }
        JsonParserBase<SellerList> jsonParserBase = ParserUtil.fromJsonBase(obj.toString(), new TypeToken<JsonParserBase<SellerList>>() {
        }.getType());
        list.addAll(jsonParserBase.getObj().getArray());
        adapter.notifyDataSetChanged();
        refresh(jsonParserBase.getObj().getArray());
    }

    private void initView(View view) {
        noData = (TextView) view.findViewById(R.id.noData);
        customListView = (CustomListView) view.findViewById(R.id.callListView);
        searchLL = (LinearLayout) view.findViewById(R.id.searchLL);
        search_edit = (EditText) view.findViewById(R.id.search_edit);
        search_btn = (Button) view.findViewById(R.id.search_btn);
        view.findViewById(R.id.close_btn).setOnClickListener(new
                                                                     View.OnClickListener() {
                                                                         @Override
                                                                         public void onClick(View v) {
                                                                          search_edit.setText("");
                                                                         }
                                                                     });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(search_edit.getText().toString())) {
                    pageNo = 1;
                    requestTask(0);
                }
            }
        });
        searchLL.setVisibility(View.GONE);
        if (flag == 2) {
            searchLL.setVisibility(View.VISIBLE);
        }
        customListView.setCanLoadMore(false);
        customListView.setCanRefresh(true);
        adapter = new NewsAdpter(mActivity, list);
        customListView.setAdapter(adapter);
        customListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mActivity, SellerInfoActivity.class);
                intent.putExtra("shopId", list.get(arg2 - 1).getId());
                startActivity(intent);
            }
        });
        customListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                status = PullRefreshStatus.PULL_REFRESH;
                pageNo = 1;
                requestData(0);
            }
        });
        adapter.notifyDataSetChanged();
    }

    /**
     * 页数为1时使用
     */
    private void refresh(List<NewsEntity> infos) {
        if (infos == null || infos.size() == 0) {//显示无数据
            if (list.size() == 0) {
                noData.setVisibility(View.VISIBLE);
                customListView.setVisibility(View.GONE);
            }
        } else {
            customListView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
            if (infos.size() == pageSize) {//有足够的数据,可以下拉刷新
                customListView.setCanLoadMore(true);
                customListView.setOnLoadListener(this);
            } else {
                customListView.setCanLoadMore(false);
            }

        }
    }

    @Override
    public void onLoadMore() {
        status = PullRefreshStatus.LOAD_MORE;
        pageNo++;
        requestData(0);
    }

    @Override
    protected void failRespone() {
        super.failRespone();
        switch (status) {
            case PULL_REFRESH:
                customListView.onRefreshComplete();
                break;
            case LOAD_MORE:
                pageNo--;
                customListView.onLoadMoreComplete(CustomListView.ENDINT_MANUAL_LOAD_DONE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void errorRespone() {
        super.errorRespone();
        failRespone();
    }

    private void initTitle(View view) {
        setLeftHeadIcon(0);
        setTitleText("商家列表");
        if (flag == 2) {
            setTitleText("搜索");
        }
    }

}