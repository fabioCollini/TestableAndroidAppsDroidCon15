package it.cosenonjaviste.testableandroidapps.v3;

import java.util.List;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;

public class PostsBatch {

    private WordPressService wordPressService;

    private EmailSender emailSender;

    public PostsBatch(WordPressService wordPressService, EmailSender emailSender) {
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

    public static void main(String[] args) {
        PostsBatch batch = Dagger_MainComponent.create().getBatch();
        batch.execute();
    }
}
