package it.cosenonjaviste.testableandroidapps.v1;

import org.junit.Rule;
import org.junit.Test;

import it.cosenonjaviste.testableandroidapps.utils.ActivityRule;

public class PostListActivityTest {
    @Rule
    public final ActivityRule<PostListActivity> rule = new ActivityRule<>(PostListActivity.class);

    @Test public void showListActivity() {
    }
}
