package com.java.jingjia.request;

import com.java.jingjia.NewsItem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * DataManager类用于从url获得各国新冠疫情数据
 */
//TODO：建立完DATA类后来改。
public class DataManager {
    private static DataManager INSTANCE = null;

    private DataManager() {
    }

    public static DataManager getDataManager() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public String getData() {
        String url = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";
        String jsonString = HttpUtil.getServerHttpResponse().getResponse(url);

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject data = jsonObject.getJSONObject("data");

            String _id = data.getString("_id");
            String category = data.getString("category");
            String content = data.getString("content");
            String title = data.getString("title");
            String language = data.getString("lang");
            String time = data.getString("time");
            String type = data.getString("type");
            String source = data.getString("source");
//            oneNews = new NewsItem(_id, category, content, title, language, source, time);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonString;
    }


}
