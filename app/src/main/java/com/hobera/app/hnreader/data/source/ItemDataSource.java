package com.hobera.app.hnreader.data.source;

import android.support.annotation.NonNull;

import com.hobera.app.hnreader.data.Item;

import java.util.ArrayList;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public interface ItemDataSource {
    interface GetItemListCallback {

        void onItemListLoaded(ArrayList<Item> itemList);

        void onDataNotAvailable();
    }

    interface GetItemCallback {

        void onItemLoaded(Item item);

        void onDataNotAvailable();
    }

    void getItemList(@NonNull GetItemListCallback callback);

    void getItem(@NonNull long itemId, @NonNull GetItemCallback callback);

}
