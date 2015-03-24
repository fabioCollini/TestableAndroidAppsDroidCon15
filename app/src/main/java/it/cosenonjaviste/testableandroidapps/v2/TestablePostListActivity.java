package it.cosenonjaviste.testableandroidapps.v2;

import java.util.List;

import it.cosenonjaviste.testableandroidapps.model.Post;
import rx.Observable;

public class TestablePostListActivity extends PostListActivity {

    public static Observable<List<Post>> result;

    @Override protected Observable<List<Post>> createListObservable() {
        return result;
    }
}
