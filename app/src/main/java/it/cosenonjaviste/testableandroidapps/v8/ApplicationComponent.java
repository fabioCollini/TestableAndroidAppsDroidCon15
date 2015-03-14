package it.cosenonjaviste.testableandroidapps.v8;

import javax.inject.Singleton;

import dagger.Component;
import it.cosenonjaviste.testableandroidapps.ShareExecutor;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    WordPressService getWordPressService();

    ShareExecutor getShareExecutor();
}
