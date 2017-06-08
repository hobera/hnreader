package com.hobera.app.hnreader.topstories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.hobera.app.hnreader.BuildConfig;
import com.hobera.app.hnreader.Injection;
import com.hobera.app.hnreader.R;
import com.hobera.app.hnreader.util.ActivityUtils;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class TopStoriesActivity extends AppCompatActivity {

    private TopStoriesPresenter mTopStoriesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(!BuildConfig.USE_CRASHLYTICS).build()).build());
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
