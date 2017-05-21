package com.hobera.app.hnreader.data;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.hobera.app.hnreader.data.source.ItemDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import timber.log.Timber;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class ItemRemoteDataSource implements ItemDataSource {
    private static ItemRemoteDataSource mInstance;

    private static final Map<String, Item> ITEM_DATA = new LinkedHashMap<>();
    private static final Map<String, Item> COMMENT_DATA = new LinkedHashMap<>();

    static final String BASE_API_URL = "https://hacker-news.firebaseio.com/v0/";
    private final RestService mRestService;
    private GetItemListCallback mGetItemListCallback;
    private GetItemCallback mGetItemCallback;

    public ItemRemoteDataSource() {
        mRestService = new Retrofit.Builder().baseUrl(BASE_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RestService.class);
    }

    public static ItemRemoteDataSource getInstance() {
        if (mInstance == null) {
            mInstance = new ItemRemoteDataSource();
        }
        return mInstance;
    }

    @Override
    public void getItemList(@NonNull GetItemListCallback callback) {
        if (callback != null) {
            mGetItemListCallback = callback;

            Observable.defer(() -> mRestService.topStoriesRx())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::onGetItemListResponse, this::onGetItemListError);
        }
    }

    @Override
    public void getItem(@NonNull long itemId, @NonNull GetItemCallback callback) {
        if (callback != null) {
            mGetItemCallback = callback;

            Observable.defer(() -> mRestService.itemRx(String.valueOf(itemId)))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::onGetItemResponse, this::onGetItemError);
        }
    }

    @Override
    public void getCommentList(Item item, @NonNull GetItemListCallback callback) {
        COMMENT_DATA.clear();

        if (item == null) {
            mGetItemListCallback.onDataNotAvailable();
            return;
        }

        long[] itemIds = item.getKids();

        if (callback != null) {
            mGetItemListCallback = callback;

            if (itemIds == null) {
                mGetItemListCallback.onDataNotAvailable();
            }

            int rank = 0;
            for (long itemId : itemIds) {
                COMMENT_DATA.put(String.valueOf(itemId), new Item(itemId, rank++, Item.COMMENT));
            }

            mGetItemListCallback.onItemListLoaded(new ArrayList<Item>(COMMENT_DATA.values()));
        }
    }

    private void onGetItemListResponse(int[] itemIds) {
        if (itemIds == null) {
            mGetItemListCallback.onDataNotAvailable();
        }

        int rank = 0;
        for (int id : itemIds) {
            ITEM_DATA.put(String.valueOf(id), new Item(id, rank++, Item.STORY));
        }

        mGetItemListCallback.onItemListLoaded(new ArrayList<Item>(ITEM_DATA.values()));
    }

    private void onGetItemListError(Throwable error) {
        Timber.d(error.getLocalizedMessage());
        mGetItemListCallback.onDataNotAvailable();
    }

    private void onGetItemResponse(Item item) {
        if (item == null) {
            mGetItemCallback.onDataNotAvailable();
            return;
        }

        Item updatedItem = null;
        switch (item.getType()) {
            case Item.STORY:
                updatedItem = ITEM_DATA.get(String.valueOf(item.getId()));
                break;
            case Item.COMMENT:
                updatedItem = COMMENT_DATA.get(String.valueOf(item.getId()));
                if (updatedItem==null) {
                    // reply
                    updatedItem = new Item(item.getId(), 0, Item.COMMENT);
                    COMMENT_DATA.put(String.valueOf(item.getId()),item);
                }
                break;
            default:
                break;
        }

        if (updatedItem != null) {
            updatedItem.populate(
                    item.getBy(),
                    item.getDescendants(),
                    item.getKids(),
                    item.getScore(),
                    item.getTime(),
                    item.getTitle(),
                    item.getType(),
                    item.getUrl(),
                    item.getParent(),
                    item.getText(),
                    item.isDeleted());

            final Item finalUpdatedItem = updatedItem;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mGetItemCallback.onItemLoaded(finalUpdatedItem);
                }
            });
        }
    }

    private void onGetItemError(Throwable error) {
        Timber.d(error.getLocalizedMessage());
        mGetItemCallback.onDataNotAvailable();
    }

    public interface RestService {
        @GET("topstories.json")
        Observable<int[]> topStoriesRx();

        @GET("item/{itemId}.json")
        Observable<Item> itemRx(@Path("itemId") String itemId);
    }
}
