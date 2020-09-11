package com.java.jingjia.util.news;

import android.widget.Button;

public class TabItem {

    private final String TAG = "TabItem";
    public static final String ALL = "all";
    public static final String NEWS = "news";
    public static final String PAPER = "paper";

    private String type;
    private boolean shaking = false;

    public TabItem(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public boolean getShaking() {
        return shaking;
    }

    public void setShaking(boolean shaking) {
        this.shaking = shaking;
    }
}
