package it.cosenonjaviste.testableandroidapps.v3;

import com.google.gson.GsonBuilder;

import java.util.List;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class PostsBatch {

    public void execute() {
        PostResponse postResponse = createService().listPosts();

        List<Post> posts = postResponse.getPosts();
        for (Post post : posts) {
            sendEmail(post);
        }
    }

    private void sendEmail(Post p) {
        System.out.println("email " + p.getTitle() + " sent!");
    }

    private static WordPressService createService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.cosenonjaviste.it/")
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .build();
        return restAdapter.create(WordPressService.class);
    }

    public static void main(String[] args) {
        new PostsBatch().execute();
    }
}
