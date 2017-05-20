package com.hobera.app.hnreader.data;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.hobera.app.hnreader.data.source.ItemDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class FakeItemDataSource implements ItemDataSource {

    private static FakeItemDataSource mInstance;

    private static final Map<String, Item> ITEM_DATA = new LinkedHashMap<>();
    private static final int RESPONSE_LATENCY_IN_MILLIS = 1000;

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
        for (int i=0; i<500; i++) {
            long itemId = 1234570+i;
            ITEM_DATA.put(String.valueOf(itemId), new Item(itemId, i+1, Item.STORY));
        }

        callback.onItemListLoaded(new ArrayList<Item>(ITEM_DATA.values()));
    }

    @Override
    public void getItem(@NonNull long itemId, @NonNull final GetItemCallback callback) {
        String parent = "";
        Item updatedItem = ITEM_DATA.get(String.valueOf(itemId));

        if (updatedItem == null) {
            callback.onDataNotAvailable();
            return;
        }

        if (updatedItem.getType() == Item.STORY) {
            long[] kids = {200001,200002};
            long timeMillis = (System.currentTimeMillis()-3600000*2)/1000; // 2h ago
            updatedItem.populate("abc", 2, kids, 50, timeMillis,
                    String.format("Story %d &quot;This is a title for a story&quot;", itemId)
                    ,"story", "https://news.ycombinator.com",  parent, "", false);
        }

        final Item finalUpdatedItem = updatedItem;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onItemLoaded(finalUpdatedItem);
            }
        }, RESPONSE_LATENCY_IN_MILLIS);
    }
}
