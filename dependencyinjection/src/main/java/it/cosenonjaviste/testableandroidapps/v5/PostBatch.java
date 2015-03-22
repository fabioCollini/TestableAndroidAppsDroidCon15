package it.cosenonjaviste.testableandroidapps.v5;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;

public class PostBatch {

    private WordPressService service;

    private EmailSender sender;

    private Executor executor;

    @Inject public PostBatch(WordPressService service, EmailSender sender, Executor executor) {
        this.service = service;
        this.sender = sender;
        this.executor = executor;
    }

    public void execute() {
        PostResponse postResponse = service.listPosts();

        List<Post> posts = postResponse.getPosts();
        for (Post post : posts) {
            executor.execute(new Runnable() {
                @Override public void run() {
                    sender.sendEmail(post);
                }
            });
        }
    }
}
