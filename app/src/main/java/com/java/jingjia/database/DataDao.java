package com.java.jingjia.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Data... data);

    @Update
    void updateData(Data... data);

    @Delete
    void deleteData(Data... data);

    @Delete
    void deleteAllData(Data... data);

    @Query("SELECT * from data_table order by country asc")
    LiveData<List<Data>> getDataAll();

    @Query("SELECT * FROM data_table WHERE country LIKE :country " +
            "AND province LIKE :province " +
            "AND county LIKE :county")
    List<Data> findDataWithPlace(String country, String province, String county);

    @Query("SELECT * FROM data_table WHERE province = '' " +
            "AND county = '' " +
            "order by confirmed DESC")
    List<Data> findAllCountryData();

    @Query("SELECT * FROM data_table " +
            "WHERE country LIKE :country "+
            "AND county = '' " +
            "order by confirmed DESC")
    List<Data> findDataWithCountry(String country);

    @Query("DELETE FROM data_table")
    void deleteAll();

    @Query("SELECT country FROM data_table")
    List<String> getAllCountry();

    @Query("SELECT * FROM data_table " +
            "WHERE province LIKE :pro AND county != '' " +
            "order by confirmed DESC")
    List<Data> getProvinceAllCountyData(String pro);

    @Query("SELECT * FROM data_table " +
            "WHERE country LIKE:s " +
            "AND county = '' " +
            "order by confirmed DESC")
    List<Data> getCountryAllProvincecData(String s);
}
