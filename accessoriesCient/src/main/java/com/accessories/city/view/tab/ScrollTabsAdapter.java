package com.accessories.city.view.tab;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.accessories.city.R;

public class ScrollTabsAdapter extends TabAdapter {
    private Activity activity;

    DisplayMetrics dm;

    public ScrollTabsAdapter(Activity activity) {
        super();
        this.activity = activity;
        dm = new DisplayMetrics();//DisplayMetrics 类提供了一种关于显示的通用信息，如显示大小，分辨率和字体。
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);//将当前窗口的一些信息放在DisplayMetrics类中
    }

    @Override
    public View getView(int position) {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);//Activity里面就使用了LayoutInflater来载入界面, 通过getSystemService(Context.LAYOUT_INFLATER_SERVICE)方法可以获得一个 LayoutInflater,
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.tabs,
                null);

        layout.setMinimumWidth(dm.widthPixels / 2);
        TextView textView = (TextView) layout.findViewById(R.id.text);
        textView.setText(tabsList.get(position));
        TextView line = (TextView) layout.findViewById(R.id.tab_line);
        if (position == 1) {
            line.setVisibility(View.INVISIBLE);
        }
        return layout;
    }

}
