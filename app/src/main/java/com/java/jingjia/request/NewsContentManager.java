package com.java.jingjia.request;

import com.java.jingjia.NewsItem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * NewsContentManager类用于从url获得正文数据
 */
public class NewsContentManager {
    private static NewsContentManager INSTANCE = null;
    private NewsContentManager() {
    }
    public static NewsContentManager getNewsContentManager() {
        if (INSTANCE == null) {
            INSTANCE = new NewsContentManager();
        }
        return INSTANCE;
    }

    public String getContent(String id) {
        String url = "https://covid-dashboard-api.aminer.cn/event/" + id;
        return HttpUtil.getServerHttpResponse().getResponse(url);
    }

    /**
     * getNewsItem用于提供新闻id返回对应初始化好的NewItem类
     */
    public NewsItem getNewsItem(String id) {
        String json = getContent(id);
        NewsItem oneNews = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("data");
            oneNews = getNewsItemFromJsonObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oneNews;
    }

    /**
     * 从JSONObject构造一个NewsItem
     */
    public NewsItem getNewsItemFromJsonObject(JSONObject data) {
        NewsItem oneNews = null;
        try {
            String _id = data.getString("_id");
            String category = data.getString("category");
            String content = data.getString("content");
            String title = data.getString("title");
            String language = data.getString("lang");
            String time = data.getString("time");
            String type = data.getString("type");
            String source = data.getString("source");
            oneNews = new NewsItem(_id, category, content, title, language, source, time, type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oneNews;
    }

}
