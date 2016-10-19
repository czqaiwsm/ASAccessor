package com.pickerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.accessories.city.R;
import com.accessories.city.bean.DateBean;
import com.pickerview.adapter.ArrayWheelAdapter;
import com.pickerview.adapter.NumericWheelAdapter;
import com.pickerview.lib.WheelView;
import com.pickerview.view.BasePickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sai on 15/11/22.
 */
public class TimePickerView extends BasePickerView implements View.OnClickListener {
    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN , YEAR_MONTH
    }// 四种选择模式，年月日时分，年月日，时分，月日时分

    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private OnTimeSelectListener timeSelectListener;

    //默认选中当前时间
    Calendar calendar = Calendar.getInstance();
    private ArrayList<DateBean> dateBeans = new ArrayList<DateBean>();

    // 添加大小月月份并将其转换为list,方便之后的判断
    String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
    String[] months_little = { "4", "6", "9", "11" };

    List<String> list_big = Arrays.asList(months_big);
    List<String> list_little = Arrays.asList(months_little);
    String[] times = {"AM","PM"};
    List<String> list_times = Arrays.asList(times);
    WheelView dayWV,hourWV,minWV,segWV;

    public TimePickerView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.pickerview_time, contentContainer);
        // -----确定和取消按钮
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // ----时间转轮
        final View timepickerview = findViewById(R.id.timepicker);

        setCancelable(true);
        dayWV = (WheelView)findViewById(R.id.day);
        hourWV = (WheelView)findViewById(R.id.hour);
        minWV = (WheelView)findViewById(R.id.min);
        segWV = (WheelView)findViewById(R.id.sege);

        dayWV.setVisibility(View.GONE);
        hourWV.setVisibility(View.VISIBLE);
        minWV.setVisibility(View.VISIBLE);
        segWV.setVisibility(View.VISIBLE);
        segWV.setCyclic(false);

        ymd();
        dayWV.setAdapter(new ArrayWheelAdapter(dateBeans));

        hourWV.setAdapter(new NumericWheelAdapter(1,12));
        minWV.setAdapter(new NumericWheelAdapter(1,60));
        segWV.setAdapter(new ArrayWheelAdapter(list_times));
    }

    public void ymd(){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int maxItem = 30;
        DateBean dateBean = null;
        for (int i = 0;i<4;i++){
            int tempMonth = month+i;
            int tempYear = year;
            if((month+i)>12){
                tempMonth = (month+i) % 12;
                tempYear  = year+1;
            }
            if (list_big.contains(String.valueOf(tempMonth))) {
                maxItem = 31;
            } else if (list_little.contains(tempMonth)) {
                maxItem = 30;
            } else {
                if ((tempYear % 4 == 0 && tempYear % 100 != 0)
                        || year % 400 == 0){
                    maxItem = 29;
                }
                else{
                    maxItem = 28;
                }
            }

            int start = 1;
            if(i == 0) start = day;

            for (int j = start;j<= maxItem;j++){
                dateBean = new DateBean();
                String dateStr = ""+tempYear+"-";
                if(tempMonth<10){
                    dateStr += "0"+tempMonth+"-";
                }else {
                    dateStr += tempMonth+"-";
                }
                if(j<10){
                    dateStr += "0"+j;
                }else {
                    dateStr += j;
                }
                System.out.println(dateStr);
                dateBean.setYmd(dateStr);
                dateBeans.add(dateBean);
            }
        }

    }


//    /**
//     * 指定选中的时间，显示选择器
//     *
//     * @param date
//     */
//    public void show(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        if (date == null)
//            calendar.setTimeInMillis(System.currentTimeMillis());
//        else
//            calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int hours = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//        wheelTime.setPicker(year, month, day, hours, minute);
//        show();
//    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (timeSelectListener != null) {
                try {
//                     String ymd = dateBeans.get(dayWV.getCurrentItem()).getYmd();
                    int hour = hourWV.getCurrentItem()+1;
                    int min = minWV.getCurrentItem()+1;

                    String hourStr = ""+hour;
                    String minStr = ""+min;
                    if(segWV.getCurrentItem() == 0){//AM
                        if(hour<10){
                            hourStr = "0"+hour;
                        }
                    }else {
                        hour = hour + 12;
                        hourStr = hour+"";
                        if(hour == 24){
                            hourStr = "00";
                        }
                    }

                    if(min<10){
                        minStr = "0"+min;
                    }

                    System.out.println(hourStr+":"+minStr);

                    timeSelectListener.onTimeSelect(hourStr+":"+minStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dismiss();
            return;
        }
    }

    public interface OnTimeSelectListener {
        void onTimeSelect(String date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }
}
