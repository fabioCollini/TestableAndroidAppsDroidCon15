package it.cosenonjaviste.testableandroidapps.v3;

import android.app.Application;

import it.cosenonjaviste.testableandroidapps.v7.Dagger_ApplicationComponent;

public class CnjApplication extends Application {

    public static ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = Dagger_ApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
