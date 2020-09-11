package com.java.jingjia.database;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.java.jingjia.NewsItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataRepository {

    private static final String TAG = "DataRepository";
    private final DataDao mDataDao;
    private LiveData<List<Data>> mAllData;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public DataRepository(Application application){
        DataRoomDatabase db = DataRoomDatabase.getDatabase(application);
        this.mDataDao = db.dataDao();
        this.mAllData = mDataDao.getDataAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Data>> getAllData() {
        return mAllData;
    }


    /**
     * insert data to database
     * */
    public void insert(Data data) {
        InsertDataTask insertDataTask = new InsertDataTask(mDataDao);
//        insertDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
        insertDataTask.execute(data);
    }

    private static class InsertDataTask extends AsyncTask<Data, Void, Void> {
        private DataDao mAsyncDao;
        InsertDataTask(DataDao dataDao){
            this.mAsyncDao = dataDao;
        }
        @Override
        protected Void doInBackground(Data... data) {
            mAsyncDao.insert(data[0]);
            return null;
        }
    }

    public List<Data> getProvinceAllCountyAccumulatedData(String province) {
        DataRepository.getProvinceAllCountyAccumulatedDataTask task =
                new DataRepository.getProvinceAllCountyAccumulatedDataTask();
        List<Data> AllCounty = null;
        try {
            AllCounty = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, province).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Log.i(TAG, "getProvinceAllCountyAccumulatedData: return List<Data> size: " + AllCounty.size());
        return AllCounty;
    }

    private class getProvinceAllCountyAccumulatedDataTask extends AsyncTask<String, Void, List<Data>> {
        private static final String TAG = "getProvinceAllCountyAccumulatedDataTask";
        @SuppressLint("LongLogTag")
        @Override
        protected List<Data> doInBackground(String... province) {
            //            Log.i(TAG, "doInBackground: returnData size " + returnData.size());
            return mDataDao.getProvinceAllCountyData(province[0]);
        }
    }

    public List<Data> getCountryAllProvinceAccumulatedData(String country) {
        DataRepository.getCountryAllProvinceAccumulatedDataTask task =
                new DataRepository.getCountryAllProvinceAccumulatedDataTask();
        List<Data> AllProvince = null;
        try {
            AllProvince = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, country).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Log.i(TAG, "getCountryAllProvinceAccumulatedData: return List<Data> size: " + AllProvince.size());
        return AllProvince;
    }
    public class getCountryAllProvinceAccumulatedDataTask extends AsyncTask<String, Void, List<Data>> {
        private static final String TAG = "getCountryAllProvinceAccumulatedDataTask";
        @SuppressLint("LongLogTag")
        @Override
        protected List<Data> doInBackground(String... country) {
            //            Log.i(TAG, "doInBackground: returnData size " + returnData.size());
            return mDataDao.getCountryAllProvincecData(country[0]);
        }
    }

    public List<Data> getChinaAllProvinceAccumulatedData(){
//        Log.i(TAG, "getChinaAllProvinceAccumulatedData: ");
        try {
            DataRepository.getChinaAllProvinceAccumulatedDataTask task =
                    new DataRepository.getChinaAllProvinceAccumulatedDataTask();
            //            Log.i(TAG, "getChinaAllProvinceAccumulatedData: return List<Data> size: " + AllPro.size());
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0).get();
        }catch(ExecutionException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    private class getChinaAllProvinceAccumulatedDataTask extends AsyncTask<Integer, Void, List<Data>>{
            private static final String TAG = "getChinaAllProvinceAccumulatedDataTask";

            @SuppressLint("LongLogTag")
            @Override
        protected  List<Data> doInBackground(Integer... params){
                //            Log.i(TAG, "doInBackground: returnData size " +returnData.size());
            return mDataDao.findDataWithCountry("China");
        }
    }

//    public List<Data> getChinaAllProvinceAccumulatedData() {
//        NewsRoomDatabase.databaseWriteExecutor.execute(() -> {
//            return mDataDao.findDataWithCountry("China");
//        });
//    }
    public List<Data> getGlobalAllCountryAccumulatedData(){
        try {
            DataRepository.getGlobalAllCountryAccumulatedDataTask task =
                    new DataRepository.getGlobalAllCountryAccumulatedDataTask();
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0).get();
        }catch(ExecutionException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
    private class getGlobalAllCountryAccumulatedDataTask extends AsyncTask<Integer, Void, List<Data>>{
        @Override
        protected  List<Data> doInBackground(Integer... params){
            return mDataDao.findAllCountryData();
        }

    }
//    public List<Data> getGlobleAllCountryAccumulatedData() {
//        List<Data> allCountry = new ArrayList<>();
//        List<String> allCountryStr = mDataDao.getAllCountry();
//        for(String country : allCountryStr){
//            allCountry.addAll(this.mDataDao.findDataWithPlace(country,"",""));
//        }
//        return allCountry;
//    }

    //    /**
//     *get all news from database
//     */
//    public ArrayList<News> getAllData(){
//        try {
//            DataRepository.GetAllDataTask getAllDataTask = new DataRepository.GetAllDataTask();
//            return new ArrayList<>(Arrays.asList(getAllDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0).get()));
//        }catch(ExecutionException e){
//            e.printStackTrace();
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        return null;
//    }

//    private class GetAllDataTask extends AsyncTask<Integer, Void, News[]>{
//        @Override
//        protected  Data[] doInBackground(Integer... params){
//            return mDataDao.loadAllData();
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
    public void deleteData(Data... data){
        DataRepository.DeleteDataTask deleteNewsTask = new DataRepository.DeleteDataTask();
        deleteNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,data);
    }
    private class DeleteDataTask extends AsyncTask<Data, Void, Void>{
        @Override
        protected Void doInBackground(Data... data){
            mDataDao.deleteData(data);
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
//    public void clearData(){
//        DataRepository.ClearNewsTask clearNewsTask = new DataRepository.ClearNewsTask();
//        clearNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,0);
//    }
//
//    private class ClearNewsTask extends AsyncTask<Integer, Void, Void>{
//
//        @Override
//        protected Void doInBackground(Integer... params){
//            mDataDao.deleteAllData();
//            return null;
//        }
//    }


    /**
     * get all newsID
     * @return
     */
//    public List<String> getAllNewsID(){
//        try{
//            DataRepository.GetAllNewsIDTask getAllNewsIDTask = new DataRepository.GetAllNewsIDTask();
//            return getAllNewsIDTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,0).get();
//        }catch(ExecutionException e){
//            e.printStackTrace();
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private class GetAllNewsIDTask extends AsyncTask<Integer, Void, List<String>> {
//        @Override
//        protected List<String> doInBackground(Integer... params){return mDataDao.getAllPlace();}
//    }

    /**
     * Update news
     * */
//    public void updateNews(Data...data){
//        DataRepository.UpdateNewsTask updateNewsTask = new DataRepository.UpdateNewsTask();
//        updateNewsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,data);
//    }
//
//    private class UpdateNewsTask extends AsyncTask<Data, Void, Void>{
//        @Override
//        protected Void doInBackground(Data...data){
//            mDataDao.updateData(data);
//            return null;
//        }
//    }

    /**
     *
     * */
//    public Data getDataByNewsID(String...newsID){
//        try{
//            DataRepository.GetDataByPlaceTask getDataByPlace = new DataRepository.GetDataByPlaceTask();
//            List<Data> _data = getDataByPlace.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,newsID).get();
//            if(_data==null || _data.size() == 0){
//                return null;
//            }
//            return _data.get(0);
//        }catch (ExecutionException e){
//            e.printStackTrace();
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private class GetDataByPlaceTask extends AsyncTask<String, Void, List<Data>> {
//        @Override
//        protected List<Data> doInBackground(String...place){
//            return mDataDao.findDataWithPlace(place);
//        }
//    }
}
