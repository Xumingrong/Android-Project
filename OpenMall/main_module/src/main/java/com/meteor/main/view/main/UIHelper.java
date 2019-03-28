package com.meteor.main.view.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Author：Meteor
 * date：2018/8/1 10:34
 * desc：
 */
public class UIHelper {
    public final static String UI_SCHEME = "mall://";
    private final static String TAG = "UIHelper";

    public final static String HOME = "home";
    public final static String CLASSIFICATION = "classification";
    public final static String FIND = "find";
    public final static String SHOPPING = "shopping";
    public final static String MINE = "mine";
//    private static long lastJumpTime = 0;//最近一次跳转的时间

//    private static Handler handler = new Handler(BaseApplication.getApplication().getMainLooper()) {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    lastJumpTime = 0;
//                    break;
//            }
//        }
//    };

    private static void parseUri(Activity activity, String uri) {
        try {
            if (activity instanceof MainActivity) {
                MainActivity mainActivity = ((MainActivity) activity);
                Uri mUri = Uri.parse(uri);
                String host = mUri.getHost();
                if (!TextUtils.isEmpty(host)) {
                    switch (host) {
                        case HOME:
                            mainActivity.setCurrentTab(0);
                            break;
                        case CLASSIFICATION:
                            mainActivity.setCurrentTab(1);
                            break;
                        case FIND:
                            mainActivity.setCurrentTab(2);
                            break;
                        case SHOPPING:
                            mainActivity.setCurrentTab(3);
                            break;
                        case MINE:
                            mainActivity.setCurrentTab(4);
                            break;
                        default:
                            startActivity(activity, uri);
                    }
                } else {
                    startActivity(activity, uri);
                }
            } else {
                startActivity(activity, uri);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 跳转到首页
     *
     * @param activity
     */
    public static void jumpToHome(Activity activity) {
        String uri = "mall://home";
        startActivity(activity, uri);
    }

    /**
     * 跳转到分类
     *
     * @param activity
     */
    public static void jumpToClassification(Activity activity) {
        String uri = "mall://classification";
        startActivity(activity, uri);
    }

    /**
     * 跳转到发现
     *
     * @param activity
     */
    public static void jumpToFind(Activity activity) {
        String uri = "mall://find";
        startActivity(activity, uri);
    }

    /**
     * 跳转到购物车
     *
     * @param activity
     */
    public static void jumpToShopping(Activity activity) {
        String uri = "mall://shopping";
        startActivity(activity, uri);
    }

    /**
     * 跳转到我的
     *
     * @param activity
     */
    public static void jumpToMine(Activity activity) {
        String uri = "mall://mine";
        startActivity(activity, uri);
    }

    public static void startActivity(Context context, String uri) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
    }

}
