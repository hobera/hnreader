package com.hobera.app.hnreader.topstories;

import android.support.annotation.NonNull;

import com.hobera.app.hnreader.data.Item;
import com.hobera.app.hnreader.data.source.ItemDataSource;
import com.hobera.app.hnreader.data.source.ItemRepository;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class TopStoriesPresenter implements TopStoriesContract.Presenter {

    private final ItemRepository mItemRepository;

    private final TopStoriesContract.View mTopStoriesView;

    public TopStoriesPresenter(@NonNull ItemRepository mItemRepository,
                               @NonNull TopStoriesContract.View mTopStoriesView) {
        this.mItemRepository = checkNotNull(mItemRepository);
        this.mTopStoriesView = checkNotNull(mTopStoriesView);

        mTopStoriesView.setPresenter(this);
    }

    @Override
    public void start() {
        loadTopStories();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode) {

    }

    @Override
    public void loadTopStories() {
        mItemRepository.getItemList(new ItemDataSource.GetItemListCallback() {
            @Override
            public void onItemListLoaded(ArrayList<Item> topStoriesList) {
                if (topStoriesList.isEmpty()) {
                    mTopStoriesView.showNoTopStoryList();
                } else {
                    mTopStoriesView.showTopStoryList(topStoriesList);
                }
            }

            @Override
            public void onDataNotAvailable() {
                mTopStoriesView.showLoadingError();
            }
        });
    }
}
