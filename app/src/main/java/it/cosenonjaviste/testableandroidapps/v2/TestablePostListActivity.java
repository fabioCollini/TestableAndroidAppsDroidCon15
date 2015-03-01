package it.cosenonjaviste.testableandroidapps.v2;

import java.util.Date;
import java.util.List;

import it.cosenonjaviste.testableandroidapps.model.Author;
import it.cosenonjaviste.testableandroidapps.model.Post;
import rx.Observable;

public class TestablePostListActivity extends PostListActivity {

    @Override protected Observable<List<Post>> createListObservable() {
        return Observable.just(
                createPost(1),
                createPost(2),
                createPost(3)
        ).toList();
    }

    private Post createPost(int id) {
        return new Post(id, new Author(id, "name " + id, "last name " + id), "title " + id, new Date(), "url" + id, "excerpt " + id);
    }
}
