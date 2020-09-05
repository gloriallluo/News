package com.java.jingjia.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(News... news);

    @Update
    void updateNews(News... news);

    @Delete
    void deleteNews(News... news);

    @Delete
    void deleteAllNews(News... news);

    @Query("SELECT * FROM news")
    News[] loadAllNews();

    @Query("SELECT id FROM news")
    List<String> getAllNewsID();

    @Query("SELECT * FROM news WHERE id LIKE :search")
    List<News> findNewsWithId(String[] search);

//    @Query("SELECT * FROM news WHERE age > :minAge")
//    public News[] loadAllUsersOlderThan(int minAge);

//    @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
//    public User[] loadAllUsersBetweenAges(int minAge, int maxAge);
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :search " +
//            "OR last_name LIKE :search")
//    public List<User> findUserWithName(String search);
}
