package it.cosenonjaviste.testableandroidapps.model;

import java.util.Arrays;
import java.util.List;

public class PostResponse {
    private List<Post> posts;

    public PostResponse() {
    }

    public PostResponse(Post... posts) {
        this.posts = Arrays.asList(posts);
    }

    public List<Post> getPosts() {
        return posts;
    }
}
