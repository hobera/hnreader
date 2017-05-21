package com.hobera.app.hnreader;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.hobera.app.hnreader.comments.CommentsActivity;
import com.hobera.app.hnreader.data.FakeItemDataSource;
import com.hobera.app.hnreader.data.Item;
import com.hobera.app.hnreader.data.source.ItemRepository;
import com.hobera.app.hnreader.util.AppUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TopStoriesMockInstrumentedTest {

    private static String STORY_TITLE = "title 01";
    private static String STORY_METADATA; // 80 points by author01 XX ago | 2 comments

    private static Item story = new Item(10000001, 0, "author01", 2, new long[]{2000001,2000002},
            80, 1495092532, STORY_TITLE, "story", "url.com", "10000001", "", false);

    @Rule
    public ActivityTestRule<CommentsActivity> mCommentActivityTestRule =
            new ActivityTestRule<>(CommentsActivity.class, true /* Initial touch mode  */,
                    false /* Lazily launch activity */);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.hobera.app.hnreader.mock", appContext.getPackageName());
    }

    private void loadCommentScreen() {
        startCommentActivityWithStubbedStory(story);
    }

    private void startCommentActivityWithStubbedStory(Item story) {
        // Add an item stub to the fake service api layer.
        ItemRepository.destroyInstance();
        FakeItemDataSource.getInstance().addItems(story);

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        STORY_METADATA = AppUtils.getDisplayMetaData(appContext, story.getScore(),
                story.getBy(), story.getTime(), story.getKids());

        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent startIntent = new Intent();
        startIntent.putExtra(CommentsActivity.EXTRA_ITEM_COMMENT, story);
        mCommentActivityTestRule.launchActivity(startIntent);
    }

    @Test
    public void ItemDetails_DisplayedInUi() throws Exception {
        loadCommentScreen();

        // Check that the story title and metadata are displayed
        onView(withId(R.id.item_title)).check(matches(withText(STORY_TITLE)));
        onView(withId(R.id.item_score)).check(matches(withText(STORY_METADATA)));
    }
}
