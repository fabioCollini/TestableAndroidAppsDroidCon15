package it.cosenonjaviste.testableandroidapps.v2;

import com.google.gson.GsonBuilder;

import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class Main {

    public static void main(String[] args) {
        new PostBatch(createService(), new EmailSender()).execute();
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
