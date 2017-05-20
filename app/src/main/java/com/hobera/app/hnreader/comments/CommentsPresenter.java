package com.hobera.app.hnreader.comments;

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

public class CommentsPresenter implements CommentsContract.Presenter {
    private final ItemRepository mItemRepository;

    private final CommentsContract.View mCommentsView;

    public CommentsPresenter(@NonNull ItemRepository mItemRepository,
                             @NonNull CommentsContract.View mCommentsView) {
        this.mItemRepository = checkNotNull(mItemRepository);
        this.mCommentsView = checkNotNull(mCommentsView);

        mCommentsView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode) {

    }

    @Override
    public void loadComments(Item item) {

    }

    @Override
    public void loadComment(@NonNull long itemId) {

    }

    @Override
    public void loadReply(@NonNull long itemId) {

    }

    @Override
    public void displayReply(@NonNull Item item) {

    }


}
