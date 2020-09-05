package com.java.jingjia.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.jingjia.R;

public class ScholarFragment extends Fragment {

    private String TAG = "ScholarFragment";
    private Activity mActivity;

    public ScholarFragment(Activity activity) {
        Log.d(TAG, "ScholarFragment: 1");
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scholar, container, false);
        return view;
    }
}
