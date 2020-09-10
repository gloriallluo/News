package com.java.jingjia.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.java.jingjia.NewsItem;
import com.java.jingjia.R;
import com.java.jingjia.request.NewsContentManager;
import com.java.jingjia.request.NewsListManager;
import com.java.jingjia.ui.news.ShareActivity;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.WBAPIFactory;
import com.sina.weibo.sdk.share.WbShareCallback;

/**
 * 新闻详情页Activity
 */
public class NewsActivity extends AppCompatActivity {

    private final String TAG = "NewsActivity";
    private static final String APP_KEY = "2235819906";
    private static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html/";
    private static final String SCOPE = "";
    private IWBAPI mWeiboAPI;

    private NewsItem mItem;
    private TextView mTitle, mSource, mTime, mContent;
    private Button mBtnWb;
    private NewsListManager listManager;
    private NewsContentManager contentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: 2");
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_news);
        bindViews();
        mContent.setVerticalScrollBarEnabled(true);
        // initSDK();

        Intent intent = getIntent();
        String mItemId = intent.getStringExtra("news");
        listManager = NewsListManager.getNewsListManager(getApplication());
        contentManager = NewsContentManager.getNewsContentManager();
        // TODO: set news visited
        new Thread(new Runnable() {
            @Override
            public void run() {
                mItem = contentManager.getNewsItem(mItemId);
                listManager.setVisitedNews(mItemId);
                Log.d(TAG, "run: " + mItem.getVisited());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mTitle.setText(mItem.getTitle());
                        mSource.setText(mItem.getSource());
                        mTime.setText(mItem.getTime());
                        mContent.setText(mItem.getContent());
                    }
                });
            }}).start();
        setListeners();
    }

    private void bindViews() {
        mTitle = findViewById(R.id.news_page_title);
        mSource = findViewById(R.id.news_page_source);
        mTime = findViewById(R.id.news_page_time);
        mContent = findViewById(R.id.news_page_content);
        mBtnWb = findViewById(R.id.news_page_btn_wb);
    }

    private void setListeners() {
        mBtnWb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: you share this news to Weibo");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share to Weibo");
                intent.putExtra(Intent.EXTRA_TEXT, mItem.getContent());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, "share"));

//                startAuth();
//                Intent intent = new Intent(NewsActivity.this, ShareActivity.class);
//                intent.putExtra("share", mItem);
//                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mWeiboAPI != null) mWeiboAPI.authorizeCallback(requestCode, resultCode, data);
    }

    private void initSDK() {
        AuthInfo authInfo = new AuthInfo(this, APP_KEY, REDIRECT_URL, SCOPE);
        mWeiboAPI = WBAPIFactory.createWBAPI(this);
        mWeiboAPI.registerApp(this, authInfo);
    }

    private void startAuth() {
        mWeiboAPI.authorize(new WbAuthListener() {
            @Override
            public void onComplete(Oauth2AccessToken token) {
                Toast.makeText(
                        NewsActivity.this, "微博授权成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(UiError error) {
                Toast.makeText(
                        NewsActivity.this, "微博授权出错", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancel() {
                Toast.makeText(
                        NewsActivity.this, "微博授权取消", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
