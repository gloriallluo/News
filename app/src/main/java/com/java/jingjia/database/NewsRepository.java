package com.java.jingjia.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.java.jingjia.NewsItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewsRepository {

    /**
     * 此类不应该是单例模式?
     * 因为我会用到此类的两个对象
     * 一个对象操作新闻界面的news
     * 一个对象操作用户转发的news
     * */

    private NewsDao mNewsDao;
    private LiveData<List<NewsItem>> mAllNews;

    public NewsRepository(Application application){
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        this.mNewsDao = db.mNewsDao();
        this.mAllNews = mNewsDao.getAllNews();
    }

    /**
     * insert news to database
     * */
    public void insertNews(NewsItem... news){
        InsertNewsTask insertNewsTask = new InsertNewsTask();
        insertNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,news);
    }

    private class InsertNewsTask extends AsyncTask<NewsItem, Void, Void>{
        @Override
        protected Void doInBackground(NewsItem... news){
            mNewsDao.insert(news);
            return null;
        }
    }
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(NewsItem news) {
        NewsRoomDatabase.databaseWriteExecutor.execute(() -> {
            mNewsDao.insert(news);
        });
    }

//    /**
//     *get all news from database
//     */
//    public ArrayList<NewsItem> getAllNews(){
//        try {
//            GetAllNewsTask getAllNewsTask = new GetAllNewsTask();
//            return new ArrayList<>(Arrays.asList(getAllNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0).get()));
//        }catch(ExecutionException e){
//            e.printStackTrace();
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.

    public LiveData<List<NewsItem>> getAllNews() {
        return mAllNews;
    }

//    private class GetAllNewsTask extends AsyncTask<Integer, Void, NewsItem[]>{
//        @Override
//        protected  NewsItem[] doInBackground(Integer... params){
//            return mNewsDao.loadAllNews();
//        }
//    }


    /**
     * get news by email
     */
//    public ArrayList<News> getNewsByEmail(String... email){
//        try{
//            GetNewsByEmailTask getNewsByEmailTask = new GetNewsByEmailTask();
//            return new ArrayList(Arrays.asList(getNewsByEmailTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,email).get()));
//        }catch (ExecutionException e){
//            e.printStackTrace();
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private class GetNewsByEmailTask extends AsyncTask<String, Void, News[]>{
//        @Override
//        protected News[] doInBackground(String... email){
//            return newsDao.getNewsByEmail(email);
//        }
//    }

    /**
     * delete news
     * */
    public void deleteNews(NewsItem... news){
        DeleteNewsTask deleteNewsTask = new DeleteNewsTask();
        deleteNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,news);

    }

    private class DeleteNewsTask extends AsyncTask<NewsItem, Void, Void>{

        @Override
        protected Void doInBackground(NewsItem... news){
            mNewsDao.deleteNews(news);
            return null;
        }
    }
    /****/

    /**
     * delte news by email
     * */
//    public void deleteNewsByEmail(String... email){
//        DeleteNewsByEmailTask deleteNewsByEmailTask = new DeleteNewsByEmailTask();
//        deleteNewsByEmailTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,email);
//    }
//
//    private class DeleteNewsByEmailTask extends AsyncTask<String, Void, Void>{
//        @Override
//        protected Void doInBackground(String... email){
//            newsDao.deleteNewsByEmail(email[0]);
//            return null;
//        }
//    }

    /**
     * clear the table
     */
    public void clearNews(){
        ClearNewsTask clearNewsTask = new ClearNewsTask();
        clearNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,0);
    }

    private class ClearNewsTask extends AsyncTask<Integer, Void, Void>{

        @Override
        protected Void doInBackground(Integer... params){
            mNewsDao.deleteAllNews();
            return null;
        }
    }


    /**
     * get all newsID
     * @return
     */
    public List<String> getAllNewsID(){
        try{
            GetAllNewsIDTask getAllNewsIDTask = new GetAllNewsIDTask();
            return getAllNewsIDTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,0).get();
        }catch(ExecutionException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetAllNewsIDTask extends AsyncTask<Integer, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Integer... params){return newsDao.getAllNewsID();}
    }

    /**
     * Update news
     * */
    public void updateNews(NewsItem...news){
        UpdateNewsTask updateNewsTask = new UpdateNewsTask();
        updateNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,news);
    }
    private class UpdateNewsTask extends AsyncTask<NewsItem, Void, Void>{
        @Override
        protected Void doInBackground(NewsItem...news){
            mNewsDao.update(news);
            return null;
        }
    }

    /**
     *
     * */
    public NewsItem getNewsByNewsID(String...newsID){
        try{
            GetNewsByNewsID getNewsByNewsID = new GetNewsByNewsID();
            List<NewsItem> _news = getNewsByNewsID.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,newsID).get();
            if(_news==null || _news.size() == 0){
                return null;
            }
            return _news.get(0);
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
    private class GetNewsByNewsID extends AsyncTask<String, Void, List<NewsItem>> {
        @Override
        protected List<NewsItem> doInBackground(String...newsID){
            return mNewsDao.findNewsWithId(newsID);
        }
    }


}