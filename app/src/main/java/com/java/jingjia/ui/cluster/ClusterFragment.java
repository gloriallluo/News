package com.java.jingjia.ui.cluster;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.jingjia.R;
import com.java.jingjia.util.ClusterListAdapter;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class ClusterFragment extends Fragment implements ScreenShotable {

    private final String TAG = "ClusterFragment";
    public static final String CLOSE = "close";
    private AppCompatActivity mActivity;
    private ClusterListAdapter mAdapter;

    private View mContainerView;
    private RecyclerView mRecyclerView;
    private List<EventItem> mEventItems = new ArrayList<>();
    private Bitmap bitmap;

    public ClusterFragment() { }
    public ClusterFragment(AppCompatActivity activity, List<EventItem> items) {
        mActivity = activity;
        mEventItems = items;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cluster, container, false);
        mAdapter = new ClusterListAdapter(mActivity, mEventItems);
        mRecyclerView = view.findViewById(R.id.cluster_rv);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(mActivity, DividerItemDecoration.HORIZONTAL));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContainerView = view.findViewById(R.id.cluster_container_fl);
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void takeScreenShot() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(
                        mContainerView.getWidth(), mContainerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                mContainerView.draw(canvas);
                ClusterFragment.this.bitmap = bitmap;
            }
        }).start();
    }
}
