package com.java.jingjia.database;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataRepository {

    /**
     * 此类应该是单例模式?
     * */

    private final DataDao dataDao;

    DataRepository(DataDao dataDao){
        this.dataDao = dataDao;
    }

    /**
     * insert data to database
     * */
    public void insertData(Data... data){
        DataRepository.InsertDataTask insertDataTask = new DataRepository.InsertDataTask();
        insertDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,data);
    }

    private class InsertDataTask extends AsyncTask<Data, Void, Void>{
        @Override
        protected Void doInBackground(Data... data){
            dataDao.insertData(data);
            return null;
        }
    }

    /**
     *get all news from database
     */
    public ArrayList<News> getAllData(){
        try {
            DataRepository.GetAllDataTask getAllDataTask = new DataRepository.GetAllDataTask();
            return new ArrayList<>(Arrays.asList(getAllDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0).get()));
        }catch(ExecutionException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetAllDataTask extends AsyncTask<Integer, Void, News[]>{
        @Override
        protected  News[] doInBackground(Integer... params){
            return dataDao.loadAllData();
        }
    }


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
    public void deleteData(Data... data){
        DataRepository.DeleteDataTask deleteNewsTask = new DataRepository.DeleteDataTask();
        deleteNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,data);

    }

    private class DeleteDataTask extends AsyncTask<Data, Void, Void>{
        @Override
        protected Void doInBackground(Data... data){
            dataDao.deleteData(data);
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
    public void clearData(){
        DataRepository.ClearNewsTask clearNewsTask = new DataRepository.ClearNewsTask();
        clearNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,0);
    }

    private class ClearNewsTask extends AsyncTask<Integer, Void, Void>{

        @Override
        protected Void doInBackground(Integer... params){
            dataDao.deleteAllData();
            return null;
        }
    }


    /**
     * get all newsID
     * @return
     */
    public List<String> getAllNewsID(){
        try{
            DataRepository.GetAllNewsIDTask getAllNewsIDTask = new DataRepository.GetAllNewsIDTask();
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
        protected List<String> doInBackground(Integer... params){return dataDao.getAllPlace();}
    }

    /**
     * Update news
     * */
    public void updateNews(Data...data){
        DataRepository.UpdateNewsTask updateNewsTask = new DataRepository.UpdateNewsTask();
        updateNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,data);
    }

    private class UpdateNewsTask extends AsyncTask<Data, Void, Void>{
        @Override
        protected Void doInBackground(Data...data){
            dataDao.updateData(data);
            return null;
        }
    }

    /**
     *
     * */
    public Data getDataByNewsID(String...newsID){
        try{
            DataRepository.GetDataByPlaceTask getDataByPlace = new DataRepository.GetDataByPlaceTask();
            List<Data> _data = getDataByPlace.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,newsID).get();
            if(_data==null || _data.size() == 0){
                return null;
            }
            return _data.get(0);
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    private class GetDataByPlaceTask extends AsyncTask<String, Void, List<Data>> {
        @Override
        protected List<Data> doInBackground(String...place){
            return dataDao.findDataWithPlace(place);
        }
    }
}
