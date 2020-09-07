package com.java.jingjia.request;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.java.jingjia.database.Data;
import com.java.jingjia.database.DataRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * DataManager类
 * 1. 用于从url获得新冠疫情数据
 * 2. 将需要的数据保存至Data数据库
 */
public class DataManager {

    private static final String TAG = "DataManager";
    private DataRepository mRepository;
    // Using LiveData and caching what getAllNews returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Data>> mAllData;

    private static DataManager INSTANCE = null;
    private DataManager(Application application) {
        mRepository = new DataRepository(application);
        mAllData = mRepository.getAllData();
    }
    /** 获得DataManager单例，需要传入参数Application application */
    public static DataManager getDataManager(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new DataManager(application);
        }
        return INSTANCE;
    }

    /** 插入一条Data */
    void insert(Data data) {
        mRepository.insert(data);
    }

    /**
     * 从url获得Data并插入到数据库
     */
    public void getData() {
        String url = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";
        String jsonString = HttpUtil.getServerHttpResponse().getResponse(url);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Iterator<String> it = jsonObject.keys();
            while(it.hasNext()){// 获得key
                String key = it.next();
                JSONObject value = jsonObject.getJSONObject(key);
                String begin = value.getString("begin");
                JSONArray data = value.getJSONArray("data");
                JSONArray latestData = data.getJSONArray(data.length()-1);
//                Log.i(TAG, "getData: latestData :" + latestData.toString());
//                Log.i(TAG, "getData: latestData(0) :" + latestData.get(0)
//                        + " latestData(1) :" + latestData.get(1)
//                        + " latestData(2) :" + latestData.get(2)
//                        + " latestData(3) :" + latestData.get(3))
                ;
                Data newData = new Data(key,
                        (latestData.get(0).toString() == "null") ? 0: latestData.getInt(0),
                        (latestData.get(1).toString() == "null") ? 0: latestData.getInt(1),
                        (latestData.get(2).toString() == "null") ? 0: latestData.getInt(2),
                        (latestData.get(3).toString() == "null") ? 0: latestData.getInt(3));
                insert(newData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Data> getChinaAllProvinceAccumulatedData(){
        getData();
        return this.mRepository.getChinaAllProvinceAccumulatedData();
    }
    public List<Data> getGlobalAllCountryAccumulatedData(){
        getData();
        return this.mRepository.getGlobalAllCountryAccumulatedData();
    }

    public List<Data> getProvinceAllCountyAccumulatedData(String province) {
        return this.mRepository.getProvinceAllCountyAccumulatedData(province);
    }

    public List<Data> getCountryAllProvinceAccumulatedData(String province) {
        return this.mRepository.getCountryAllProvinceAccumulatedData(province);
    }
}
