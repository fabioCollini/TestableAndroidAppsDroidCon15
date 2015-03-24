package it.cosenonjaviste.testableandroidapps.v2;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import it.cosenonjaviste.testableandroidapps.PostCreator;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.utils.ActivityRule;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class PostListActivityTest {
    @Rule public final ActivityRule<TestablePostListActivity> rule = new ActivityRule<>(TestablePostListActivity.class, false);

    @Test public void showListActivity() {
        TestablePostListActivity.result = Observable.just(
                PostCreator.createPost(1),
                PostCreator.createPost(2),
                PostCreator.createPost(3)
        ).toList();

        rule.launchActivity();

        onView(withText("title 1")).check(matches(isDisplayed()));
    }

    @Test public void checkErrorLayoutDisplayed() {
        TestablePostListActivity.result = Observable.error(new IOException());

        rule.launchActivity();

        onView(withId(R.id.error_layout)).check(matches(isDisplayed()));
    }
}
