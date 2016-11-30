package com.accessories.seller.fragment.center;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accessories.seller.R;
import com.accessories.seller.activity.CallPhoneReceiver;
import com.accessories.seller.bean.Phone;
import com.accessories.seller.bean.SellerInfo;
import com.accessories.seller.fragment.BaseFragment;
import com.accessories.seller.help.RequsetListener;
import com.accessories.seller.utils.BaseApplication;
import com.accessories.seller.utils.URLConstants;
import com.accessories.seller.view.ListViewForScrollView;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;
import com.volley.req.parser.ParserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 关于我们
 *
 * @author czq
 * @time 2015年9月28日上午11:44:26
 */
public class SellerInfoFragment extends BaseFragment implements OnClickListener, RequsetListener {


    @Bind(R.id.selleName)
    TextView selleName;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.bussiness)
    TextView bussiness;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.addressLL)
    LinearLayout addressLL;
    @Bind(R.id.phone1)
    TextView phone1;
    @Bind(R.id.phone1LL)
    LinearLayout phone1LL;
    @Bind(R.id.phone2)
    TextView phone2;
    @Bind(R.id.phone2LL)
    LinearLayout phone2LL;
    @Bind(R.id.tel1)
    TextView tel1;
    @Bind(R.id.tel1LL)
    LinearLayout tel1LL;
    @Bind(R.id.tel2)
    TextView tel2;
    @Bind(R.id.tel2LL)
    LinearLayout tel2LL;
    @Bind(R.id.QQ)
    TextView QQ;
    @Bind(R.id.QQLL)
    LinearLayout QQLL;
    @Bind(R.id.wx)
    TextView wx;
    @Bind(R.id.callNumTv)
    TextView callNumTv;
    @Bind(R.id.wxLL)
    LinearLayout wxLL;
    @Bind(R.id.container)
    LinearLayout container;
    @Bind(R.id.listview)
    ListViewForScrollView listview;

    private String shopId;
    private PhoneAdapter phoneAdapter ;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            requestData(2);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = mActivity.getIntent();
        if (intent != null) {
            shopId = intent.hasExtra("shopId") ? intent.getStringExtra("shopId") : "";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        initView();
        CallPhoneReceiver.mHandler = mHandler;

        listview.setAdapter((phoneAdapter =new PhoneAdapter()));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                phoneStr = ((Phone)phoneAdapter.getItem(position)).getPhone();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ((Phone)phoneAdapter.getItem(position)).getPhone()));
                startActivity(intent);
            }
        });

        img.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int height = (img.getWidth() * 27 / 39);
                ViewGroup.LayoutParams layoutParams = img.getLayoutParams();
                layoutParams.height = height;
                img.setLayoutParams(layoutParams);
                return true;
            }
        });
    }

    private void initTitleView() {
        setLeftHeadIcon(0);
        setTitleText("商家主页");
        setLeftHeadIcon(0);

        requestTask(1);
    }

    private void initView() {
        phone1LL.setOnClickListener(this);
        phone2LL.setOnClickListener(this);
        tel1LL.setOnClickListener(this);
        tel2LL.setOnClickListener(this);
    }


    private String phoneStr = "";

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.phone1LL:
                phoneStr = phone1.getText().toString();
                break;
            case R.id.phone2LL:
                phoneStr = phone2.getText().toString();
                break;
            case R.id.tel1LL:
                phoneStr = tel1.getText().toString();
                break;
            case R.id.tel2LL:
                phoneStr = tel2.getText().toString();
                break;
        }

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneStr));
        startActivity(intent);
//        requestData(2);
    }

    /**
     * 重新登录
     */
    private void reLogin() {
//        startActivityForResult(new Intent(getActivity(), LoginActivity.class), Constant.RELOGIN);
    }

    /**
     * 请求 用户信息
     */
    @Override
    public void requestData(int requestType) {
        HttpURL url = new HttpURL();
        Map postParams = new HashMap();
        switch (requestType) {
            case 1:
                url.setmBaseUrl(URLConstants.SHOPDETAIL);
                postParams.put("shopId", shopId);
                break;
            case 2:
                url.setmBaseUrl(URLConstants.CALL);
                postParams.put("osType", "1");//安卓  1   IOS  2
                postParams.put("phone", phoneStr);
                postParams.put("shopId", shopId);
                postParams.put("userId", BaseApplication.getUserInfo().getId());
                break;
        }
        RequestParam param = new RequestParam();
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestType), createMyReqErrorListener(), param);
    }

    @Override
    public void handleRspSuccess(int requestType, Object obj) {
        switch (requestType) {
            case 1:

                JsonParserBase<SellerInfo> jsonParserBase = ParserUtil.fromJsonBase(obj.toString(), new TypeToken<JsonParserBase<SellerInfo>>() {
                }.getType());
                SellerInfo balanceInfo = jsonParserBase.getObj();
                if (balanceInfo == null) return;
                ImageLoader.getInstance().displayImage(balanceInfo.getShopPic(), img);
                selleName.setText(balanceInfo.getShopName());
                bussiness.setText(balanceInfo.getShopDesc());
                callNumTv.setText(TextUtils.isEmpty(balanceInfo.getPhoneCalledNum())?"0":balanceInfo.getPhoneCalledNum());

                if(balanceInfo.getPhoneAry() == null || balanceInfo.getPhoneAry().size()==0){
                    if (!TextUtils.isEmpty(balanceInfo.getShopAddr())) {
                        address.setText(balanceInfo.getShopAddr());
                        addressLL.setVisibility(View.VISIBLE);
                    }
                    if (!TextUtils.isEmpty(balanceInfo.getPhone())) {
                        phone1.setText(balanceInfo.getPhone());
                        phone1LL.setVisibility(View.VISIBLE);
                    }
                    if (!TextUtils.isEmpty(balanceInfo.getPhone2())) {
                        phone2.setText(balanceInfo.getPhone2());
                        phone2LL.setVisibility(View.VISIBLE);
                    }
                    if (!TextUtils.isEmpty(balanceInfo.getTel())) {
                        tel1.setText(balanceInfo.getTel());
                        tel1LL.setVisibility(View.VISIBLE);
                    }
                    if (!TextUtils.isEmpty(balanceInfo.getTel2())) {
                        tel2.setText(balanceInfo.getTel2());
                        tel2LL.setVisibility(View.VISIBLE);
                    }
                }else {
                    phoneAdapter.addList(balanceInfo.getPhoneAry());
                }

                if (!TextUtils.isEmpty(balanceInfo.getQq())) {
                    QQ.setText(balanceInfo.getQq());
                    QQLL.setVisibility(View.VISIBLE);
                }
                if (!TextUtils.isEmpty(balanceInfo.getWx())) {
                    wx.setText(balanceInfo.getWx());
                    wxLL.setVisibility(View.VISIBLE);
                }
                break;
            case 2:

                break;

        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            requestTask(1);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class PhoneAdapter extends BaseAdapter {
        private List<Phone> mItemList;

        private String joniorId = "";

        @Override
        public int getCount() {
            if (mItemList == null) mItemList = new ArrayList<>();
            return mItemList == null ? 0 : mItemList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup arg2) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_phone, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Phone idInfo = mItemList.get(position);
            if(idInfo != null){
                holder.nameTv.setText(idInfo.getName()+":");
                holder.telTv.setText(idInfo.getPhone());
            }
            return convertView;
        }


        @Override
        public Object getItem(int position) {

            return mItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addList(ArrayList list) {
            if (mItemList == null) mItemList = new ArrayList<>();
            mItemList.clear();
            mItemList.addAll(list);
            notifyDataSetChanged();
        }


       class ViewHolder {
            @Bind(R.id.nameTv)
            TextView nameTv;
            @Bind(R.id.telTv)
            TextView telTv;
            @Bind(R.id.tel2LL)
            LinearLayout tel2LL;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
