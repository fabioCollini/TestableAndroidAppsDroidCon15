package it.cosenonjaviste.testableandroidapps.v8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.parceler.Parcels;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.RetainedFragment;
import it.cosenonjaviste.testableandroidapps.lib.AndrularMvpContext;


public class ShareActivity extends ActionBarActivity {

    private AndrularMvpContext<ShareModel, ShareActivity> andrularContext;

    public static final String MODEL = "model";

    public static void createAndStart(Context context, ShareModel model) {
        Intent intent = new Intent(context, ShareActivity.class);
        populateIntent(intent, model);
        context.startActivity(intent);
    }

    public static void populateIntent(Intent intent, ShareModel model) {
        intent.putExtra(MODEL, Parcels.wrap(model));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        andrularContext = new AndrularMvpContext<>(this,
                savedInstanceState != null ? savedInstanceState : getIntent().getExtras(),
                RetainedFragment.getOrCreate(getSupportFragmentManager(), "retained", () -> {
                    ApplicationComponent appComponent = ((CnjApplication) getApplicationContext()).getComponent();
                    return appComponent.createSharePresenter();
                })
        );
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        andrularContext.saveState(outState);
    }

    @Override protected void onResume() {
        super.onResume();
        andrularContext.resume();
    }

    @Override protected void onPause() {
        super.onPause();
        andrularContext.pause();
    }
}
