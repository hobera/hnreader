package com.hobera.app.hnreader.comments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.hobera.app.hnreader.Injection;
import com.hobera.app.hnreader.R;
import com.hobera.app.hnreader.data.Item;
import com.hobera.app.hnreader.util.ActivityUtils;
import com.hobera.app.hnreader.util.AppUtils;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class CommentsActivity extends AppCompatActivity {
    private CommentsPresenter commentsPresenter;
    public static final String EXTRA_ITEM_COMMENT = "ITEM_COMMENT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_HOME_AS_UP);

        Item item = getIntent().getParcelableExtra(EXTRA_ITEM_COMMENT);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        TextView txtTitle = (TextView) appBarLayout.findViewById(R.id.item_title);
        TextView txtScore = (TextView) appBarLayout.findViewById(R.id.item_score);

        txtTitle.setText(item.getTitle());
        txtScore.setText(AppUtils.getDisplayMetaData(this, item.getScore(),
                item.getBy(), item.getTime(), item.getKids()));

        CommentsFragment commentsFragment =
                (CommentsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameComments);
        if (commentsFragment == null) {
            commentsFragment = CommentsFragment.newInstance(item);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), commentsFragment, R.id.contentFrameComments);
        }

        commentsPresenter = new CommentsPresenter(
                Injection.provideItemRepository(), commentsFragment);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
