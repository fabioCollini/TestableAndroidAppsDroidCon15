package it.cosenonjaviste.testableandroidapps.v3;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import it.cosenonjaviste.testableandroidapps.CnjApplication;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.ShareExecutor;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static it.cosenonjaviste.testableandroidapps.utils.ErrorTextMatcher.hasErrorText;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class ShareActivityTest {

    @Inject ShareExecutor shareExecutor;

    @Rule
    public final ActivityTestRule<ShareActivity> rule = new ActivityTestRule<ShareActivity>(ShareActivity.class, false, false) {
        @Override protected Intent getActivityIntent() {
            Intent intent = super.getActivityIntent();
            ShareActivity.populateIntent(intent, "title", "body");
            return intent;
        }
    };

    @Before
    public void setUp() {
        TestComponent component = DaggerTestComponent.create();
        CnjApplication application = (CnjApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
        application.setComponent(component);
        component.inject(this);
    }

    @Test public void checkParameters() {
        rule.launchActivity(null);

        onView(withId(R.id.share_title)).check(matches(withText("title")));
        onView(withId(R.id.share_body)).check(matches(withText("body")));
    }

    @Test public void clickOnShare() {
        rule.launchActivity(null);

        onView(withId(R.id.share_button)).perform(click());

        verify(shareExecutor).startSendActivity(eq("title"), eq("body"));
    }

    @Test public void checkValidationWhenEmpty() {
        rule.launchActivity(null);

        onView(withId(R.id.share_title)).perform(ViewActions.clearText());
        onView(withId(R.id.share_body)).perform(ViewActions.clearText());

        onView(withId(R.id.share_button)).perform(click());

        String errorText = rule.getActivity().getString(R.string.mandatory_field);

        onView(withId(R.id.share_title)).check(matches(hasErrorText(errorText)));
        onView(withId(R.id.share_body)).check(matches(hasErrorText(errorText)));
    }
}
