package com.java.jingjia.ui.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.java.jingjia.R;
import com.java.jingjia.util.news.TabGridAdapter;
import com.java.jingjia.util.news.TabItem;

import java.util.ArrayList;
import java.util.List;

public class EditTabActivity extends Activity {

    private final String TAG = "EditTabActivity";
    private GridView mSelectedGv, mUnselectedGv;
    private List<TabItem> selectedItems, unselectedItems;
    private TabGridAdapter selectedAdapter, unselectedAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittab);
        bindViews();
        Intent intent = getIntent();
        initData();
        selectedAdapter = new TabGridAdapter(
                EditTabActivity.this, selectedItems, "selected");
        unselectedAdapter = new TabGridAdapter(
                EditTabActivity.this, unselectedItems, "unselected");
        mSelectedGv.setAdapter(selectedAdapter);
        mUnselectedGv.setAdapter(unselectedAdapter);
        setListeners();
    }

    private void bindViews() {
        mSelectedGv = findViewById(R.id.tab_selected_gv);
        mUnselectedGv = findViewById(R.id.tab_unselected_gv);
    }

    private void setListeners() {
        mSelectedGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TabItem item = selectedAdapter.removeItem(position);
                unselectedAdapter.insertItem(item);
                updateData(item.getType(), false);
            }
        });

        mUnselectedGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TabItem item = unselectedAdapter.removeItem(position);
                selectedAdapter.insertItem(item);
                updateData(item.getType(), true);
            }
        });
    }

    private void initData() {
        selectedItems = new ArrayList<>();
        unselectedItems = new ArrayList<>();
        if (sharedPreferences == null)
            sharedPreferences = getSharedPreferences(
                    "user_tab", Context.MODE_PRIVATE);
        TabItem all = new TabItem(TabItem.ALL);
        TabItem news = new TabItem(TabItem.NEWS);
        TabItem paper = new TabItem(TabItem.PAPER);
        if (sharedPreferences.getBoolean("all", true))
            selectedItems.add(all);
        else unselectedItems.add(all);
        if (sharedPreferences.getBoolean("news", true))
            selectedItems.add(news);
        else unselectedItems.add(news);
        if (sharedPreferences.getBoolean("paper", true))
            selectedItems.add(paper);
        else unselectedItems.add(paper);
    }

    private void updateData(String tabType, Boolean selected) {
        if (sharedPreferences == null)
            sharedPreferences = getSharedPreferences("user_tab", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(tabType, selected);
        editor.apply();
    }
}
