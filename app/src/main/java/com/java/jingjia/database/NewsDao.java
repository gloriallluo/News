package com.java.jingjia.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.java.jingjia.NewsItem;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsItem... news);

    @Update
    void update(NewsItem... news);

    @Delete
    void deleteNews(NewsItem... news);

    @Delete
    void deleteAllNews(NewsItem... news);

    @Query("SELECT * FROM news_table")
    NewsItem[] loadAllNews();

    @Query("SELECT * from news_table ORDER BY time ASC")
    List<NewsItem> getAllNews();

    @Query("SELECT id FROM news_table")
    List<String> getAllNewsID();

    @Query("SELECT * FROM news_table WHERE id LIKE :search")
    List<NewsItem> findNewsWithId(String[] search);

    @Query("SELECT * FROM news_table WHERE visited LIKE :search")
    List<NewsItem> getAllNewsByVisitedOrNot(Boolean search);

    @Query("UPDATE news_table SET visited= :v WHERE id = :i")
    public abstract int setVisitedById(String i, boolean v);

//    @Query("SELECT * FROM news WHERE age > :minAge")
//    public News[] loadAllUsersOlderThan(int minAge);

//    @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
//    public User[] loadAllUsersBetweenAges(int minAge, int maxAge);
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :search " +
//            "OR last_name LIKE :search")
//    public List<User> findUserWithName(String search);
}
