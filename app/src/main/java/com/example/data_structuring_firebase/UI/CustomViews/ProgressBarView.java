package com.example.data_structuring_firebase.UI.CustomViews;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.RequiresApi;

import com.example.data_structuring_firebase.R;

public class ProgressBarView extends LinearLayout
{
    int value;
    int maxLimit=100;
    String title;
    LinearLayout indicator,indicator_bg;
    TextView tv_title,tv_valueIndicator;

    public ProgressBarView(Context context) {
        super(context);
        Init(context);
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context);
    }

    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        tv_title.setText(title);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        incrementBarTo(value);
    }

    public int getLimit() {
        return maxLimit;
    }

    public void setLimit(int maxLimit) {
        this.maxLimit = maxLimit;
        incrementBarTo(value);
    }

    private void Init(Context context) {
        inflate(context, R.layout.view_progress_bar, this);
        tv_title = findViewById(R.id.tv_title);
        tv_valueIndicator = findViewById(R.id.value_indicator);
        indicator=findViewById(R.id.indicator);
        indicator_bg=findViewById(R.id.indicator_bg);
    }

    private void incrementBarTo(int value){

        final float width=getContext().getResources().getDisplayMetrics().widthPixels;

        if(value>maxLimit)
            value=maxLimit;
        int currentWidth=(int)(value*width/maxLimit);
        indicator.getLayoutParams().width=currentWidth;
        int perc=value*100/maxLimit;
        int drawableId;
        if(perc<=30)
            drawableId=R.drawable.bg_highlight_low;
        else if(perc>30 && perc<=60)
            drawableId=R.drawable.bg_highlight_medium;
        else
            drawableId=R.drawable.bg_highlight_high;

        indicator.setBackground(getContext().getResources().getDrawable(drawableId));
        tv_valueIndicator.setText(String.format("%d%%", (int) value * 100 / maxLimit));

    }


}
