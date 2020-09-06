package com.java.jingjia.request;

import com.java.jingjia.NewsItem;
import com.java.jingjia.database.Data;
import com.java.jingjia.database.DataRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * DataManager类用于从url获得各国新冠疫情数据
 */
//TODO：建立完DATA类后来改。
public class DataManager {

    private static DataManager INSTANCE = null;
    private DataManager() { }
    public static DataManager getDataManager() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    private DataRepository mRepository;
    void insert(Data data) {
        mRepository.insert(data);
    }

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
}
