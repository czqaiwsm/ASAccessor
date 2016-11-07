package com.accessories.seller.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.accessories.seller.R;
import com.pickerview.adapter.ArrayWheelAdapter;
import com.pickerview.lib.WheelView;
import com.pickerview.listener.OnItemSelectedListener;
import com.pickerview.view.BasePickerView;

import java.util.ArrayList;

/**
 * Created by Sai on 15/11/22.
 */
public class SingelPickerView extends BasePickerView implements View.OnClickListener {

    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private WheelView wheelView = null;

    private SelectOnItem selectOnItem = null;

    public SingelPickerView(Context context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.pickerview_time, contentContainer);
        // -----确定和取消按钮
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // 数据选择器
        wheelView = (WheelView) view.findViewById(R.id.year);
        wheelView.setVisibility(View.VISIBLE);
        wheelView.setTextSize(20);
        wheelView.setCyclic(false);

    }




    public void setCurrent(int index){
        wheelView.setCurrentItem(index);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
        } else {
           if(selectOnItem != null){
               selectOnItem.itemSelect(wheelView.getCurrentItem());
           }
        }
        dismiss();
        return;

    }

    public void setAdapter(ArrayList<String> list){
        if(list != null && list.size()>0)
       wheelView.setAdapter(new ArrayWheelAdapter(list));
    }

    public void pickShow(){
        if(!wheelView.isShown()){
            show();
        }
    }

    public void setOnItemSelectListener(OnItemSelectedListener listener){
        wheelView.setOnItemSelectedListener(listener);
    }


    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setOnItem(SelectOnItem selectOnItem){
        this.selectOnItem = selectOnItem;
    }

    public interface SelectOnItem{
        void itemSelect(int selectIndex);
    }

}
