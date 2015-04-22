package it.cosenonjaviste.testableandroidapps.v1;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import it.cosenonjaviste.testableandroidapps.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class PostListActivityTest {
    @Rule public final ActivityTestRule<PostListActivity> rule = new ActivityTestRule<>(PostListActivity.class);

    @Test public void showListActivity() {
        onView(withText("???")).check(matches(isDisplayed()));
    }

    @Test public void showErrorLayoutOnServerError() {
        //???
        onView(withId(R.id.error_layout)).check(matches(isDisplayed()));
    }
}
