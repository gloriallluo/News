package com.java.jingjia.database;

import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Fts4
@Entity
public class News {
    @PrimaryKey //每个实体必须将至少 1 个字段定义为主键。
    public String id;/**用于请求新闻正文的id */

    public String category;/**分类(直接从网址读为空)*/
    public String title;/**标题*/
    public String language;/**语言*/
    public String source = "xia bb news";/**来源*/
    public String time = "2020-09-04";/**时间*/
    public String type;/**种类news或paper*/

    @Ignore
    public String content;
}