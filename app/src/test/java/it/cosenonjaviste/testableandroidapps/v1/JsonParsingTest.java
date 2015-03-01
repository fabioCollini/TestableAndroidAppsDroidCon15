package it.cosenonjaviste.testableandroidapps.v1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.utils.FileUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JsonParsingTest {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Test public void parsePostsJson() throws IOException {
        PostResponse postResponse = gson.fromJson(FileUtils.readFile("post_response.json"), PostResponse.class);
        List<Post> posts = postResponse.getPosts();
        assertEquals(2, posts.size());
        Post post = posts.get(0);
        assertEquals(12046, post.getId());
        assertNotNull(post.getDate());
        assertNotNull(post.getTitle());
        assertNotNull(post.getUrl());
        assertNotNull(post.getAuthor());
        assertEquals(33, post.getAuthor().getId());
        assertNotNull(post.getAuthor().getName());
    }
}
