package it.cosenonjaviste.testableandroidapps;

import rx.Observable;
import rx.Scheduler;

public class SchedulerManager {

    private Observable.Transformer transformer;

    public SchedulerManager(Scheduler io, Scheduler mainThread) {
        transformer = new Observable.Transformer<Object, Object>() {
            @Override public Observable call(Observable observable) {
                return observable.subscribeOn(io).observeOn(mainThread);
            }
        };
    }

    public <T> Observable.Transformer<T, T> schedule() {
        return transformer;
    }
}
