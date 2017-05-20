package com.hobera.app.hnreader.topstories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.hobera.app.hnreader.data.Item;

import java.util.ArrayList;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class TopStoriesFragment extends Fragment implements TopStoriesContract.View {

    public TopStoriesFragment() {
    }

    public static TopStoriesFragment newInstance() {
        return new TopStoriesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(TopStoriesContract.Presenter presenter) {

    }

    @Override
    public void showTopStoryList(ArrayList<Item> itemList) {

    }

    @Override
    public void showNoTopStoryList() {

    }

    @Override
    public void showLoadingError() {

    }
}
