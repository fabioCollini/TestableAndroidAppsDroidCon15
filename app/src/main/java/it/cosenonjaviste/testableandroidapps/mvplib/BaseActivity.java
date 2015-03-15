package it.cosenonjaviste.testableandroidapps.mvplib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import butterknife.ButterKnife;
import it.cosenonjaviste.testableandroidapps.RetainedFragment;

public abstract class BaseActivity<M, V> extends ActionBarActivity {

    protected MvpContext<M, V> mvpContext;

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mvpContext.saveState(outState);
    }

    @Override protected void onResume() {
        super.onResume();
        mvpContext.resume();
    }

    @Override protected void onPause() {
        super.onPause();
        mvpContext.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        RetainedFragment<Presenter<M, V>> retainedFragment = RetainedFragment.getOrCreate(this, "retained", this::createPresenter);

        mvpContext = new MvpContext<>((V) this, savedInstanceState != null ? savedInstanceState : getIntent().getExtras(), retainedFragment);

        ButterKnife.inject(retainedFragment.get(), findViewById(android.R.id.content));
    }

    protected abstract int getLayoutId();

    protected abstract Presenter<M, V> createPresenter();

    public <M1, V1> void startShareActivity(Class<? extends BaseActivity<M1, V1>> cls, M1 model) {
        Intent intent = new Intent(this, cls);
        MvpContext.populateIntent(intent, model);
        startActivity(intent);
    }
}
