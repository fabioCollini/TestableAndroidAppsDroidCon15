package it.cosenonjaviste.testableandroidapps;

import rx.Observable;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.subscriptions.Subscriptions;

public class ObservableHolder<T> {
    private Subscription connectableSubscription = Subscriptions.empty();

    private ConnectableObservable<T> observable;


    public void bind(ConnectableObservable<T> observable) {
        this.observable = observable;
        connectableSubscription = observable.connect();
    }

    public void destroy() {
        connectableSubscription.unsubscribe();
    }

    public Observable<T> getObservable() {
        if (observable == null) {
            return Observable.empty();
        }
        return observable;
    }

    public boolean isRunning() {
        return observable != null;
    }

    public void clear() {
        observable = null;
        connectableSubscription = Subscriptions.empty();
    }
}
