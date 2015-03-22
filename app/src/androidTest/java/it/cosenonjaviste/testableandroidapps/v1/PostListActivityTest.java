package it.cosenonjaviste.testableandroidapps.v1;

import org.junit.Rule;
import org.junit.Test;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.utils.ActivityRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class PostListActivityTest {
    // see https://gist.github.com/JakeWharton/1c2f2cadab2ddd97f9fb
    @Rule
    public final ActivityRule<PostListActivity> rule = new ActivityRule<>(PostListActivity.class);

    @Test public void showListActivity() {
        onView(withText("???")).check(matches(isDisplayed()));
    }

    @Test public void showErrorLayoutOnServerError() {
        //???
        onView(withId(R.id.error_layout)).check(matches(isDisplayed()));
    }
}
