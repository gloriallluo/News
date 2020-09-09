package com.java.jingjia.request;

import com.java.jingjia.Scholar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ScholarManager
 * 1. 用于从url获得学者信息
 */
public class ScholarManager {
    //单例
    private static ScholarManager INSTANCE = null;
    private ScholarManager() {
    }
    /** 获得DataManager单例，需要传入参数Application application */
    public static ScholarManager getScholarManager() {
        if (INSTANCE == null) {
            INSTANCE = new ScholarManager();
        }
        return INSTANCE;
    }

    public List<Scholar> getScholars() {
        String url = "https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2";
        String jsonString = HttpUtil.getServerHttpResponse().getResponse(url);
        List<Scholar> retScholar = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray data = jsonObject.getJSONArray("data");
            Integer numOfScholar = data.length();
            for (int i = 0; i < numOfScholar; i++) {
                JSONObject oneScholar = data.getJSONObject(i);
                String avatar = oneScholar.getString("avatar");
                String id = oneScholar.getString("id");

                //indices
                JSONObject indices = oneScholar.getJSONObject("indices");
                Double activity = indices.getDouble("activity");
                Integer citations = indices.getInt("citations");
                Double diversity = indices.getDouble("diversity");
                Integer gindex = indices.getInt("gindex");
                Integer hindex = indices.getInt("hindex");
                Double newStar = indices.getDouble("newStar");
                Double risingStar = indices.getDouble("risingStar");
                Double sociability = indices.getDouble("sociability");

                String name = oneScholar.getString("name");
                String name_zh = oneScholar.getString("name_zh");

                //profile
                JSONObject profile = oneScholar.getJSONObject("profile");
                String affiliation = profile.getString("affiliation");
                String affiliation_zh = profile.getString("affiliation_zh");
                String bio = profile.getString("bio");
                String homepage = profile.getString("homepage");
                String position = profile.getString("position");
                String work = profile.getString("work");

                Boolean is_passedaway = oneScholar.getBoolean("is_passedaway");

                Scholar newOne = new Scholar(name, name_zh, affiliation, affiliation_zh, bio,
                        homepage, position, work, avatar, id, activity, citations, diversity,
                        gindex, hindex, newStar, risingStar, sociability, is_passedaway);
                retScholar.add(newOne);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retScholar;
    }
}
