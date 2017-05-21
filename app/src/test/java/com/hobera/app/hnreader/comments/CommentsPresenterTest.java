package com.hobera.app.hnreader.comments;

import com.hobera.app.hnreader.data.Item;
import com.hobera.app.hnreader.data.source.ItemDataSource.GetItemCallback;
import com.hobera.app.hnreader.data.source.ItemDataSource.GetItemListCallback;
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

public class CommentsPresenterTest {
    private static ArrayList<Item> COMMENTS;
    private static Item commentItem;
    private static long commentId;
    private static int commentRank;
    private static Item replyItem;
    private static long replyId;

    @Mock
    private ItemRepository mItemRepository;

    @Mock
    private CommentsContract.View mCommentsView;

    @Captor
    private ArgumentCaptor<GetItemListCallback> mGetItemListCallbackCaptor;

    @Captor
    private ArgumentCaptor<GetItemCallback> mGetItemCallbackCaptor;

    private CommentsPresenter mCommentsPresenter;

    @Before
    public void setupCommentsPresenter() {
        MockitoAnnotations.initMocks(this);

        mCommentsPresenter = new CommentsPresenter(mItemRepository, mCommentsView);

        // check presenter if active
        when(mCommentsView.isActive()).thenReturn(true);

        commentId = 40000001;
        commentRank = 0;
        COMMENTS = new ArrayList<Item>();
        COMMENTS.add(new Item(commentId, commentRank, "comment"));

        replyId = 400000011;
        commentItem = new Item(commentId, commentRank, "comment author01", 1, new long[]{replyId},
                80, 1495092532, "", "comment", "", "10000001", "comment 01", false);

        replyItem = new Item(replyId, 1, "reply author01", 0, null, 80,
                1495092532, "title 01",
                "comment", "url.com", String.valueOf(commentId), "", false);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        mCommentsPresenter = new CommentsPresenter(mItemRepository, mCommentsView);

        // Then the presenter is set to the view
        verify(mCommentsView).setPresenter(mCommentsPresenter);
    }

    @Test
    public void loadCommentsFromRepositoryAndLoadIntoView() {
        mCommentsPresenter.loadComments(commentItem);

        // Callback is captured and invoked with stubbed items
        verify(mItemRepository).getCommentList(eq(commentItem), mGetItemListCallbackCaptor.capture());
        mGetItemListCallbackCaptor.getValue().onItemListLoaded(COMMENTS);

        ArgumentCaptor<ArrayList> showItemsArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(mCommentsView).showCommentList(showItemsArgumentCaptor.capture());
        assertTrue(showItemsArgumentCaptor.getValue().size() == 1);
    }

    @Test
    public void loadCommentItemFromRepositoryAndUpdateIntoView() {
        mCommentsPresenter.loadComment(commentId);

        // Callback is captured and invoked with stubbed item
        verify(mItemRepository).getItem(eq(commentId), mGetItemCallbackCaptor.capture());
        mGetItemCallbackCaptor.getValue().onItemLoaded(commentItem);

        ArgumentCaptor<Item> showItemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(mCommentsView).showUpdatedComment(eq(commentRank) , showItemArgumentCaptor.capture());
        assertTrue(showItemArgumentCaptor.getValue().equals(commentItem));
    }

    @Test
    public void noItems_ShowsNoComments() {
        // When comments are loaded
        mCommentsPresenter.loadComments(commentItem);

        // And no items are returned from the repository
        verify(mItemRepository).getCommentList(eq(commentItem), mGetItemListCallbackCaptor.capture());
        mGetItemListCallbackCaptor.getValue().onItemListLoaded(new ArrayList<Item>());

        // Then no value list is shown
        verify(mCommentsView).showNoComments();
    }

    @Test
    public void unavailableComments_ShowsError() {
        // When comments are loaded
        mCommentsPresenter.loadComments(commentItem);

        // And the items aren't available in the repository
        verify(mItemRepository).getCommentList(eq(commentItem), mGetItemListCallbackCaptor.capture());
        mGetItemListCallbackCaptor.getValue().onDataNotAvailable();

        // Then an error message is shown
        verify(mCommentsView).showLoadingError();
    }

    @Test
    public void clickOnComment_ShowsReply() {
        // When show reply is requested
        mCommentsPresenter.loadReply(commentItem.getKids()[0]);

        verify(mItemRepository).getItem(eq(replyId), mGetItemCallbackCaptor.capture());
        mGetItemCallbackCaptor.getValue().onItemLoaded(replyItem);

        // Then reply is shown
        ArgumentCaptor<Item> showItemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(mCommentsView).showReply(showItemArgumentCaptor.capture());
        assertTrue(showItemArgumentCaptor.getValue().equals(replyItem));
    }

}
