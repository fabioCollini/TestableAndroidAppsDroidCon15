package it.cosenonjaviste.testableandroidapps.v6;

import android.content.Intent;
import android.support.test.espresso.action.ViewActions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import javax.inject.Inject;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.ShareExecutor;
import it.cosenonjaviste.testableandroidapps.utils.ActivityRule;
import it.cosenonjaviste.testableandroidapps.utils.DaggerRule;

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

    private final ActivityRule<ShareActivity> activityRule = new ActivityRule<ShareActivity>(ShareActivity.class) {
        @Override protected Intent getLaunchIntent(String targetPackage, Class<ShareActivity> activityClass) {
            Intent intent = super.getLaunchIntent(targetPackage, activityClass);
            ShareActivity.populateIntent(intent, new ShareModel("title", "body"));
            return intent;
        }
    };

    private final DaggerRule<TestComponent> daggerRule = new DaggerRule<>(Dagger_TestComponent.create(), component -> {
        CnjApplication.component = component;
        component.inject(this);
    });

    @Rule public TestRule chain = RuleChain.outerRule(daggerRule).around(activityRule);

    @Test public void checkParameters() {
        onView(withId(R.id.share_title)).check(matches(withText("title")));
        onView(withId(R.id.share_body)).check(matches(withText("body")));
    }

    @Test public void clickOnShare() {
        onView(withId(R.id.share_button)).perform(click());

        verify(shareExecutor).startSendActivity(eq("title"), eq("body"));
    }

    @Test public void checkValidationWhenEmpty() {
        onView(withId(R.id.share_title)).perform(ViewActions.clearText());
        onView(withId(R.id.share_body)).perform(ViewActions.clearText());

        onView(withId(R.id.share_button)).perform(click());

        String errorText = activityRule.get().getString(R.string.mandatory_field);

        onView(withId(R.id.share_title)).check(matches(hasErrorText(errorText)));
        onView(withId(R.id.share_body)).check(matches(hasErrorText(errorText)));
    }
}
