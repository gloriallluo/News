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

    @Query("SELECT * from news_table ORDER BY time ASC")
    List<NewsItem> getAllNews();

    @Query("SELECT id FROM news_table")
    List<String> getAllNewsID();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NewsItem... news);

    @Delete
    void deleteAllNews(NewsItem... news);

    @Query("SELECT * FROM news_table WHERE id LIKE :search")
    List<NewsItem> findNewsWithId(String[] search);

    @Query("SELECT * FROM news_table WHERE visited LIKE :search")
    List<NewsItem> getAllNewsByVisitedOrNot(Boolean search);

    @Query("UPDATE news_table SET visited= :v WHERE id = :i")
    void setVisitedById(String i, boolean v);

    @Delete
    void deleteNews(NewsItem[] news);

    @Query("SELECT * FROM news_table WHERE type LIKE :type LIMIT 10")
    List<NewsItem> getLastInsertNews(String type);

    @Query("SELECT * FROM news_table WHERE id LIKE :id")
    NewsItem getNewsByID(String id);
}
