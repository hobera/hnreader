package com.hobera.app.hnreader.topstories;

import android.support.v4.app.Fragment;

import com.hobera.app.hnreader.data.Item;

import java.util.ArrayList;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class TopStoriesFragment extends Fragment implements TopStoriesContract.View {
    @Override
    public void setPresenter(TopStoriesContract.Presenter presenter) {
        
    }

    @Override
    public void showTopStoryList(ArrayList<Item> itemList) {

    }

    @Override
    public void showNoTopStoryList() {

    }
}
