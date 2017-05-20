package com.hobera.app.hnreader.comments;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

import android.support.annotation.NonNull;

import com.hobera.app.hnreader.BasePresenter;
import com.hobera.app.hnreader.BaseView;
import com.hobera.app.hnreader.data.Item;

import java.util.ArrayList;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface CommentsContract {
    interface View extends BaseView<Presenter> {
        void showCommentList(ArrayList<Item> itemList);

        void showNoComments();

        void showUpdatedComment(int rank, Item item);

        void showLoadingError();

        void showReply(Item item);
    }

    interface Presenter extends BasePresenter {
        void onActivityResult(int requestCode, int resultCode);

        void loadComments(Item item);

        void loadComment(@NonNull long itemId);

        void loadReply(@NonNull long itemId);

        void displayReply(@NonNull Item item);
    }
}