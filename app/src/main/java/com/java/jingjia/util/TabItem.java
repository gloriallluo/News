package com.java.jingjia.util;

import android.widget.Button;

public class TabItem {

    private final String TAG = "TabItem";
    public static final String ALL = "all";
    public static final String NEWS = "news";
    public static final String PAPER = "paper";
    public static final long ALL_ID = 1;
    public static final long NEWS_ID = 2;
    public static final long PAPER_ID = 3;

    private String type;
    private long tabId;

    public TabItem(final String type) {
        this.type = type;
        if (type == ALL) {
            tabId = ALL_ID;
        } else if (type == NEWS) {
            tabId = NEWS_ID;
        } else {
            tabId = PAPER_ID;
        }
    }

    public String getType() {
        return type;
    }

    public long getTabId() {
        return tabId;
    }
}
