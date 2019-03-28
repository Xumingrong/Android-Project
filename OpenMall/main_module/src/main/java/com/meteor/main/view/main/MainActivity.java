package com.meteor.main.view.main;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.meteor.common.base.BaseMvcActivity;
import com.meteor.main.R;
import com.meteor.main.R2;
import com.meteor.main.arouter.RouterUrl;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;


/**
 * Author：Meteor
 * date：2018/7/30 11:36
 * desc：MainActivity
 */

@Route(path = RouterUrl.BASE_MAIN)
public class MainActivity extends BaseMvcActivity {
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabHost;
    @BindView(R2.id.main_real_tab_content)
    FrameLayout mainRealTabContent;
    @BindView(R2.id.splash_jump)
    TextView splashJump;
    @BindView(R2.id.splash)
    FrameLayout splash;

    private SparseArray<Integer> toolbarAlpha = new SparseArray<>();


    //广告页：splash
    private Timer timer = new Timer();
    private Integer JUMP_DURATION = 1;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
//              splashJump.setText(SpannableStringBuilderUtils.SpannableStringSize("跳过 " + JUMP_DURATION + "s", SystemMethod.dip2px(MainActivity.this, 12), 0, 2));
                JUMP_DURATION--;
                if (JUMP_DURATION <= -1) {
                    splash.setVisibility(View.GONE);
                    timerDestroy();
                }
            }
        }
    };

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        loadTab();
        if (getUri() != null) {
            Uri uri = getUri();
            String host = uri.getHost();
            if (!TextUtils.isEmpty(host)) {
                switch (host) {
                    case UIHelper.HOME:
                        tabHost.setCurrentTab(0);
                        break;
                    case UIHelper.CLASSIFICATION:
                        tabHost.setCurrentTab(1);
                        break;
                    case UIHelper.FIND:
                        tabHost.setCurrentTab(2);
                        break;
                    case UIHelper.SHOPPING:
                        tabHost.setCurrentTab(3);
                        break;
                    case UIHelper.MINE:
                        tabHost.setCurrentTab(4);
                        break;
                }
            }
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0, 1 * 1000);
    }

    @Override
    protected void initEvent() {
        splashJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splash.setVisibility(View.GONE);
                timerDestroy();
            }
        });
    }

    private void loadTab() {
        // versionUpdate();
        tabHost.setup(this, getSupportFragmentManager(), R.id.main_real_tab_content);
        tabHost.getTabWidget().setShowDividers(0);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                setTbAlpha(toolbarAlpha.get(tabHost.getCurrentTab()));
            }
        });
        for (MainTab mainTab : MainTab.values()) {
            TabHost.TabSpec tab = tabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = LayoutInflater.from(this).inflate(R.layout.activity_main_tab_indicator, null, false);

            ImageView icon = (ImageView) indicator.findViewById(R.id.tab_icon);
            icon.setImageResource(mainTab.getResIcon());
//
//            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
//            title.setText(getString(mainTab.getResName()));

            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            toolbarAlpha.put(mainTab.getIdx(), 255);
            tabHost.addTab(tab, mainTab.getClz(), null);
        }
    }

    public void setTbAlpha(int alpha) {
        toolbarAlpha.put(tabHost.getCurrentTab(), alpha);
    }

    /**
     * @param id:设置为哪个页面
     */
    public void setCurrentTab(int id) {
        tabHost.setCurrentTab(id);
    }

    public void timerDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
