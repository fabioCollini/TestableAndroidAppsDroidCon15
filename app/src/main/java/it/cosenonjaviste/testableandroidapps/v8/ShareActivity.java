package it.cosenonjaviste.testableandroidapps.v8;

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
import rx.functions.Actions;


public class ShareActivity extends ActionBarActivity {

    public static final String MODEL = "model";

    private SharePresenter presenter;

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

        ApplicationComponent component = ((CnjApplication) getApplicationContext()).getComponent();
        component.inject(this);

        setContentView(R.layout.activity_detail);

        RetainedFragment<SharePresenter> retainedFragment = RetainedFragment.getOrCreate(this, "retained");
        presenter = retainedFragment.get();
        if (presenter == null) {
            presenter = component.getSharePresenterV8();
            retainedFragment.init(presenter, Actions.empty());
        }
        ButterKnife.inject(presenter, findViewById(android.R.id.content));

        Bundle bundle = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();
        ShareModel model = Parcels.unwrap(bundle.getParcelable(MODEL));

        presenter.init(this, model);
    }
    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        presenter.updateModel(titleEditText.getText().toString(), bodyEditText.getText().toString());
        outState.putParcelable(MODEL, Parcels.wrap(presenter.getModel()));
    }
}
