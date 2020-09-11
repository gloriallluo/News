package com.java.jingjia.ui.data;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.jingjia.NewsItem;
import com.java.jingjia.R;
import com.java.jingjia.database.Data;
import com.java.jingjia.request.DataManager;
import com.java.jingjia.util.data.DataExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DataFragment extends Fragment {

    private final String TAG = "DataFragment";
    public static final String CHINA = "china";
    public static final String GLOBAL = "global";

    private String type; //表示自己是china还是global
    private DataManager manager;

    private List<Data> gData = null;
    private List<List<Data>> iData = null;
    private Activity mActivity;
    public ExpandableListView exDataList;
    private DataExpandableListAdapter mAdapter;

    public DataFragment(Activity activity, String type) {
        mActivity = activity;
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        manager = DataManager.getDataManager(mActivity.getApplication());
        exDataList = view.findViewById(R.id.exlist_lol);
        initDataItems();
        mAdapter = new DataExpandableListAdapter(gData,iData,mActivity);
        exDataList.setAdapter(mAdapter);
        setListeners();
        if(this.type.equals(GLOBAL)){
            exDataList.setGroupIndicator(null);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread() {
            @Override
            public void run() {
                manager.getData();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        exDataList.deferNotifyDataSetChanged();
                    }
                });
            }
        }.run();
    }


    private void setListeners() {
        //为列表设置点击事件
        exDataList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(parent.isGroupExpanded(groupPosition)){
                    parent.collapseGroup(groupPosition);
                }else{
                    parent.expandGroup(groupPosition,false);//第二个参数false表示展开时是否触发默认滚动动画
                }
                //telling the listView we have handled the group click, and don't want the default actions.
                return true;
            }
        });
    }

    private void initDataItems() {
        Log.i(TAG, "initDataItems: " + type);
        gData = new ArrayList<>();
        iData = new ArrayList<>();

        if(this.type.equals("china")){
            gData = manager.getChinaAllProvinceAccumulatedData();
            Log.i(TAG, "initDataItems: getChinaAllProvinceAccumulatedData return size" +  gData.size());
            for(int i = 0; i < gData.size(); i++){
                List<Data> childData = manager.getProvinceAllCountyAccumulatedData(gData.get(i).getProvince());
                iData.add(childData);
            }
        }
        else{//global
            gData = manager.getGlobalAllCountryAccumulatedData();
            Log.i(TAG, "initDataItems: getGlobalAllCountryAccumulatedData return size" +  gData.size());
            for(int i = 0; i < gData.size(); i++){
                List<Data> childData = manager.getCountryAllProvinceAccumulatedData(gData.get(i).getProvince());
                iData.add(childData);
            }
        }
    }
}
