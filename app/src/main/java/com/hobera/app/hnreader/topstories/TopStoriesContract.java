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

        void showUpdatedItem(int position, Item item);

        void showLoadingError();

        void showItemComments(Item item);
    }

    interface Presenter extends BasePresenter {
        void onActivityResult(int requestCode, int resultCode);

        void loadTopStories(boolean forceUpdate);

        void loadItem(@NonNull long itemId);

        void openItemComments(@NonNull Item item);
    }
}
