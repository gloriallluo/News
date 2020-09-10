//package com.java.jingjia.ui;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.java.jingjia.Entity;
//import com.java.jingjia.NewsItem;
//import com.java.jingjia.R;
//import com.java.jingjia.request.NewsContentManager;
//import com.java.jingjia.request.NewsListManager;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * 新闻详情页Activity
// */
//public class EntityActivity extends AppCompatActivity {
//
//    private final String TAG = "EntityActivity";
////    private final int MSG_SET_VISITED = 0x2000;
//
//    private Entity mItem;
//    Map properties;
//    List<Entity.Relation> relations;
//    String imageUrl;
//    private TextView mLabel, mAbstractInfo;
//
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        Log.d(TAG, "onCreate: ");
//        super.onCreate(savedInstanceState);
////        if (getSupportActionBar() != null)
////            getSupportActionBar().hide();
//        setContentView(R.layout.activity_entity);
//        bindViews();
//        mContent.setVerticalScrollBarEnabled(true);
//
//        Intent intent = getIntent();
//        String mItemId = intent.getStringExtra("news");
//        listManager = NewsListManager.getNewsListManager(getApplication());
//        contentManager = NewsContentManager.getNewsContentManager();
//        mItem = contentManager.getNewsItem(mItemId);
//        Message msg = new Message();
//        msg.what = MSG_SET_VISITED;
//
//        setListeners();
//        mTitle.setText(mItem.getTitle());
//        mSource.setText(mItem.getSource());
//        mTime.setText(mItem.getTime());
//        mContent.setText(mItem.getContent());
//    }
//
//    private void bindViews() {
//        mLabel = findViewById(R.id.entity_page_label);
//        mAbstractInfo = findViewById(R.id.entity_page_info);
//        mTime = findViewById(R.id.news_page_time);
//        mContent = findViewById(R.id.news_page_content);
//        mBtnLike = findViewById(R.id.news_page_btn_like);
//        mBtnCollect = findViewById(R.id.news_page_btn_collect);
//        mBtnWb = findViewById(R.id.news_page_btn_wb);
//        mBtnWx = findViewById(R.id.news_page_btn_wx);
//    }
//
//    private void setListeners() {
//        mBtnLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: you liked this news");
//            }
//        });
//        mBtnCollect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: you collected this news");
//            }
//        });
//        mBtnWb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: you share this news to Weibo");
//            }
//        });
//        mBtnWx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: you share this news to Wechat");
//            }
//        });
//    }
//}
