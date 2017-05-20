package com.hobera.app.hnreader.data.source;

import android.support.annotation.NonNull;

import com.hobera.app.hnreader.data.Item;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class ItemRepository implements ItemDataSource  {
    private static ItemRepository mInstance = null;
    private final ItemDataSource mItemDataSource;
    private boolean mForceUpdate = false;
    Map<String, Item> CACHED_DATA = new LinkedHashMap<>();

    public ItemRepository(ItemDataSource mItemDataSource) {
        this.mItemDataSource = mItemDataSource;
    }

    public static ItemRepository getInstance(ItemDataSource mItemDataSource) {
        if (mInstance == null) {
            mInstance = new ItemRepository(mItemDataSource);
        }
        return mInstance;
    }

    public void forceUpdate() {
        mForceUpdate = true;
    }

    @Override
    public void getItemList(@NonNull final GetItemListCallback callback) {
        checkNotNull(callback);

        if (CACHED_DATA != null && !mForceUpdate) {
            callback.onItemListLoaded(new ArrayList<>(CACHED_DATA.values()));
            return;
        }

        mItemDataSource.getItemList(new GetItemListCallback() {
            @Override
            public void onItemListLoaded(ArrayList<Item> itemList) {
                if (CACHED_DATA == null) {
                    CACHED_DATA = new LinkedHashMap<>();
                }
                CACHED_DATA.clear();
                for (Item item : itemList) {
                    CACHED_DATA.put(String.valueOf(item.getRank()-1), item);
                }
                mForceUpdate = false;

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
