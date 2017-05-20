package com.hobera.app.hnreader.data;

import android.support.annotation.NonNull;

import com.hobera.app.hnreader.data.source.ItemDataSource;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class ItemRemoteDataSource implements ItemDataSource {
    private static ItemRemoteDataSource mInstance;

    public ItemRemoteDataSource() {

    }

    public static ItemRemoteDataSource getInstance() {
        if (mInstance == null) {
            mInstance = new ItemRemoteDataSource();
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
