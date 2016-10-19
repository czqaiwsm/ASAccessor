package com.accessories.city.fragment.center;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.fragment.BaseFragment;

public class ServiceProtocolFragment extends BaseFragment {

    private WebView mWebView;
    private TextView content;
    private String url;
    private String title = "";
    private int flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if(intent != null){
//            url = intent.getStringExtra("url");
            flag = intent.getFlags();
            switch (flag){
                case 11:
                    title = getResources().getString(R.string.app_name);
                    break;
                case 12:
                    title = "关于我们";
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_web_logic, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();

    }

    @SuppressWarnings("deprecation")
    private void initView(View view) {
        // TODO Auto-generated method stub
        setTitleText(title);
        setLeftHeadIcon(R.drawable.back, new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                getActivity().finish();
            }
        });
        mWebView = (WebView) view.findViewById(R.id.rule_webview);
        content = (TextView) view.findViewById(R.id.content);
        mWebView.setVisibility(View.GONE);
        mWebView.setWebViewClient(getClient());
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(false);
        int screenDensity = getResources().getDisplayMetrics().densityDpi;
        WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
        switch (screenDensity) {
            case DisplayMetrics.DENSITY_LOW:
                zoomDensity = WebSettings.ZoomDensity.CLOSE;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                zoomDensity = WebSettings.ZoomDensity.FAR;
                break;
        }
        WebSettings settings = mWebView.getSettings();
        settings.setDefaultZoom(zoomDensity);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);// 可能的话不要超过屏幕宽度
        if(TextUtils.isEmpty(url)){
            url = "www.baidu.com";
        }
        mWebView.loadUrl(url);

    }

    private void initData(){
        String str = "";
        switch (flag){
            case 11:
                str = "车易配会员协议\n" +
                        "\n" +
                        "    车易配会员，除全部接受注册会员注册协议以外，并遵守以下条件和条款，享受南昌企动广告有限公司（简称车易配，下同）为您提供所享有的服务。\n" +
                        "    欢迎阅读南昌企动广告有限公司（车易配）服务条款协议（下称“本协议”）。本协议阐述之条款和条件适用于您使用车易配网站（所涉域名为：ncqdgg.cn、车易配手机应用，下同）所提供的各种服务（下称“服务”），车易配网站的权利和义务全部归属南昌企动广告有限公司所有；\n" +
                        "    同意本协议即视为车易配会员按照约定为车易配网站提供所承诺的服务，车易配网站认可车易配会员所提供的服务并自愿接受车易配会员为其提供服务。\n" +
                        "    车易配注册会员在接受或者请求车易配企业会员为其提供服务时，车易配企业会员应自动提供齐全的企业合法运营证明材料。\n" +
                        "    车易配企业会员以协议办理或者委托的形式为车易配注册会员提供服务。车易配注册会员在接受车易配企业会员为其提供服务时应提供证明其会员身份的会员卡、车易配（www.ncqdgg.com）网站或应用系统的账号、密码。\n" +
                        "    车易配企业会员为车易配注册会员提供的所有服务必须与车易配登记的经营信息相符。车易配企业会员作为车易配企业会员，会员资格为每一个实体店一个账户制度，连锁企业可以拥有多个会员账户。\n" +
                        "                                                      （2016年7月31日）\n";
                break;
            case 12:
                str =   "     南昌企动广告有限公司成立于2013年 是一家致力于汽车配件、汽车装潢行业的专业广告公司。\n" +
                        "\n" +
                        "     低成本高效率为客户推广产品是我们的服务宗旨，为客户及用户搭建信息桥梁是我们的职责。\n" +
                        "\n" +
                        "     “车易配”是本公司为汽车配件 汽车装潢行业专业打造的手机APP软件，同时也是最方便、\n" +
                        "      最快捷 最有效的市场客户开发手机软件，无需在网络支持的情况下打开软件，\n" +
                        "     “一机在手、配件无忧”。";
                break;
        }
        content.setText(str);
    }


    private WebViewClient getClient() {
        // TODO Auto-generated method stub
        WebViewClient mClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                // startLoading(mFragment);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }

        };
        return mClient;
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        if (mWebView != null) {
            mWebView.getSettings().setLoadWithOverviewMode(false);
            mWebView.getSettings().setUseWideViewPort(false);
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.getSettings().setSupportZoom(false);
            mWebView.getSettings().setBuiltInZoomControls(false);
            mWebView.destroy();
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ViewGroup view = (ViewGroup) getActivity().getWindow().getDecorView();
        view.removeAllViews();
    }



}
