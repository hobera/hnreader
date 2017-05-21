package com.hobera.app.hnreader.topstories;

import com.hobera.app.hnreader.data.Item;
import com.hobera.app.hnreader.data.source.ItemDataSource.GetItemListCallback;
import com.hobera.app.hnreader.data.source.ItemDataSource.GetItemCallback;
import com.hobera.app.hnreader.data.source.ItemRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Hernan Obera on 5/21/2017.
 */

public class TopStoriesPresenterTest {
    private static ArrayList<Item> ITEMS;
    private static Item updatedItem;
    private static long itemId;
    private static int itemRank;

    @Mock
    private ItemRepository mItemRepository;

    @Mock
    private TopStoriesContract.View mTopStoriesView;

    @Captor
    private ArgumentCaptor<GetItemListCallback> mGetItemListCallbackCaptor;

    @Captor
    private ArgumentCaptor<GetItemCallback> mGetItemCallbackCaptor;

    private TopStoriesPresenter mTopStoriesPresenter;

    @Before
    public void setupTopStoriesPresenter() {
        MockitoAnnotations.initMocks(this);

        mTopStoriesPresenter = new TopStoriesPresenter(mItemRepository, mTopStoriesView);

        // check presenter if active
        when(mTopStoriesView.isActive()).thenReturn(true);

        itemId = 20000001;
        itemRank = 0;
        ITEMS = new ArrayList<Item>();
        ITEMS.add(new Item(itemId, itemRank, "story"));
        ITEMS.add(new Item(20000002, 1, "story"));
        ITEMS.add(new Item(20000003, 2, "story"));

        updatedItem = new Item(itemId, itemRank, "author01", 0, null, 80, 1495092532, "title 01",
                "story", "url.com", "10000001", "", false);
    }


    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        mTopStoriesPresenter = new TopStoriesPresenter(mItemRepository, mTopStoriesView);

        // Then the presenter is set to the view
        verify(mTopStoriesView).setPresenter(mTopStoriesPresenter);
    }

    @Test
    public void loadTopStoriesFromRepositoryAndLoadIntoView() {
        mTopStoriesPresenter.loadTopStories(true);

        // Callback is captured and invoked with stubbed items
        verify(mItemRepository).getItemList(mGetItemListCallbackCaptor.capture());
        mGetItemListCallbackCaptor.getValue().onItemListLoaded(ITEMS);

        // Then progress indicator is shown
        InOrder inOrder = inOrder(mTopStoriesView);
        inOrder.verify(mTopStoriesView).setLoadingIndicator(true);
        // Then progress indicator is hidden and all items are shown in UI
        inOrder.verify(mTopStoriesView).setLoadingIndicator(false);
        ArgumentCaptor<ArrayList> showItemsArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(mTopStoriesView).showTopStoryList(showItemsArgumentCaptor.capture());
        assertTrue(showItemsArgumentCaptor.getValue().size() == 3);
    }

    @Test
    public void loadItemFromRepositoryAndUpdateIntoView() {
        mTopStoriesPresenter.loadItem(itemId);

        // Callback is captured and invoked with stubbed item
        verify(mItemRepository).getItem(eq(itemId), mGetItemCallbackCaptor.capture());
        mGetItemCallbackCaptor.getValue().onItemLoaded(updatedItem);

        ArgumentCaptor<Item> showItemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(mTopStoriesView).showUpdatedItem(eq(itemRank), showItemArgumentCaptor.capture());
        assertTrue(showItemArgumentCaptor.getValue().equals(updatedItem));
    }

    @Test
    public void noItems_ShowsNoStoryList() {
        // When top stories are loaded
        mTopStoriesPresenter.loadTopStories(true);

        // And no items are returned from the repository
        verify(mItemRepository).getItemList(mGetItemListCallbackCaptor.capture());
        mGetItemListCallbackCaptor.getValue().onItemListLoaded(new ArrayList<Item>());

        // Then no value list is shown
        verify(mTopStoriesView).showNoTopStoryList();
    }

    @Test
    public void unavailableItems_ShowsError() {
        // When top stories are loaded
        mTopStoriesPresenter.loadTopStories(true);

        // And the items aren't available in the repository
        verify(mItemRepository).getItemList(mGetItemListCallbackCaptor.capture());
        mGetItemListCallbackCaptor.getValue().onDataNotAvailable();

        // Then an error message is shown
        verify(mTopStoriesView).showLoadingError();
    }

    @Test
    public void clickOnItem_ShowsComment() {
        // Given a stubbed story item
        Item selectedItem = new Item(20000004, 1, "author04", 0, null, 80, 1495092532, "title " +
                "01", "story", "url.com", "10000004", "", false);

        // When open item is requested
        mTopStoriesPresenter.openItemComments(selectedItem);

        // Then passed Item is shown
        ArgumentCaptor<Item> showItemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(mTopStoriesView).showItemComments(showItemArgumentCaptor.capture());
        assertTrue(showItemArgumentCaptor.getValue().equals(selectedItem));
    }

}
