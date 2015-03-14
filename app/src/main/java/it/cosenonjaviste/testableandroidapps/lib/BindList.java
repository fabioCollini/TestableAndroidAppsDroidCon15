package it.cosenonjaviste.testableandroidapps.lib;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BindList {
    int value();

    int layoutId();
}
