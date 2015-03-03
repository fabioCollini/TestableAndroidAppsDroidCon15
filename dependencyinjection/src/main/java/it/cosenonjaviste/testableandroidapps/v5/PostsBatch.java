package it.cosenonjaviste.testableandroidapps.v5;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;

public class PostsBatch {

    @Inject WordPressService wordPressService;

    @Inject EmailSender emailSender;

    @Inject Executor executor;

    @Inject public PostsBatch() {
    }

    public void execute() {
        PostResponse postResponse = wordPressService.listPosts();

        List<Post> posts = postResponse.getPosts();
        for (Post post : posts) {
            executor.execute(new Runnable() {
                @Override public void run() {
                    emailSender.sendEmail(post);
                }
            });
        }
    }

    public static void main(String[] args) {
        PostsBatch batch = Dagger_MainComponent.create().getBatch();
        batch.execute();
    }
}
