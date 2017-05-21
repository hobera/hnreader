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
        if (item != null && item.getKids() != null && item.getKids().length > 0) {
            mItemRepository.getCommentList(item, new ItemDataSource.GetItemListCallback() {
                @Override
                public void onItemListLoaded(ArrayList<Item> commentsList) {
                    if (commentsList.isEmpty()) {
                        mCommentsView.showNoComments();
                    } else {
                        mCommentsView.showCommentList(commentsList);
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    mCommentsView.showLoadingError();
                }
            });
        } else {
            mCommentsView.showNoComments();
        }
    }

    @Override
    public void loadComment(@NonNull long commentId) {
        mItemRepository.getItem(commentId, new ItemDataSource.GetItemCallback() {
            @Override
            public void onItemLoaded(Item item) {
                int position = item.getRank()-1;
                mCommentsView.showUpdatedComment(position, item);
            }

            @Override
            public void onDataNotAvailable() {
                Timber.d("comment %d not Loaded ", commentId);
            }
        });
    }

    @Override
    public void loadReply(@NonNull long replyId) {
        mItemRepository.getItem(replyId, new ItemDataSource.GetItemCallback() {
            @Override
            public void onItemLoaded(Item item) {
                mCommentsView.showReply(item);
            }

            @Override
            public void onDataNotAvailable() {
                Timber.d("reply %d not Loaded ", replyId);
            }
        });
    }

    @Override
    public void displayReply(@NonNull Item item) {
        mCommentsView.showReply(item);
    }

}
