package com.java.jingjia;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName ="news_table",
        indices = {@Index(value = {"id"}, unique = true),
                @Index("time")})
public class NewsItem implements Serializable {

    @Ignore
    private final String TAG = "NewsItem";
    @Ignore
    LinearLayout mLayout;

    Boolean visitedStatus;

    @PrimaryKey //每个实体必须将至少 1 个字段定义为主键。
    @NonNull
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
    String source;
    /**
     * 来源
     */
    String time;
    /**
     * 时间
     */
    String type;
    /**
     * 种类news或paper
     */

    @Ignore
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
            String time,     /* 时间 */
            String type) {
        this.id = id;
        this.category = category;
        this.content = content;
        this.title = title;
        this.language = language;
        this.source = source;
        this.time = time;
        this.type = type;
        this.visitedStatus = false;
    }

    public String getId(){
        return this.id;
    }
    public String getCategory(){
        return this.category;
    }
    public String getTitle(){
        return this.title;
    }
    public String getLanguage(){
        return this.language;
    }
    public String getSource(){
        return this.source;
    }
    public String getTime(){
        return this.time;
    }
    public String getType(){
        return this.type;
    }
    public String getContent() {return this.content;}
    public Boolean getVisited() {return this.visitedStatus;}

    public void setVisited() {this.visitedStatus = true;}
}