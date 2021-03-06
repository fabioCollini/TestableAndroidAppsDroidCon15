package it.cosenonjaviste.testableandroidapps.v3;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module
public class MainModule {
    @Provides @Singleton EmailSender provideEmailSender() {
        return new EmailSender();
    }

    @Provides @Singleton WordPressService provideWordPressService() {
        //...
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.cosenonjaviste.it/")
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .build();
        return restAdapter.create(WordPressService.class);
    }

    @Provides PostBatch providePostsBatch(WordPressService worpressService, EmailSender emailSender) {
        return new PostBatch(worpressService, emailSender);
    }
}
