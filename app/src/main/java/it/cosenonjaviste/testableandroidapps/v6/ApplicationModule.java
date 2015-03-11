package it.cosenonjaviste.testableandroidapps.v6;

import android.app.Application;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import it.cosenonjaviste.testableandroidapps.AndroidShareExecutor;
import it.cosenonjaviste.testableandroidapps.BuildConfig;
import it.cosenonjaviste.testableandroidapps.ShareExecutor;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton ShareExecutor provideShareExecutor() {
        return new AndroidShareExecutor(application);
    }

    @Provides @Singleton WordPressService providesWordPressService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.cosenonjaviste.it/")
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .build();
        if (BuildConfig.DEBUG) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return restAdapter.create(WordPressService.class);
    }

    @Provides PostListPresenter providesPresenter(WordPressService wordPressService) {
        return new PostListPresenter(wordPressService, Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
