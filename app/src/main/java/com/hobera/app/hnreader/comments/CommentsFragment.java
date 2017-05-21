package com.hobera.app.hnreader.comments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.hobera.app.hnreader.R;
import com.hobera.app.hnreader.data.Item;

import java.util.ArrayList;
import java.util.HashMap;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Hernan Obera on 5/21/2017.
 */

public class CommentsFragment extends Fragment implements CommentsContract.View {

    private static final String ARGUMENT_COMMENT_ITEM = "COMMENT_ITEM";
    private CommentsContract.Presenter mPresenter;

    private LinearLayout mCommentsLayout;
    private ExpandableListView mCommentsExpandableList;
    private LinearLayout mNoCommentsLayout;
    private CommentsListAdapter mAdapter;
    private Item commentItem;

    public CommentsFragment() {
    }

    public static CommentsFragment newInstance(Item item) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_COMMENT_ITEM, item);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        commentItem = getArguments().getParcelable(ARGUMENT_COMMENT_ITEM);
        mAdapter = new CommentsListAdapter(getContext(), new ArrayList<Item>(0),
                new HashMap<String, Item>());
    }

    @Override
    public void onResume() {
        super.onResume();
        showList(true);
        mPresenter.loadComments(commentItem);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_comments, container, false);

        mCommentsLayout = (LinearLayout) root.findViewById(R.id.comments_layout);
        mCommentsExpandableList = (ExpandableListView) root.findViewById(R.id.comments_expandable_list);
        mCommentsExpandableList.setGroupIndicator(null);
        mCommentsExpandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                                        long id) {
                return true;
            }
        });

        mNoCommentsLayout = (LinearLayout) root.findViewById(R.id.no_comments_layout);

        return root;
    }

    @Override
    public void setPresenter(CommentsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showCommentList(final ArrayList<Item> itemList) {
        mAdapter.replaceData(itemList);
        mCommentsExpandableList.setAdapter(mAdapter);

        mAdapter.setPresenter(mPresenter);

        showList(true);
    }

    @Override
    public void showNoComments() {
        showList(false);
    }

    @Override
    public void showUpdatedComment(int position, Item item) {
        mAdapter.updateComment(position, item);

        mCommentsExpandableList.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showLoadingError() {
        showList(false);
    }

    @Override
    public void showReply(Item item) {
        mAdapter.addReply(item);

        mCommentsExpandableList.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showList(boolean bShow){
        mCommentsLayout.setVisibility(bShow ? View.VISIBLE : View.GONE);
        mNoCommentsLayout.setVisibility(bShow ? View.GONE : View.VISIBLE);
    }

}
