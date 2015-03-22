package it.cosenonjaviste.testableandroidapps.v2;

import java.util.List;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;

public class PostBatch {

    private WordPressService wordPressService;

    private EmailSender emailSender;

    public PostBatch(WordPressService wordPressService, EmailSender emailSender) {
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
