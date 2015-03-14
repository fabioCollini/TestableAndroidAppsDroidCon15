package it.cosenonjaviste.testableandroidapps.lib;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BindItem {
    int value();
}
