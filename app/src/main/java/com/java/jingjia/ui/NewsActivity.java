package com.java.jingjia.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
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
import com.flyco.dialog.widget.NormalListDialog;

import java.util.ArrayList;


/**
 * 新闻详情页Activity
 */
public class NewsActivity extends AppCompatActivity {

    private final String TAG = "NewsActivity";
    private static final String APP_KEY = "2235819906";
    private static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html/";
    private static final String SCOPE = "";
    private Context mContext = this;
    private IWBAPI mWeiboAPI;

    private NewsItem mItem;
    private TextView mTitle, mSource, mTime, mContent;
    private Button mBtnWb;
    BaseAnimatorSet mBasIn;
    BaseAnimatorSet mBasOut;
    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();
    public void setBasIn(BaseAnimatorSet bas_in) {
        this.mBasIn = bas_in;
    }
    public void setBasOut(BaseAnimatorSet bas_out) {
        this.mBasOut = bas_out;
    }
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
        Drawable drawable_share = getResources().getDrawable(R.drawable.share_button);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_share.setBounds(0, 0, 70, 70);
        DialogMenuItem shareText = new DialogMenuItem("文本", R.drawable.share_button_small);
        mMenuItems.add(shareText);
        mMenuItems.add(new DialogMenuItem("微博", R.drawable.weibo_button_small));
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
    }

    private void bindViews() {
        mTitle = findViewById(R.id.news_page_title);
        mSource = findViewById(R.id.news_page_source);
        mTime = findViewById(R.id.news_page_time);
        mContent = findViewById(R.id.news_page_content);
        mBtnWb = findViewById(R.id.news_page_btn_wb);
        //定义底部标签图片大小和位置
        Drawable drawable_share = getResources().getDrawable(R.drawable.share_button);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_share.setBounds(0, 0, 70, 70);
        //设置图片在文字的哪个方向
        mBtnWb.setCompoundDrawables(drawable_share, null, null, null);
        mBtnWb.setTextColor(getResources().getColor(R.color.gray) );
    }

    private void setListeners() {
        mBtnWb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: you share this news");
                mBtnWb.post(new Runnable() {
                    @Override
                    public void run() {
                        NormalListDialog();
                    }
                });
            }
        });
    }

    private void NormalListDialog() {
        final NormalListDialog dialog = new NormalListDialog(mContext, mMenuItems);
        dialog.title("请选择...")//
                .titleBgColor(R.color.yd_navy_blue)
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (mMenuItems.get(position).mOperName){
                    case "文本":
                        Toast toast_share = Toast.makeText(mContext,"share", Toast.LENGTH_SHORT);
                        toast_share.show();
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Share to Weibo");
                        intent.putExtra(Intent.EXTRA_TEXT, mItem.getContent());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent, "share"));
                        break;
                    case "微博":
                        Toast toast_weibo = Toast.makeText(mContext,"weibo", Toast.LENGTH_SHORT);
                        toast_weibo.show();
    //                startAuth();
    //                Intent intent = new Intent(NewsActivity.this, ShareActivity.class);
    //                intent.putExtra("share", mItem);
    //                startActivity(intent);
                      break;
                }
                dialog.dismiss();
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
