package com.java.jingjia.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.java.jingjia.R;

public class EditTabActivity extends Activity {

    private final String TAG = "EditTabActivity";
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittab);
        mLinearLayout = findViewById(R.id.edit_tab_ll);
        Intent intent = getIntent();
    }
}
