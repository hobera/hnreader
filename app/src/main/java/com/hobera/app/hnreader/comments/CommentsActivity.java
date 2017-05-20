package com.hobera.app.hnreader.comments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class CommentsActivity extends AppCompatActivity {
    private CommentsPresenter commentsPresenter;
    public static final String EXTRA_ITEM_COMMENT = "ITEM_COMMENT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
