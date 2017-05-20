package com.hobera.app.hnreader.comments;

import android.support.v4.app.Fragment;

import com.hobera.app.hnreader.data.Item;

import java.util.ArrayList;

/**
 * Created by Hernan Obera on 5/21/2017.
 */

public class CommentsFragment extends Fragment implements CommentsContract.View {
    @Override
    public void setPresenter(CommentsContract.Presenter presenter) {

    }

    @Override
    public void showCommentList(ArrayList<Item> itemList) {

    }

    @Override
    public void showNoComments() {

    }

    @Override
    public void showUpdatedComment(int rank, Item item) {

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void showReply(Item item) {

    }
}
