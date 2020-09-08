package com.java.jingjia.request;

import android.app.Application;
import android.graphics.pdf.PdfDocument;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.java.jingjia.NewsItem;
import com.java.jingjia.database.Data;
import com.java.jingjia.database.NewsRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsListManager {

    private String TAG = "NewsListManager";
    private NewsRepository mRepository;
    private Integer pageDown;
    // Using LiveData and caching what getAllNews returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
//    private List<NewsItem> mAllNews;

    private static NewsListManager INSTANCE = null;
    private NewsListManager(Application application) {
        this.mRepository = new NewsRepository(application);
//        this.mAllNews =  mRepository.getAllNews();
        this.pageDown = 1;
    }
    public static NewsListManager getNewsListManager(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new NewsListManager(application);
        }
        return INSTANCE;
    }

    void insert(NewsItem news) {
        mRepository.insert(news);
    }

    /**
     * 获得指定type的最新10条消息的json字符串。参数type可以为"all"或"news"或"paper"，默认为all，
     * 如果是paper，接口返回的内容并没有按照时间顺序排列，之后再做处理。
     */
    private String getLatestJson(String type) {
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
                break;
        }
        return HttpUtil.getServerHttpResponse().getResponse(url);
    }

    private String getJson(String type, Integer page){
        String url = "https://covid-dashboard.aminer.cn/api/events/list?type="+type
                +"&page="+page+"&size=10";
        return HttpUtil.getServerHttpResponse().getResponse(url);
    }

    /**
     * getNewsList用于返回最新的十个对应初始化好的NewItem类的ArrayList
     */
    private ArrayList<NewsItem> getNewsList(String type) {
        String json = getLatestJson(type);
        ArrayList<NewsItem> newsList = new ArrayList<>();
        if (json == null) return newsList;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject oneJsonObject = data.getJSONObject(i);
                NewsItem oneNews = NewsContentManager.getNewsContentManager().
                        getNewsItemFromJsonObject(oneJsonObject);
                newsList.add(oneNews);
                insert(oneNews);
            }
        } catch (JSONException e) {
            Log.e(TAG, "getNewsList: JSONException e");
        }
        return newsList;
    }

    /**
     * 如果参数id = ""就是第一次刷新，返回最新的十条NewItem类的ArrayList。
     * 否则返回比id新的NewItem类的ArrayList。
     */
    public ArrayList<NewsItem> getLatestNewsList(String type, String id) {
//        Log.e(TAG, "getLatestNewsList: "+ type + " "+ id);
        if(id == ""){
            return getNewsList(type);
        }
        ArrayList<NewsItem> newsList = new ArrayList<>();
        while(true){
            String json = getLatestJson(type);
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject oneJsonObject = data.getJSONObject(i);
                    String thisId = oneJsonObject.getString("_id");
                    if(!thisId.equals(id)){ //还没有到id那一条
                        NewsItem oneNews = NewsContentManager.getNewsContentManager().
                                getNewsItemFromJsonObject(oneJsonObject);
                        if(oneNews == null){
                            Log.e(TAG, "getLatestNewsList: oneNews == null");
                        }
                        newsList.add(oneNews);
                        insert(oneNews);
                    }else{
                        return newsList;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * getMoreNewsList(String type, String id)
     * 返回更多的比id旧的[10,20]个NewItem类的ArrayList。
     */
    public ArrayList<NewsItem> getMoreNewsList(String type, String id) {
        ArrayList<NewsItem> newsList = new ArrayList<>();
        boolean find = false;
        while(!find){
            String json = getJson(type, pageDown);
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject oneJsonObject = data.getJSONObject(i);
                    String thisId = oneJsonObject.getString("_id");
                    if(!find){
                        if(thisId.equals(id)){ //到id那一条
                            find = true;
                        }
                    }else{
                        NewsItem oneNews = NewsContentManager.getNewsContentManager().
                                getNewsItemFromJsonObject(oneJsonObject);
                        newsList.add(oneNews);
                        insert(oneNews);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pageDown++;
        }
        //再读10条
        String json = getJson(type, pageDown);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject oneJsonObject = data.getJSONObject(i);
                NewsItem oneNews = NewsContentManager.getNewsContentManager().
                        getNewsItemFromJsonObject(oneJsonObject);
                newsList.add(oneNews);
                insert(oneNews);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    /**
     * getHistoryNewsList
     * 返回所有数据库中的News（在首页出现过的News）
     */
    public List<NewsItem> getHistoryNewsList(String type) {
        return this.mRepository.getAllNews();
    }

    /**
     * getVisitedNewsList
     * 返回所有数据库中被看过的的News
     */
    public List<NewsItem> getVisitedNewsList(String type) {
        return this.mRepository.getAllNewsByVisitedOrNot(true);
    }

    /**
     * SetVisitedNews
     * 将id对应的news设置为已读
     */
    public void setVisitedNews(String id){
        this.mRepository.setVisitedById(id);
    }

}