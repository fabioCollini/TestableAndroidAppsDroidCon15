package it.cosenonjaviste.testableandroidapps.v3;

import java.util.List;

import javax.inject.Inject;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;

public class PostBatch {

    private WordPressService wordPressService;

    private EmailSender emailSender;

    @Inject public PostBatch(WordPressService wordPressService, EmailSender emailSender) {
        this.wordPressService = wordPressService;
        this.emailSender = emailSender;
    }

    public void execute() {
        PostResponse postResponse = wordPressService.listPosts();

        List<Post> posts = postResponse.getPosts();
        for (Post post : posts) {
            emailSender.sendEmail(post);
        }
    }
}
