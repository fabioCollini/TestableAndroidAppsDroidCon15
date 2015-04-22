package it.cosenonjaviste.testableandroidapps.v2;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import it.cosenonjaviste.testableandroidapps.PostCreator;
import it.cosenonjaviste.testableandroidapps.R;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class PostListActivityTest {
    @Rule public final ActivityTestRule<TestablePostListActivity> rule = new ActivityTestRule<>(TestablePostListActivity.class, false, false);

    @Test public void showListActivity() {
        TestablePostListActivity.result = Observable.just(
                PostCreator.createPost(1),
                PostCreator.createPost(2),
                PostCreator.createPost(3)
        ).toList();

        rule.launchActivity(null);

        onView(withText("title 1")).check(matches(isDisplayed()));
    }

    @Test public void checkErrorLayoutDisplayed() {
        TestablePostListActivity.result = Observable.error(new IOException());

        rule.launchActivity(null);

        onView(withId(R.id.error_layout)).check(matches(isDisplayed()));
    }
}
