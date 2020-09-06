package com.java.jingjia.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_table",
        indices = {@Index(value = {"place"}, unique = true)})
public class Data {
    @PrimaryKey //每个实体必须将至少 1 个字段定义为主键。
    @NonNull    //主键不能为空
    @ColumnInfo(name= "place")
    private String place;/**地区*/

    private Integer confirmed;/**确诊病例*/
    private Integer suspected;/**疑似病例*/
    private Integer cured;/**治愈病例*/
    private Integer dead;/**死亡病例*/

    public Data(String place, Integer confirmed, Integer suspected, Integer cured, Integer dead){
        this.place = place;
        this.confirmed = confirmed;
        this.suspected = suspected;
        this.cured = cured;
        this.dead = dead;
    }

    public String getPlace(){
        return this.place;
    }
    public Integer getConfirmed(){
        return this.confirmed;
    }
    public Integer getSuspected(){
        return this.suspected;
    }
    public Integer getCured(){
        return this.cured;
    }
    public Integer getDead(){
        return this.dead;
    }
}