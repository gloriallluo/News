package com.java.jingjia.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.java.jingjia.R;
import com.java.jingjia.util.TabGridAdapter;
import com.java.jingjia.util.TabItem;

import java.util.ArrayList;
import java.util.List;

public class EditTabActivity extends Activity {

    private final String TAG = "EditTabActivity";
    private GridView mSelectedGv, mUnselectedGv;
    private List<TabItem> selectedItems, unselectedItems;
    private List<Long> selectedIds, unselectedIds;
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
                EditTabActivity.this, selectedItems, selectedIds, "x");
        unselectedAdapter = new TabGridAdapter(
                EditTabActivity.this, unselectedItems, unselectedIds, "+");
        mSelectedGv.setAdapter(selectedAdapter);
        mUnselectedGv.setAdapter(unselectedAdapter);
    }

    private void bindViews() {
        mSelectedGv = findViewById(R.id.tab_selected_gv);
        mUnselectedGv = findViewById(R.id.tab_unselected_gv);
    }

    private void setListeners() {
        mSelectedGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mUnselectedGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void initData() {
        selectedItems = new ArrayList<>();
        unselectedItems = new ArrayList<>();
        selectedIds = new ArrayList<>();
        unselectedIds = new ArrayList<>();
        if (sharedPreferences == null)
            sharedPreferences = getSharedPreferences("user_tab", Context.MODE_PRIVATE);
        TabItem all = new TabItem(TabItem.ALL);
        TabItem news = new TabItem(TabItem.NEWS);
        TabItem paper = new TabItem(TabItem.PAPER);
        if (sharedPreferences.getBoolean("all", true)) {
            selectedItems.add(all);
            selectedIds.add(TabItem.ALL_ID);
        } else {
            unselectedItems.add(all);
            unselectedIds.add(TabItem.ALL_ID);
        }
        if (sharedPreferences.getBoolean("news", true)) {
            selectedItems.add(news);
            selectedIds.add(TabItem.NEWS_ID);
        } else {
            unselectedItems.add(news);
            unselectedIds.add(TabItem.NEWS_ID);
        }
        if (sharedPreferences.getBoolean("paper", true)) {
            selectedItems.add(paper);
            selectedIds.add(TabItem.PAPER_ID);
        } else {
            unselectedItems.add(paper);
            unselectedIds.add(TabItem.PAPER_ID);
        }
    }

    private void updateData(String tabType, Boolean selected) {
        if (sharedPreferences == null)
            sharedPreferences = getSharedPreferences("user_tab", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(tabType, selected);
        editor.apply();
    }
}
