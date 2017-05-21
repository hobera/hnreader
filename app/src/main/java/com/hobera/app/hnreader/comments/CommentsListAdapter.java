package com.hobera.app.hnreader.comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hobera.app.hnreader.R;
import com.hobera.app.hnreader.data.Item;
import com.hobera.app.hnreader.util.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Hernan Obera on 5/21/2017.
 */

public class CommentsListAdapter extends BaseExpandableListAdapter {
    private CommentsContract.Presenter mPresenter;
    private ArrayList<Item> mCommentList;
    private HashMap<String, Item> mReplyMap;
    private Context mContext;

    public CommentsListAdapter(Context mContext,
                               ArrayList<Item> mCommentList, HashMap<String, Item> mReplyMap) {
        this.mContext = mContext;
        this.mCommentList = mCommentList;
        this.mReplyMap = mReplyMap;
    }

    public void setPresenter(CommentsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void replaceData(ArrayList<Item> itemList) {
        setList(itemList);
        notifyDataSetChanged();
    }

    public void setList(ArrayList<Item> itemList) {
        mCommentList = checkNotNull(itemList);
    }

    private Context getContext() {
        return mContext;
    }

    public void updateComment(int position, Item item) {
        if (position < mCommentList.size()) {
            mCommentList.set(position, item);
        }
    }

    public void addReply(Item item) {
        if (item!=null) {
            mReplyMap.put(String.valueOf(item.getParent()), item);
        }
    }

    @Override
    public int getGroupCount() {
        return mCommentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count = 0;
        if (mReplyMap != null) {
            Item reply = mReplyMap.get(
                    String.valueOf(mCommentList.get(groupPosition).getId()));
            if (reply!=null) {
                count = 1;
            }
        }

        return count;

    }

    @Override
    public Item getGroup(int groupPosition) {
        return mCommentList.get(groupPosition);
    }

    @Override
    public Item getChild(int groupPosition, int childPosition) {
        Item reply = null;
        if (mReplyMap != null) {
            reply = mReplyMap.get(
                    String.valueOf(mCommentList.get(groupPosition).getId()));
        }

        return reply;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView,
                             final ViewGroup parent) {
        Item comment = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_comment, null);
        }

        if (comment!=null) {
            if (comment.getTime()!=0) {

                if (comment.isDeleted()) {
                    //TODO: remove from list
                    notifyDataSetChanged();
                    return convertView;
                }

                TextView item_by = (TextView) convertView.findViewById(R.id.item_by);
                TextView item_time = (TextView) convertView.findViewById(R.id.item_time);
                TextView item_comment = (TextView) convertView.findViewById(R.id.item_comment);
                TextView item_replies = (TextView) convertView.findViewById(R.id.item_replies);

                item_by.setText(comment.getBy());
                item_time.setText(AppUtils.getDisplayedTime(getContext(), comment.getTime()));
                item_comment.setText(comment.getText());
                item_replies.setText(String.format(
                        getContext().getString(R.string.replies),
                        isExpanded?
                                getContext().getString(R.string.hide):
                                getContext().getString(R.string.show)));

                if (comment.getKids()!=null
                        && comment.getKids().length > 0
                        && !comment.isDeleted()) {
                    item_replies.setVisibility(View.VISIBLE);
                } else {
                    item_replies.setVisibility(View.GONE);
                }

                item_replies.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isExpanded) {
                            ((ExpandableListView) parent).collapseGroup(groupPosition);
                        } else {
                            ((ExpandableListView) parent).expandGroup(groupPosition);
                        }
                    }
                });
            } else {
                long itemId = comment.getId();
                mPresenter.loadComment(itemId);
            }
        }

        return convertView;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        Item item = getGroup(groupPosition);
        if (item != null) {
            long replyId = item.getKids()[0];

            Item replyItem = mReplyMap.get(String.valueOf(item.getId()));
            if (replyItem != null) {
                mPresenter.displayReply(replyItem);
            } else {
                mPresenter.loadReply(replyId);
            }
        }

        super.onGroupExpanded(groupPosition);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {
        Item reply = getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_comment, null);
        }

        if (reply!=null) {
            LinearLayout padding_layout = (LinearLayout)
                    convertView.findViewById(R.id.padding_layout);
            TextView item_by = (TextView) convertView.findViewById(R.id.item_by);
            TextView item_time = (TextView) convertView.findViewById(R.id.item_time);
            TextView item_comment = (TextView) convertView.findViewById(R.id.item_comment);

            item_by.setText(reply.getBy());
            item_time.setText(AppUtils.getDisplayedTime(getContext(), reply.getTime()));
            item_comment.setText(reply.getText());

            padding_layout.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
