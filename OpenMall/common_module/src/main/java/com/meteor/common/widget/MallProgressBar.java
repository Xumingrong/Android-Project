package com.meteor.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.meteor.common.R;

/**
 * Author：Meteor
 * date：2018/7/25 17:59
 * desc：进度条
 */
public class MallProgressBar extends LinearLayout {
    public MallProgressBar(Context context) {
        super(context);
    }

    private ProgressBar mProgressBar;

    public MallProgressBar(Context context, AttributeSet attr) {
        super(context, attr);
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.mall_progress, this, true);
        this.mProgressBar = ((ProgressBar) findViewById(R.id.progressbar));
        hideProgress();
    }

    public void hideProgress() {
        this.mProgressBar.setVisibility(View.GONE);
    }

    public void showProgress() {
        this.mProgressBar.setVisibility(View.VISIBLE);
    }
}
