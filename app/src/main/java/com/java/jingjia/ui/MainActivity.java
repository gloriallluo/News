package com.java.jingjia.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.java.jingjia.R;
import com.java.jingjia.ui.cluster.AllClusterFragment;
import com.java.jingjia.ui.data.AllDataFragment;
import com.java.jingjia.ui.graph.GraphFragment;
import com.java.jingjia.ui.news.AllNewsFragment;

/**
 * Main page of the News App.
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private FrameLayout mFrameLayout;
    private RadioGroup mRadioGroup;
    private AllNewsFragment fgNews;
    private AllDataFragment fgData;
    private GraphFragment fgGraph;
    private ScholarFragment fgScholar;
    private AllClusterFragment fgCluster;
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
        initButtonImg();
    }

    /**
     * 初始化底部Button图像
     */
    private void initButtonImg(){
        //定义底部标签图片大小和位置
        Drawable drawable_news = getResources().getDrawable(R.drawable.news_button);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_news.setBounds(0, 10, 70, 70);
        //设置图片在文字的哪个方向
        ((RadioButton) mRadioGroup.getChildAt(0)).setCompoundDrawables(
                null, drawable_news, null, null);

        Drawable drawable_data = getResources().getDrawable(R.drawable.data_button);
        drawable_data.setBounds(0, 10, 70, 70);
        ((RadioButton) mRadioGroup.getChildAt(1)).setCompoundDrawables(
                null, drawable_data, null, null);

        Drawable drawable_graph = getResources().getDrawable(R.drawable.graph_button);
        drawable_graph.setBounds(0, 10, 70, 70);
        ((RadioButton) mRadioGroup.getChildAt(2)).setCompoundDrawables(
                null, drawable_graph, null, null);

        Drawable drawable_scholar = getResources().getDrawable(R.drawable.scholar_button);
        drawable_scholar.setBounds(0, 10, 70, 70);
        ((RadioButton) mRadioGroup.getChildAt(3)).setCompoundDrawables(
                null, drawable_scholar, null, null);

        Drawable drawable_cluster = getResources().getDrawable(R.drawable.cluster_button);
        drawable_cluster.setBounds(0, 10, 70, 70);
        ((RadioButton) mRadioGroup.getChildAt(4)).setCompoundDrawables(
                null, drawable_cluster, null, null);
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
                        if (fgNews == null) {
                            fgNews = new AllNewsFragment(MainActivity.this);
                            fgTransaction.add(R.id.main_fl, fgNews);
                        } else {
                            fgTransaction.show(fgNews);
                        } break;
                    case R.id.tab_data:
                        if (fgData == null) {
                            fgData = new AllDataFragment(MainActivity.this);
                            fgTransaction.add(R.id.main_fl, fgData);
                        } else {
                            fgTransaction.show(fgData);
                        } break;
                    case R.id.tab_graph:
                        if (fgGraph == null) {
                            fgGraph = new GraphFragment(MainActivity.this);
                            fgTransaction.add(R.id.main_fl, fgGraph);
                        } else {
                            fgTransaction.show(fgGraph);
                        } break;
                    case R.id.tab_scholar:
                        if (fgScholar == null) {
                            fgScholar = new ScholarFragment(MainActivity.this);
                            fgTransaction.add(R.id.main_fl, fgScholar);
                        } else {
                            fgTransaction.show(fgScholar);
                        } break;
                    case R.id.tab_cluster:
                        if (fgCluster == null) {
                            fgCluster = new AllClusterFragment(MainActivity.this);
                            fgTransaction.add(R.id.main_fl, fgCluster);
                        } else {
                            fgTransaction.show(fgCluster);
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
        if (fgCluster != null) fgTransaction.hide(fgCluster);
    }
}
