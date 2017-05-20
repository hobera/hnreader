package com.hobera.app.hnreader.topstories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hobera.app.hnreader.R;
import com.hobera.app.hnreader.data.Item;

import java.util.ArrayList;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class TopStoriesFragment extends Fragment implements TopStoriesContract.View {

    private TopStoriesContract.Presenter mPresenter;

    private LinearLayout mTopStoriesLayout;
    private RecyclerView mTopStoriesRecycler;
    private LinearLayout mNoStoriesLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public TopStoriesFragment() {
    }

    public static TopStoriesFragment newInstance() {
        return new TopStoriesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_topstories, container, false);

        mTopStoriesLayout = (LinearLayout) root.findViewById(R.id.topstories_layout);
        mTopStoriesRecycler = (RecyclerView) root.findViewById(R.id.topstories_recycler);

        mNoStoriesLayout = (LinearLayout) root.findViewById(R.id.no_stories_layout);

        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadTopStories();
            }
        });

        return root;
    }

    @Override
    public void setPresenter(TopStoriesContract.Presenter presenter) {

    }

    @Override
    public void showTopStoryList(ArrayList<Item> itemList) {

    }

    @Override
    public void showNoTopStoryList() {

    }

    @Override
    public void showUpdatedItem(int rank, Item item) {

    }

    @Override
    public void showLoadingError() {

    }
}
