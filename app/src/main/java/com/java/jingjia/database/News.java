package com.java.jingjia.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Fts4
@Entity(tableName ="news_table")
public class News {
    @PrimaryKey //每个实体必须将至少 1 个字段定义为主键。
    @NonNull
    private String id;/**用于请求新闻正文的id */

    private String category;/**分类(直接从网址读为空)*/
    private String title;/**标题*/
    private String language;/**语言*/
    private String source = "xia bb news";/**来源*/
    private String time = "2020-09-04";/**时间*/
    private String type;/**种类news或paper*/

    @Ignore
    public String content;

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
}