package com.java.jingjia.request;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.java.jingjia.NewsItem;
import com.java.jingjia.database.Data;
import com.java.jingjia.database.DataRepository;
import com.java.jingjia.database.NewsRepository;

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
                Data newData = new Data(key,
                        latestData.getInt(0),
                        latestData.getInt(1),
                        latestData.getInt(2),
                        latestData.getInt(3));
                insert(newData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public List<Data> getChinaAccumulatedata(){
//        return this.mRepository.getChinaAccumulatedata();
//    }
}
