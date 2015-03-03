package it.cosenonjaviste.testableandroidapps.v2;

import com.google.gson.GsonBuilder;

import java.util.List;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

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

    private static WordPressService createService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.cosenonjaviste.it/")
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .build();
        return restAdapter.create(WordPressService.class);
    }

    public static void main(String[] args) {
        new PostsBatch(createService(), new EmailSender()).execute();
    }
}
