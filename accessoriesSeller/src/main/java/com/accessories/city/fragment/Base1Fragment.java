package com.accessories.city.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.accessories.city.R;

/**
 * 此Fragment日后研究使用
 * @desc 公共的Fragment，初始化公共标题部分；如果不需要公共标题，可隐藏
 * 注意:如果需要标题，子类必须重写 onCreateView，并调用 super.onCreateView(inflater, container, savedInstanceState);
 *     例如:@Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);//如果需要系统标题，就必须加这段
            View view = inflater.inflate(R.layout.test,null);
            return view;
            }
 * @creator caozhiqing
 * @data 2016/3/10
 */
public class Base1Fragment extends Fragment {

    private View titleView = null;
    private TextView headerTitle;
    private ImageView headerLeftIcon;
    private ImageView headerRightIcon;
    private ImageView heederRightMore;
    private TextView headerRightText;
    private Activity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /***************添加自定义标题start********************/
            if (titleView != null) {
                if (view instanceof ViewGroup) {
                    if (view instanceof LinearLayout) {
                        ((ViewGroup) view).addView(titleView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.header_bar_height)));
                    } else {
                        final View childView = ((ViewGroup) view).getChildAt(0);
                        childView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                            @Override
                            public boolean onPreDraw() {
                                childView.setTop((int) getResources().getDimension(R.dimen.header_bar_height));
                                ((ViewGroup) view).removeView(titleView);
                                ((ViewGroup) view).addView(titleView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.header_bar_height)));
                                return true;
                            }
                        });
                    }
                }
                onInitTitleView(titleView);
        }
        /***************添加自定义标题start********************/

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * 设置头部是否可见
     *
     * @param visible
     */
    protected void setTitlteVisible(int visible) {
        titleView.setVisibility(visible);
    }

    private void onInitTitleView(View view) {
        headerTitle = (TextView) view.findViewById(R.id.header_title);
        headerLeftIcon = (ImageView) view.findViewById(R.id.header_left_icon);
        headerRightIcon = (ImageView) view.findViewById(R.id.header_right_icon);
        heederRightMore = (ImageView) view.findViewById(R.id.header_right_more);
        headerRightText = (TextView) view.findViewById(R.id.header_rignt_text);
    }

    /**
     *
     * @param stringId
     */
    protected void setTitleText(int stringId) {
        if (null != headerTitle) {
            headerTitle.setText(stringId);
        }
    }

    /**
     * @param string
     */
    protected void setTitleText(String string) {
        if (null != headerTitle) {
            headerTitle.setText(string);
        }
    }

    protected void setTitleOnClickListener(View.OnClickListener onClickListener) {
        if (null != headerTitle) {
            headerTitle.setClickable(true);
            headerTitle.setOnClickListener(onClickListener);
        }
    }

    /**
     * @param drawableId
     */
    protected void setLeftHeadIcon(int drawableId) {
        if (null != headerLeftIcon) {
            if (drawableId == -1) {
                headerLeftIcon.setVisibility(View.GONE);
            } else {
                headerLeftIcon.setVisibility(View.VISIBLE);
                if (drawableId != 0) {
                    headerLeftIcon.setImageResource(drawableId);
                }
                headerLeftIcon.setEnabled(true);
                headerLeftIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Log.e(">>>>>>>>>>>>","onCLicek");
                        mActivity.finish();
                    }
                });

            }
        }

    }

    /*
     * @param stringId
     */
    protected void setLeftHeadIcon(int drawableId, View.OnClickListener onClickListener) {
        if (null != headerLeftIcon) {
            headerLeftIcon.setVisibility(View.VISIBLE);
            if (drawableId != 0) {
                headerLeftIcon.setImageResource(drawableId);
            }
            headerLeftIcon.setOnClickListener(onClickListener);
        }

    }

    /**
     * @param drawableId
     */
    protected void setRightHeadIcon(int drawableId, View.OnClickListener listener) {

        if (null != headerRightIcon) {
            headerRightIcon.setVisibility(View.VISIBLE);
            headerRightIcon.setImageResource(drawableId);
            headerRightIcon.setOnClickListener(listener);
        }
    }

    /**
     * @param drawableId
     */
    public void setRightHeadIcon(int drawableId) {
        if (null != headerRightIcon) {
            headerRightIcon.setVisibility(View.VISIBLE);
            headerRightIcon.setImageResource(drawableId);
            headerRightIcon.setClickable(true);
        }
    }

    public View getRightHeadView() {
        if (null != heederRightMore) {
            return heederRightMore;
        }
        return null;
    }

    /**
     * @param drawableId
     */
    protected void setRightMoreIcon(int drawableId, View.OnClickListener listener) {
        if (null != heederRightMore) {
            heederRightMore.setVisibility(View.VISIBLE);
            heederRightMore.setImageResource(drawableId);
            heederRightMore.setOnClickListener(listener);
        }
    }




}