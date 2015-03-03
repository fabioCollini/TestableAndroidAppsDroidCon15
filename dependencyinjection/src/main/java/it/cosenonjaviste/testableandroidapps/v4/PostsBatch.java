package it.cosenonjaviste.testableandroidapps.v4;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;

public class PostsBatch {

    @Inject WordPressService wordPressService;

    @Inject EmailSender emailSender;

    @Inject public PostsBatch() {
    }

    public void execute() {
        PostResponse postResponse = wordPressService.listPosts();

        List<Post> posts = postResponse.getPosts();
        for (Post post : posts) {
            emailSender.sendEmail(post);
        }
    }

    public static void main(String[] args) {
        PostsBatch batch = Dagger_MainComponent.create().getBatch();
        batch.execute();
    }
}
