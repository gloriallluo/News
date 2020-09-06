package com.java.jingjia.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.java.jingjia.R;

/**
 * Main page of the News App.
 */
public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private FrameLayout mFrameLayout;
    private RadioGroup mRadioGroup;
    private Fragment fgNews;
    private Fragment fgData;
    private Fragment fgGraph;
    private Fragment fgScholar;
    private Fragment fgUser;
    private FragmentManager fgManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        fgManager = getSupportFragmentManager();
        setListeners();
        RadioButton btnNews = findViewById(R.id.tab_news);
        btnNews.setChecked(true);
    }

    /**
     * 绑定组件
     */
    private void bindViews() {
        mFrameLayout = findViewById(R.id.main_fl);
        mRadioGroup = findViewById(R.id.tabs_rg);
    }

    /**
     * Set OnCheckedChangeListener to the RadioGroup
     * 点击某个RadioButton时显示对应Fragment
     */
    private void setListeners() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fgTransaction = fgManager.beginTransaction();
                hideAllFragments(fgTransaction);
                switch (checkedId) {
                    case R.id.tab_news:
                        Log.d(TAG, "onCheckedChanged: news");
                        if (fgNews == null) {
                            fgNews = new AllNewsFragment(MainActivity.this);
                            fgTransaction.add(R.id.main_fl, fgNews);
                        } else {
                            fgTransaction.show(fgNews);
                        } break;
                    case R.id.tab_data:
                        Log.d(TAG, "onCheckedChanged: data");
                        if (fgData == null) {
                            fgData = new DataFragment(MainActivity.this);
                            fgTransaction.add(R.id.main_fl, fgData);
                        } else {
                            fgTransaction.show(fgData);
                        } break;
                    case R.id.tab_graph:
                        Log.d(TAG, "onCheckedChanged: graph");
                        if (fgGraph == null) {
                            fgGraph = new GraphFragment(MainActivity.this);
                            fgTransaction.add(R.id.main_fl, fgGraph);
                        } else {
                            fgTransaction.show(fgGraph);
                        } break;
                    case R.id.tab_scholar:
                        Log.d(TAG, "onCheckedChanged: scholar");
                        if (fgScholar == null) {
                            fgScholar = new ScholarFragment(MainActivity.this);
                            fgTransaction.add(R.id.main_fl, fgScholar);
                        } else {
                            fgTransaction.show(fgScholar);
                        } break;
                    case R.id.tab_user:
                        Log.d(TAG, "onCheckedChanged: user");
                        if (fgUser == null) {
                            fgUser = new UserFragment(MainActivity.this);
                            fgTransaction.add(R.id.main_fl, fgUser);
                        } else {
                            fgTransaction.show(fgUser);
                        } break;
                    default:
                        break;
                }
                fgTransaction.commit();
        }});
    }

    /**
     * 私有方法，将所有Fragment隐藏
     * 为之后的show()方法作准备
     */
    private void hideAllFragments(FragmentTransaction fgTransaction) {
        if (fgNews != null) fgTransaction.hide(fgNews);
        if (fgData != null) fgTransaction.hide(fgData);
        if (fgGraph != null) fgTransaction.hide(fgGraph);
        if (fgScholar != null) fgTransaction.hide(fgScholar);
        if (fgUser != null) fgTransaction.hide(fgUser);
    }
}
