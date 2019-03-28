package com.meteor.main.view.main;

import com.meteor.main.R;
import com.meteor.main.view.fragment.ClassificationFragment;
import com.meteor.main.view.fragment.FindFragment;
import com.meteor.main.view.fragment.HomeFragment;
import com.meteor.main.view.fragment.MineFragment;
import com.meteor.main.view.fragment.ShoppingCardFragment;

/**
 * Author：Meteor
 * date：2018/8/1 10:34
 * desc：
 */

public enum MainTab {

    /**
     * 首页
     */
    HOME(0, R.string.main_home, R.drawable.ig_home_selector, HomeFragment.class),
    /**
     * 分类
     */
    CLASSIFICATION(1, R.string.main_classification, R.drawable.ig_classification_selector, ClassificationFragment.class),
    /**
     * 发现
     */
    INVEST(2, R.string.main_find, R.drawable.ig_find_selector, FindFragment.class),

    /**
     * 购物车
     */
    FIND(3, R.string.main_shopping, R.drawable.ig_shopping_selector, ShoppingCardFragment.class),

    /**
     * 我的
     */
    MINE(4, R.string.main_mine, R.drawable.ig_mine_selector, MineFragment.class);


    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
