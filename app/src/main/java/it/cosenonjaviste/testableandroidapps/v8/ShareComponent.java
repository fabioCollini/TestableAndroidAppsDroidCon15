package it.cosenonjaviste.testableandroidapps.v8;

import dagger.Component;
import it.cosenonjaviste.testableandroidapps.MvpScope;

@MvpScope
@Component(dependencies = ApplicationComponent.class)
public interface ShareComponent {
    void inject(ShareActivity activity);
}
