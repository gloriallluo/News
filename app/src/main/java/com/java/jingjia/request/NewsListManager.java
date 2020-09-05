package com.java.jingjia.request;

import android.icu.text.ListFormatter;
import android.util.Log;

import com.java.jingjia.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsListManager {

    private String TAG = "NewsListManager";
    private static NewsListManager INSTANCE = null;

    private NewsListManager() {
    }

    public static NewsListManager getNewsListManager() {
        if (INSTANCE == null) {
            INSTANCE = new NewsListManager();
        }
        return INSTANCE;
    }

    /**
     * 获得指定type的最新10条消息的json。参数type可以为"all"或"news"或"paper"，默认为all，
     * 如果是paper，接口返回的内容并没有按照时间顺序排列，之后再做处理。
     */
    public String getLatestJson(String type) {
        String url = "https://covid-dashboard.aminer.cn/api/events/list?type=all&page=1&size=10";
        switch (type) {
            case "all":
                url = "https://covid-dashboard.aminer.cn/api/events/list?type=all&page=1&size=10";
                break;
            case "news":
                url = "https://covid-dashboard.aminer.cn/api/events/list?type=news&page=1&size=10";
                break;
            case "paper":
                url = "https://covid-dashboard.aminer.cn/api/events/list?type=paper&page=1&size=10";
        }
        return HttpUtil.getServerHttpResponse().getResponse(url);
    }

    /**
     * getNewsList用于返回最新的十个对应初始化好的NewItem类的ArrayList
     */
    public ArrayList<NewsItem> getNewsList(String type) {
        String json = getLatestJson(type);
        Log.d(TAG, "getNewsList: " + json);
        ArrayList<NewsItem> newsList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject oneJsonObject = data.getJSONObject(i);
                NewsItem oneNews = NewsContentManager.getNewsContentManager().
                        getNewsItemFromJsonObject(oneJsonObject);
                newsList.add(oneNews);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}