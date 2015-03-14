package it.cosenonjaviste.testableandroidapps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import rx.functions.Action1;
import rx.functions.Func0;

public class RetainedFragment<T> extends Fragment {

    private T object;
    private Action1<T> onDestroy;

    public RetainedFragment() {
        setRetainInstance(true);
    }

    public static <T> RetainedFragment<T> create(T object) {
        RetainedFragment<T> fragment = new RetainedFragment<>();
        fragment.object = object;
        return fragment;
    }

    public T get() {
        return object;
    }

    public void init(T object, Action1<T> onDestroy) {
        this.object = object;
        this.onDestroy = onDestroy;
    }

    @Override public void onDestroy() {
        super.onDestroy();
        if (onDestroy != null) {
            onDestroy.call(object);
        }
    }

    public void setOnDestroy(Action1<T> onDestroy) {
        this.onDestroy = onDestroy;
    }

    public static <T> RetainedFragment<T> getOrCreate(FragmentActivity activity, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        RetainedFragment<T> fragment = (RetainedFragment<T>) fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new RetainedFragment<>();
            fragmentManager.beginTransaction().add(fragment, tag).commit();
        }
        return fragment;
    }

    public static <T> T getOrCreate(FragmentActivity activity, String tag, Action1<RetainedFragment<T>> initAction) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        RetainedFragment<T> fragment = (RetainedFragment<T>) fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new RetainedFragment<>();
            fragmentManager.beginTransaction().add(fragment, tag).commit();
            initAction.call(fragment);
        }
        return fragment.get();
    }

    public static <T> RetainedFragment<T> getOrCreate(FragmentManager fragmentManager, String tag, Func0<T> initAction) {
        RetainedFragment<T> fragment = (RetainedFragment<T>) fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new RetainedFragment<>();
            fragmentManager.beginTransaction().add(fragment, tag).commit();
            fragment.object = initAction.call();
        }
        return fragment;
    }
}
