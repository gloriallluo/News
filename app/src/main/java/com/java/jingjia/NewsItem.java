package com.java.jingjia;

import android.widget.LinearLayout;

import java.io.Serializable;

public class NewsItem implements Serializable {

    private final String TAG = "NewsItem";
    LinearLayout mLayout;
    String id;
    /**
     * 用于请求新闻正文的id
     */
    String category;
    /**
     * 分类(直接从网址读为空)
     */
    String content;
    /**
     * 正文
     */
    String title;
    /**
     * 标题
     */
    String language;
    /**
     * 语言
     */
    String source = "xia bb news";
    /**
     * 来源
     */
    String time = "2020-09-04";
    /**
     * 时间
     */
    String type;

    /**
     * 种类news或paper
     */

    public NewsItem(String id) {
        this.id = id;
    }

    public NewsItem(
            String id,     /* 用于请求新闻正文的id */
            String category,/* 分类(直接从网址读为空) */
            String content, /* 正文 */
            String title,   /* 标题 */
            String language,/* 语言 */
            String source,  /* 来源 */
            String time     /* 时间 */) {
        this.id = id;
        this.category = category;
        this.content = content;
        this.title = title;
        this.language = language;
        this.source = source;
        this.time = time;
    }

    public String getId() {
        return id;
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

    public String getContent() {
        return content;
    }
}
