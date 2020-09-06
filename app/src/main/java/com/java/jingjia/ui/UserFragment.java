package com.java.jingjia.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.jingjia.R;

public class UserFragment extends Fragment {

    private final String TAG = "UserFragment";
    private Button userCollection, userDownloads, userHistory;
    private Activity mActivity;

    public UserFragment(Activity activity) {
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        bindViews(view);
        setListeners();
        return view;
    }
    
    private void bindViews(View view) {
        userCollection = view.findViewById(R.id.btn_user_collection);
        userDownloads = view.findViewById(R.id.btn_user_downloads);
        userHistory = view.findViewById(R.id.btn_user_history);
    }
    
    private void setListeners() {
        userCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click collection");
                Intent intent = new Intent(mActivity, UserDetailActivity.class);
                intent.putExtra("user", 1);
                mActivity.startActivity(intent);
            }
        });
        userDownloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click downloads");
                Intent intent = new Intent(mActivity, UserDetailActivity.class);
                intent.putExtra("user", 2);
                mActivity.startActivity(intent);
            }
        });
        userHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click history");
                Intent intent = new Intent(mActivity, UserDetailActivity.class);
                intent.putExtra("user", 3);
                mActivity.startActivity(intent);
            }
        });
    }
}
