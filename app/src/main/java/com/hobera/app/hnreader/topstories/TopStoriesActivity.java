package com.hobera.app.hnreader.topstories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hobera.app.hnreader.Injection;
import com.hobera.app.hnreader.R;
import com.hobera.app.hnreader.util.ActivityUtils;

import timber.log.Timber;

public class TopStoriesActivity extends AppCompatActivity {

    private TopStoriesPresenter mTopStoriesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topstories);

        Timber.plant(new Timber.DebugTree());

        TopStoriesFragment topStoriesFragment =
                (TopStoriesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (topStoriesFragment == null) {
            topStoriesFragment = TopStoriesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), topStoriesFragment, R.id.contentFrame);
        }

        mTopStoriesPresenter = new TopStoriesPresenter(
                Injection.provideItemRepository(), topStoriesFragment);
    }
}
