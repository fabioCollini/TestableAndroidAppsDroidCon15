package it.cosenonjaviste.testableandroidapps.v2;

import org.junit.Rule;
import org.junit.Test;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.utils.ActivityRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PostListActivityWithErrorTest {
    @Rule
    public final ActivityRule<TestablePostListActivityWithError> rule = new ActivityRule<>(TestablePostListActivityWithError.class);

    @Test public void checkErrorLayoutDisplayed() {
        onView(withId(R.id.error_layout)).check(matches(isDisplayed()));
    }
}
