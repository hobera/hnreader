package com.hobera.app.hnreader.topstories;

import android.support.annotation.NonNull;

import com.hobera.app.hnreader.data.Item;
import com.hobera.app.hnreader.data.source.ItemDataSource;
import com.hobera.app.hnreader.data.source.ItemRepository;

import java.util.ArrayList;

import timber.log.Timber;

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

    @Override
    public void loadItem(@NonNull final long itemId) {
        mItemRepository.getItem(itemId, new ItemDataSource.GetItemCallback() {
            @Override
            public void onItemLoaded(Item item) {
                int position = item.getRank()-1;
                mTopStoriesView.showUpdatedItem(position, item);
            }

            @Override
            public void onDataNotAvailable() {
                Timber.d("item %d not Loaded ", itemId);
            }
        });
    }
}
