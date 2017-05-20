package com.hobera.app.hnreader.topstories;

import android.support.annotation.NonNull;

import com.hobera.app.hnreader.BasePresenter;
import com.hobera.app.hnreader.BaseView;
import com.hobera.app.hnreader.data.Item;

import java.util.ArrayList;

/**
 * Created by Hernan Obera on 5/20/2017.
 */
/**
 * This specifies the contract between the view and the presenter.
 */
public interface TopStoriesContract {
    interface View extends BaseView<Presenter> {
        void showTopStoryList(ArrayList<Item> itemList);

        void showNoTopStoryList();

        void showUpdatedItem(int rank, Item item);

        void showLoadingError();
    }

    interface Presenter extends BasePresenter {
        void onActivityResult(int requestCode, int resultCode);

        void loadTopStories();

        void loadItem(@NonNull long itemId);
    }
}
