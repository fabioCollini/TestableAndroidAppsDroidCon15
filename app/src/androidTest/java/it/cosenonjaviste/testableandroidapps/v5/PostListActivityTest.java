package it.cosenonjaviste.testableandroidapps.v5;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.util.Date;

import javax.inject.Inject;

import it.cosenonjaviste.testableandroidapps.model.Author;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import it.cosenonjaviste.testableandroidapps.utils.ActivityRule;
import it.cosenonjaviste.testableandroidapps.utils.DaggerRule;
import rx.Observable;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class PostListActivityTest {

    @Inject WordPressService wordPressService;

    private final ActivityRule<PostListActivity> activityRule = new ActivityRule<>(PostListActivity.class);

    private final DaggerRule<TestComponent> daggerRule = new DaggerRule<>(Dagger_TestComponent.create(), component -> {
        CnjApplication.component = component;
        component.inject(this);

        when(wordPressService.listPosts())
                .thenReturn(Observable.just(new PostResponse(createPost(1), createPost(2), createPost(3))));

    });

    @Rule public TestRule chain = RuleChain.outerRule(daggerRule).around(activityRule);

    @Test public void showListActivity() {
        onView(withText("title 1")).check(matches(isDisplayed()));

        onData(is(instanceOf(Post.class))).atPosition(1).perform(click());
    }

    private static Post createPost(int id) {
        return new Post(id, new Author(id, "name " + id, "last name " + id), "title " + id, new Date(), "url" + id, "excerpt " + id);
    }
}
