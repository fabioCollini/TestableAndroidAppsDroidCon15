package it.cosenonjaviste.testableandroidapps.v1;

import android.content.Intent;
import android.support.test.espresso.action.ViewActions;

import org.junit.Rule;
import org.junit.Test;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.utils.ActivityRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static it.cosenonjaviste.testableandroidapps.utils.ErrorTextMatcher.hasErrorText;

public class ShareActivityTest {
    @Rule
    public final ActivityRule<ShareActivity> rule = new ActivityRule<ShareActivity>(ShareActivity.class) {
        @Override protected Intent getLaunchIntent(String targetPackage, Class<ShareActivity> activityClass) {
            Intent intent = super.getLaunchIntent(targetPackage, activityClass);
            ShareActivity.populateIntent(intent, "title", "body");
            return intent;
        }
    };

    @Test public void checkParameters() {
        onView(withId(R.id.share_title)).check(matches(withText("title")));
        onView(withId(R.id.share_body)).check(matches(withText("body")));
    }

    @Test public void clickOnShare() {
        onView(withId(R.id.share_button)).perform(click());
    }

    @Test public void checkValidationWhenEmpty() {
        onView(withId(R.id.share_title)).perform(ViewActions.clearText());
        onView(withId(R.id.share_body)).perform(ViewActions.clearText());

        onView(withId(R.id.share_button)).perform(click());

        String errorText = rule.get().getString(R.string.mandatory_field);

        onView(withId(R.id.share_title)).check(matches(hasErrorText(errorText)));
        onView(withId(R.id.share_body)).check(matches(hasErrorText(errorText)));
    }
}
