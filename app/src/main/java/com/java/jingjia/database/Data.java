package com.java.jingjia.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity(tableName = "data_table",
        primaryKeys = {"country", "province", "county"},
        indices = {@Index(value = {"country", "province", "county"}, unique = true)})
public class Data {

    @NonNull
    private String country;/**国家*/
    @NonNull
    private String province;/**国家*/
    @NonNull
    private String county;/**国家*/

    private Integer confirmed;/**确诊病例*/
    private Integer suspected;/**疑似病例*/
    private Integer cured;/**治愈病例*/
    private Integer dead;/**死亡病例*/

    public Data(String country, String province, String county, Integer confirmed, Integer suspected, Integer cured, Integer dead){

        this.country = country;
        this.province = province;
        this.county = county;

        this.confirmed = confirmed;
        this.suspected = suspected;
        this.cured = cured;
        this.dead = dead;
    }

    public Data(String place, Integer confirmed, Integer suspected, Integer cured, Integer dead){
        String[] p = place.split("|");
        if(p.length == 1){
            this.country = p[0];
            this.province = "";
            this.county = "";
        }else if(p.length == 2){
            this.country = p[0];
            this.province = p[1];
            this.county = "";
        }else{
            this.country = p[0];
            this.province = p[1];
            this.county = p[2];
        }
        this.confirmed = confirmed;
        this.suspected = suspected;
        this.cured = cured;
        this.dead = dead;
    }

    public String getCountry() {
        return country;
    }
    public String getProvince() {
        return province;
    }
    public String getCounty() {
        return county;
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