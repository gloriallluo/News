package com.java.jingjia;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserFragment extends Fragment {

    private String TAG = "UserFragment";
    private TextView userCollection, userDownloads, userHistory;
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
        userCollection = view.findViewById(R.id.user_collection);
        userDownloads = view.findViewById(R.id.user_downloads);
        userHistory = view.findViewById(R.id.user_history);
    }
    
    private void setListeners() {
        userCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click collection");
            }
        });
        userDownloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click downloads");
            }
        });
        userHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click history");
            }
        });
    }
}
