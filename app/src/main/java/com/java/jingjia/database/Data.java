package com.java.jingjia.database;

import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Fts4
@Entity
public class Data {
    @PrimaryKey //每个实体必须将至少 1 个字段定义为主键。
    public String Place;/**地区*/

    public Integer confirmed;/**确诊病例*/
    public Integer suspected;/**疑似病例*/
    public Integer cured;/**治愈病例*/
    public Integer dead;/**死亡病例*/
}