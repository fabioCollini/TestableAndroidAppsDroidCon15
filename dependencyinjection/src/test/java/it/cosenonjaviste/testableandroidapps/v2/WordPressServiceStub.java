package it.cosenonjaviste.testableandroidapps.v2;

import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;

public class WordPressServiceStub implements WordPressService {

    private PostResponse postResponse;

    public WordPressServiceStub(PostResponse postResponse) {
        this.postResponse = postResponse;
    }

    @Override public PostResponse listPosts() {
        return postResponse;
    }
}
