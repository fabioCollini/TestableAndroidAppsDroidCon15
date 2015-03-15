package it.cosenonjaviste.testableandroidapps.v9;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import it.cosenonjaviste.testableandroidapps.ApplicationComponent;
import it.cosenonjaviste.testableandroidapps.CnjApplication;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.RetainedFragment;
import it.cosenonjaviste.testableandroidapps.mvplib.MvpContext;
import it.cosenonjaviste.testableandroidapps.mvplib.Presenter;


public class ShareActivity extends ActionBarActivity {

    private MvpContext<ShareModel, ShareActivity> mvpContext;

    public static void createAndStart(Context context, ShareModel model) {
        Intent intent = new Intent(context, ShareActivity.class);
        populateIntent(intent, model);
        context.startActivity(intent);
    }

    public static void populateIntent(Intent intent, ShareModel model) {
        intent.putExtra(MvpContext.MODEL, Parcels.wrap(model));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        RetainedFragment<Presenter<ShareModel, ShareActivity>> retainedFragment = RetainedFragment.getOrCreate(this, "retained", () -> {
            ApplicationComponent component = ((CnjApplication) getApplicationContext()).getComponent();
            return component.getSharePresenterV9();
        });

        mvpContext = new MvpContext<>(this, savedInstanceState != null ? savedInstanceState : getIntent().getExtras(), retainedFragment);

        ButterKnife.inject(retainedFragment.get(), findViewById(android.R.id.content));
    }

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
}
