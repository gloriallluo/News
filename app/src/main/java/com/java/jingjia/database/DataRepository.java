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
        return AllProvince;
    }
    public class getCountryAllProvinceAccumulatedDataTask extends AsyncTask<String, Void, List<Data>> {
        private static final String TAG = "getCountryAllProvinceAccumulatedDataTask";
        @SuppressLint("LongLogTag")
        @Override
        protected List<Data> doInBackground(String... country) {
            return mDataDao.getCountryAllProvincecData(country[0]);
        }
    }

    public List<Data> getChinaAllProvinceAccumulatedData(){
        try {
            DataRepository.getChinaAllProvinceAccumulatedDataTask task =
                    new DataRepository.getChinaAllProvinceAccumulatedDataTask();
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class getChinaAllProvinceAccumulatedDataTask extends AsyncTask<Integer, Void, List<Data>>{
            private static final String TAG = "getChinaAllProvinceAccumulatedDataTask";

            @SuppressLint("LongLogTag")
            @Override
        protected  List<Data> doInBackground(Integer... params){
            return mDataDao.findDataWithCountry("China");
        }
    }

    public List<Data> getGlobalAllCountryAccumulatedData(){
        try {
            DataRepository.getGlobalAllCountryAccumulatedDataTask task =
                    new DataRepository.getGlobalAllCountryAccumulatedDataTask();
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
}
