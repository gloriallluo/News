package com.java.jingjia.request;


import com.java.jingjia.Entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * DataManager类
 * 1. 用于从url获得查询信息
 */
public class KnowledgeGraphManager {

    //单例模式
    private static KnowledgeGraphManager INSTANCE = null;

    private KnowledgeGraphManager() { }
    /** 获得DataManager单例 */
    public static KnowledgeGraphManager getKnowledgeGraphManager() {
        if (INSTANCE == null) {
            INSTANCE = new KnowledgeGraphManager();
        }
        return INSTANCE;
    }

    /**
     * 从url查询某个词
     */
    public ArrayList<Entity> query(String searchName) throws JSONException {
        String url = "https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity=" + searchName;
        String jsonString = HttpUtil.getServerHttpResponse().getResponse(url);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray data = jsonObject.getJSONArray("data");
        Integer hotNum = data.length();

        ArrayList<Entity> retEntity = new ArrayList<>();
        for(int i = 0; i < hotNum; i++){
            JSONObject oneHot= data.getJSONObject(i);
            JSONObject abInfo = oneHot.getJSONObject("abstractInfo");
            String briefInfo = abInfo.getString("enwiki") + abInfo.getString("baidu") + abInfo.getString("zhwiki");
            JSONObject covidInfo = abInfo.getJSONObject("COVID");
            JSONObject pros = covidInfo.getJSONObject("properties");
            Map<String, String> properties = new HashMap<>();
            Iterator<String> it = pros.keys();
            while(it.hasNext()){
                String key = it.next();
                String value = pros.getString("key");
                properties.put(key, value);
            }
            List<Entity.Relation> relas = new ArrayList<>();
            JSONArray relats = covidInfo.getJSONArray("relations");
            Integer relaNum = relats.length();
            for(int j = 0; j < relaNum; j++){
                JSONObject oneRela = relats.getJSONObject(i);
                Entity.Relation oneRelation = new Entity.Relation(
                        oneRela.getString("relation"),
                        oneRela.getString("url"),
                        oneRela.getString("label"),
                        oneRela.getBoolean("forward"));
                relas.add(oneRelation);
            }
            String image = oneHot.get("img").toString() == "null" ? "" :oneHot.getString("img");

            Entity newOne = new Entity(
                    oneHot.getString("label"),
                    oneHot.getString("url"),
                    oneHot.getString("abstractInfo"),
                    properties,
                    relas,
                    image);
            retEntity.add(newOne);
        }
        return retEntity;
    }
}
