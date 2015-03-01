package it.cosenonjaviste.testableandroidapps.v4;

import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.v3.WordPressService;

public class WordPressServiceStub implements WordPressService {

    private PostResponse postResponse;

    public WordPressServiceStub(PostResponse postResponse) {
        this.postResponse = postResponse;
    }

    @Override public PostResponse listPosts() {
        return postResponse;
    }
}
