package com.hobera.app.hnreader.data.source;

import android.support.annotation.NonNull;

import com.hobera.app.hnreader.data.Item;

import java.util.ArrayList;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class ItemRepository implements ItemDataSource  {
    private static ItemRepository mInstance = null;

    private final ItemDataSource mItemDataSource;

    public ItemRepository(ItemDataSource mItemDataSource) {
        this.mItemDataSource = mItemDataSource;
    }

    public static ItemRepository getInstance(ItemDataSource mItemDataSource) {
        if (mInstance == null) {
            mInstance = new ItemRepository(mItemDataSource);
        }
        return mInstance;
    }

    @Override
    public void getItemList(@NonNull final GetItemListCallback callback) {
        checkNotNull(callback);

        mItemDataSource.getItemList(new GetItemListCallback() {
            @Override
            public void onItemListLoaded(ArrayList<Item> itemList) {
                callback.onItemListLoaded(itemList);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getItem(@NonNull long itemId, @NonNull final GetItemCallback callback) {
        checkNotNull(itemId);
        checkNotNull(callback);

        mItemDataSource.getItem(itemId, new GetItemCallback() {
            @Override
            public void onItemLoaded(Item item) {
                callback.onItemLoaded(item);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }
}
