package com.hobera.app.hnreader.topstories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hobera.app.hnreader.R;
import com.hobera.app.hnreader.data.Item;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class TopStoriesFragment extends Fragment implements TopStoriesContract.View {

    private TopStoriesContract.Presenter mPresenter;

    private LinearLayout mTopStoriesLayout;
    private RecyclerView mTopStoriesRecycler;
    private LinearLayout mNoStoriesLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TopStoriesListAdapter mAdapter;

    public TopStoriesFragment() {
    }

    public static TopStoriesFragment newInstance() {
        return new TopStoriesFragment();
    }

    private void showList(boolean bShow){
        mTopStoriesLayout.setVisibility(bShow ? View.VISIBLE : View.GONE);
        mNoStoriesLayout.setVisibility(bShow ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mAdapter = new TopStoriesListAdapter(new ArrayList<Item>(0), getContext());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        showList(true);
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.start();
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
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showTopStoryList(final ArrayList<Item> itemList) {
        mAdapter.replaceData(itemList);
        mTopStoriesRecycler.setAdapter(mAdapter);
        mTopStoriesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mTopStoriesRecycler.addItemDecoration(itemDecoration);

        mAdapter.setOnItemClickListener(new TopStoriesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Item story = itemList.get(position);
                if (story!=null) {
                    // TODO: open comments
                }
            }
        });

        mAdapter.setPresenter(mPresenter);

        showList(true);

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNoTopStoryList() {
        showList(false);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showUpdatedItem(final int position, Item item) {
        mAdapter.updateItem(position, item);

        mTopStoriesRecycler.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    public void showLoadingError() {
        showList(false);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
