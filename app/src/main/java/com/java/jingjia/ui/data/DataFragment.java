package com.java.jingjia.ui.data;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.jingjia.R;
import com.java.jingjia.database.Data;
import com.java.jingjia.request.DataManager;
import com.java.jingjia.util.data.DataExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DataFragment extends Fragment {

    private final String TAG = "DataFragment";
    public static final String CHINA = "china";
    public static final String GLOBAL = "global";

    private String type; //表示自己是china还是global
    private DataManager manager;

    //
    private List<Data> gData = null;
    private List<List<Data>> iData = null;
    private Activity mActivity;
    private ExpandableListView exlist_lol;
    private DataExpandableListAdapter mAdapter;
    //

    public DataFragment(Activity activity, String type) {
        mActivity = activity;
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        manager = DataManager.getDataManager(mActivity.getApplication());
        exlist_lol = view.findViewById(R.id.exlist_lol);
        initDataItems();
        mAdapter = new DataExpandableListAdapter(gData,iData,mActivity);
        exlist_lol.setAdapter(mAdapter);
        setListeners();
        return view;
    }

    private void setListeners() {
        //为列表设置点击事件
        exlist_lol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(mActivity, "你点击了：" + iData.get(groupPosition).get(childPosition).getPlace(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void initDataItems() {
        Log.i(TAG, "initDataItems: " + type);
        gData = new ArrayList<Data>();
        iData = new ArrayList<List<Data>>();

        if(this.type == "china"){
            gData = manager.getChinaAllProvinceAccumulatedData();
            Log.i(TAG, "initDataItems: getChinaAllProvinceAccumulatedData return size" +  gData.size());
            for(int i = 0; i < gData.size(); i++){
                List<Data> childData = manager.getProvinceAllCountyAccumulatedData(gData.get(i).getProvince());
                iData.add(childData);
            }
        }
        else{
            gData = manager.getGlobalAllCountryAccumulatedData();
            Log.i(TAG, "initDataItems: getGlobalAllCountryAccumulatedData return size" +  gData.size());
            for(int i = 0; i < gData.size(); i++){
                List<Data> childData = manager.getCountryAllProvinceAccumulatedData(gData.get(i).getProvince());
                iData.add(childData);
            }
        }
    }
}
