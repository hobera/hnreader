package com.hobera.app.hnreader;

import com.hobera.app.hnreader.data.ItemRemoteDataSource;
import com.hobera.app.hnreader.data.source.ItemRepository;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class Injection {
    public static ItemRepository provideItemRepository() {
        return ItemRepository.getInstance(ItemRemoteDataSource.getInstance());
    }
}
