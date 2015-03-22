package it.cosenonjaviste.testableandroidapps.v1;

import com.google.gson.GsonBuilder;

import java.util.List;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class PostBatch {

    public void execute() {
        PostResponse postResponse = createService().listPosts();
        EmailSender emailSender = new EmailSender();

        List<Post> posts = postResponse.getPosts();
        for (Post post : posts) {
            emailSender.sendEmail(post);
        }
    }

    private static WordPressService createService() {
        //...
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.cosenonjaviste.it/")
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .build();
        return restAdapter.create(WordPressService.class);
    }
}
