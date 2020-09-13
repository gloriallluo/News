package com.java.jingjia.database;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.java.jingjia.NewsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewsRepository {

    private static final String TAG = "NewsRepository";
    /**
     * 此类不应该是单例模式?
     * 因为我会用到此类的两个对象
     * 一个对象操作新闻界面的news
     * 一个对象操作用户转发的news
     * */

    private NewsDao mNewsDao;

    public NewsRepository(Application application) {
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        this.mNewsDao = db.mNewsDao();
    }

    /**
     * insert news to database
     * */
    public void insert(NewsItem... news) {
        InsertNewsTask insertNewsTask = new InsertNewsTask();
        insertNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,news);
    }

    private class InsertNewsTask extends AsyncTask<NewsItem, Void, Void> {
        @Override
        protected Void doInBackground(NewsItem... news){
            mNewsDao.insert(news);
            return null;
        }
    }

    public List<NewsItem> getAllNews() {
        try {
            GetAllNewsTask getAllNewsTask = new GetAllNewsTask();
            return new ArrayList<>(getAllNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class GetAllNewsTask extends AsyncTask<Integer, Void, List<NewsItem>> {
        @Override
        protected  List<NewsItem> doInBackground(Integer... params){
            return mNewsDao.getAllNews();
        }
    }

    public List<NewsItem> getAllNewsByVisitedOrNot(boolean visited) {
        GetAllNewsByVisitedOrNotTask getAllNewsByVisitedOrNotTask = new GetAllNewsByVisitedOrNotTask();
        try {
            return new ArrayList<>(getAllNewsByVisitedOrNotTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, visited).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class GetAllNewsByVisitedOrNotTask extends AsyncTask<Boolean, Void, List<NewsItem>> {
        @SuppressLint("LongLogTag")
        @Override
        protected  List<NewsItem> doInBackground(Boolean... visited){
            List<NewsItem> v = mNewsDao.getAllNewsByVisitedOrNot(visited[0]);
            return v;
        }
    }

    public void setVisitedById(String id) {
        SetVisitedByIdTask setVisitedByIdTask = new SetVisitedByIdTask();
        setVisitedByIdTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id);
        Log.i(TAG, "setVisitedById: ID ：" + id);
    }

    private class SetVisitedByIdTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... id){
            mNewsDao.setVisitedById(id[0], true);
            Log.i(TAG, "setVisitedById: " + mNewsDao.getNewsByID(id[0]).getVisited());
            return null;
        }
    }

    public List<NewsItem> getLastInsertNews(String type) {
        GetLastInsertNewsTask getLastInsertNewsTask = new GetLastInsertNewsTask();
        try {
            return new ArrayList<>(getLastInsertNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, type).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private class GetLastInsertNewsTask extends AsyncTask<String, Void, List<NewsItem>> {
        @Override
        protected List<NewsItem> doInBackground(String... type){
            return mNewsDao.getLastInsertNews(type[0]);
        }
    }
}