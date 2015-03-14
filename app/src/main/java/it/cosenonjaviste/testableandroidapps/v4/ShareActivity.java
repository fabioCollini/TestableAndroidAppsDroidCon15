package it.cosenonjaviste.testableandroidapps.v4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import it.cosenonjaviste.testableandroidapps.CnjApplication;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.ShareExecutor;


public class ShareActivity extends ActionBarActivity {

    public static final String MODEL = "model";
    @InjectView(R.id.share_title) EditText titleEditText;

    @InjectView(R.id.share_body) EditText bodyEditText;

    @Inject ShareExecutor shareExecutor;

    private static final String TITLE = "title";
    private static final String BODY = "body";

    private ShareModel model;

    public static void createAndStart(Context context, String title, String body) {
        Intent intent = new Intent(context, ShareActivity.class);
        populateIntent(intent, title, body);
        context.startActivity(intent);
    }

    public static void populateIntent(Intent intent, String title, String body) {
        intent.putExtra(TITLE, title);
        intent.putExtra(BODY, body);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((CnjApplication) getApplicationContext()).getComponent().inject(this);

        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);

        if (savedInstanceState != null) {
            model = Parcels.unwrap(savedInstanceState.getParcelable(MODEL));
        } else {
            model = new ShareModel();
            model.setTitle(getIntent().getStringExtra(TITLE));
            model.setBody(getIntent().getStringExtra(BODY));
        }

        updateUi();
    }

    private void updateModel() {
        model.setTitle(titleEditText.getText().toString());
        model.setBody(bodyEditText.getText().toString());
    }

    private void updateUi() {
        titleEditText.setText(model.getTitle());
        bodyEditText.setText(model.getBody());
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        updateModel();
        outState.putParcelable(MODEL, Parcels.wrap(model));
    }

    @OnFocusChange({R.id.share_title, R.id.share_body}) void validateFields() {
        updateModel();
        titleEditText.setError(model.getTitle().isEmpty() ? getString(R.string.mandatory_field) : null);
        bodyEditText.setError(model.getBody().isEmpty() ? getString(R.string.mandatory_field) : null);
    }

    @OnClick(R.id.share_button) void share() {
        validateFields();
        if (titleEditText.getError() == null && bodyEditText.getError() == null) {
            shareExecutor.startSendActivity(titleEditText.getText().toString(), bodyEditText.getText().toString());
        }
    }
}
