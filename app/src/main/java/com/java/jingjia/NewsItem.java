package com.java.jingjia;

import android.widget.LinearLayout;

import java.io.Serializable;

public class NewsItem implements Serializable {
    LinearLayout mLayout;
    long newsId;
    String title;
    String source = "xia bb news";
    String time = "2020-09-04";

    public NewsItem(long id, String title) {
        newsId = id;
        this.title = title;
    }

    public long getId() {
        return newsId;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getTime() {
        return time;
    }
}
