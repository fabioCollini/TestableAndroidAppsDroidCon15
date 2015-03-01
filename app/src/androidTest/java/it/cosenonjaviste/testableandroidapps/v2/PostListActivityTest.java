package it.cosenonjaviste.testableandroidapps.v2;

import org.junit.Rule;
import org.junit.Test;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.utils.ActivityRule;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class PostListActivityTest {
    @Rule
    public final ActivityRule<TestablePostListActivity> rule = new ActivityRule<>(TestablePostListActivity.class);

    @Test public void showListActivity() {
        onView(withText("title 1")).check(matches(isDisplayed()));

        onData(is(instanceOf(Post.class))).atPosition(1).perform(click());
    }
}
