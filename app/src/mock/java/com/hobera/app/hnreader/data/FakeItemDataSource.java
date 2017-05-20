package com.hobera.app.hnreader.data;

import android.support.annotation.NonNull;

import com.hobera.app.hnreader.data.source.ItemDataSource;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class FakeItemDataSource implements ItemDataSource {

    private static FakeItemDataSource mInstance;

    public FakeItemDataSource() {
    }

    public static FakeItemDataSource getInstance() {
        if (mInstance == null) {
            mInstance = new FakeItemDataSource();
        }
        return mInstance;
    }

    @Override
    public void getItemList(@NonNull GetItemListCallback callback) {

    }

    @Override
    public void getItem(@NonNull long itemId, @NonNull GetItemCallback callback) {

    }
}
