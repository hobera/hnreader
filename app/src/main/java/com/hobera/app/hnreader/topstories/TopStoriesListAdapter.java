package com.hobera.app.hnreader.topstories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hobera.app.hnreader.R;
import com.hobera.app.hnreader.data.Item;
import com.hobera.app.hnreader.util.AppUtils;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class TopStoriesListAdapter extends RecyclerView.Adapter<TopStoriesListAdapter.ViewHolder> {
    private OnItemClickListener listener;
    private TopStoriesContract.Presenter mPresenter;
    private ArrayList<Item> mItemList;
    private Context mContext;

    public TopStoriesListAdapter(ArrayList<Item> mItemList, Context mContext) {
        this.mItemList = mItemList;
        this.mContext = mContext;
    }

    private Context getContext() {
        return mContext;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setPresenter(TopStoriesContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void setList(ArrayList<Item> itemList) {
        mItemList = checkNotNull(itemList);
    }

    public void replaceData(ArrayList<Item> itemList) {
        setList(itemList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_rank;
        public TextView item_score;
        public TextView item_title;

        public ViewHolder(final View itemView) {
            super(itemView);

            item_rank = (TextView) itemView.findViewById(R.id.item_rank);
            item_score = (TextView) itemView.findViewById(R.id.item_score);
            item_title = (TextView) itemView.findViewById(R.id.item_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, pos);
                        }
                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemStoryView = inflater.inflate(R.layout.item_story, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemStoryView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView txtRank = holder.item_rank;
        TextView txtTitle = holder.item_title;
        TextView txtScore = holder.item_score;
        String rank = String.format("%d.",position+1);
        String title = "...";
        String score = "...";

        if (!mItemList.isEmpty()) {
            Item story = mItemList.get(position);

            if (story != null) {
                if (story.getTime()!=0) {
                    title = story.getTitle();
                    score = AppUtils.getDisplayMetaData(getContext(), story.getScore(),
                            story.getBy(), story.getTime(), story.getKids());
                } else {
                    long itemId = story.getId();
                    mPresenter.loadItem(itemId);
                }
            }
        }
        txtRank.setText(rank);
        txtTitle.setText(title);
        txtScore.setText(score);

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void updateItem(int position, Item item) {
        mItemList.set(position, item);
    }
}
