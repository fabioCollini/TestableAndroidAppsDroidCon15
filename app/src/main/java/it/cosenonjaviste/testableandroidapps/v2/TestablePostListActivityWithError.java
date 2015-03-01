package it.cosenonjaviste.testableandroidapps.v2;

import java.io.IOException;
import java.util.List;

import it.cosenonjaviste.testableandroidapps.model.Post;
import rx.Observable;

public class TestablePostListActivityWithError extends PostListActivity {

    @Override protected Observable<List<Post>> createListObservable() {
        return Observable.error(new IOException());
    }
}
