package com.java.jingjia.database;

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
    void insertData(Data... data);

    @Update
    void updateData(Data... data);

    @Delete
    void deleteData(Data... data);

    @Delete
    void deleteAllData(Data... data);

    @Query("SELECT * FROM data")
    News[] loadAllData();

    @Query("SELECT place FROM data")
    List<String> getAllPlace();

    @Query("SELECT * FROM data WHERE place LIKE :search")
    List<Data> findDataWithPlace(String[] search);

//    @Query("SELECT * FROM news WHERE age > :minAge")
//    public News[] loadAllUsersOlderThan(int minAge);

//    @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
//    public User[] loadAllUsersBetweenAges(int minAge, int maxAge);
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :search " +
//            "OR last_name LIKE :search")
//    public List<User> findUserWithName(String search);
}
