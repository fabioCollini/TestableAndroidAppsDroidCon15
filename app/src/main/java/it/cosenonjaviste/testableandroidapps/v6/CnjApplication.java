package it.cosenonjaviste.testableandroidapps.v6;

import android.app.Application;

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
