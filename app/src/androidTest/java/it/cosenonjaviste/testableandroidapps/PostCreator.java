package it.cosenonjaviste.testableandroidapps;

import java.util.Date;

import it.cosenonjaviste.testableandroidapps.model.Author;
import it.cosenonjaviste.testableandroidapps.model.Post;

public class PostCreator {
    public static Post createPost(int id) {
        return new Post(id, new Author(id, "name " + id, "last name " + id), "title " + id, new Date(), "url" + id, "excerpt " + id);
    }
}
