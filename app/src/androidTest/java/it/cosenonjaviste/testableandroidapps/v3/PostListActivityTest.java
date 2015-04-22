package it.cosenonjaviste.testableandroidapps.v3;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import javax.inject.Inject;

import it.cosenonjaviste.testableandroidapps.CnjApplication;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import rx.Observable;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static it.cosenonjaviste.testableandroidapps.PostCreator.createPost;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class PostListActivityTest {

    @Inject WordPressService wordPressService;

    @Rule public final ActivityTestRule<PostListActivity> rule = new ActivityTestRule<>(PostListActivity.class, false, false);

    @Before
    public void setUp() {
        TestComponent component = DaggerTestComponent.create();
        CnjApplication application = (CnjApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
        application.setComponent(component);
        component.inject(this);
    }

    @Test public void showListActivity() {
        when(wordPressService.listPosts())
                .thenReturn(Observable.just(new PostResponse(createPost(1), createPost(2), createPost(3))));

        rule.launchActivity(null);

        onView(withText("title 1")).check(matches(isDisplayed()));

        onData(is(instanceOf(Post.class))).atPosition(1).perform(click());
    }

    @Test public void showErrorLayoutOnServerError() {
        when(wordPressService.listPosts())
                .thenReturn(Observable.error(new IOException("error!")));

        rule.launchActivity(null);

        onView(withId(R.id.error_layout)).check(matches(isDisplayed()));
    }
}
