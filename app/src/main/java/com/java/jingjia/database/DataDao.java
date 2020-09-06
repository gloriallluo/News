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

//    @Query("SELECT * FROM data_table")
//    Data[] loadAllData();

    @Query("SELECT * from data_table order by country asc")
    LiveData<List<Data>> getDataAll();

//    @Query("SELECT country,province,county FROM data_table")
//    List<String> getAllPlace();

    @Query("SELECT * FROM data_table WHERE country LIKE :country " +
            "AND province LIKE :province " +
            "AND county LIKE :county")
    List<Data> findDataWithPlace(String country, String province, String county);

    @Query("DELETE FROM data_table")
    void deleteAll();

//    @Query("SELECT * FROM news WHERE age > :minAge")
//    public News[] loadAllUsersOlderThan(int minAge);

//    @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
//    public User[] loadAllUsersBetweenAges(int minAge, int maxAge);
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :search " +
//            "OR last_name LIKE :search")
//    public List<User> findUserWithName(String search);
}
