package it.cosenonjaviste.testableandroidapps.v1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import it.cosenonjaviste.testableandroidapps.R;


public class ShareActivity extends ActionBarActivity {

    @InjectView(R.id.share_title) EditText titleEditText;

    @InjectView(R.id.share_body) EditText bodyEditText;

    private static final String TITLE = "title";
    private static final String BODY = "body";

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

        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);

        String title = getIntent().getStringExtra(TITLE);
        String body = getIntent().getStringExtra(BODY);

        titleEditText.setText(title);
        bodyEditText.setText(body);
    }

    @OnFocusChange({R.id.share_title, R.id.share_body}) void validateFields() {
        String title = titleEditText.getText().toString();
        titleEditText.setError(title.isEmpty() ? getString(R.string.mandatory_field) : null);

        String body = bodyEditText.getText().toString();
        bodyEditText.setError(body.isEmpty() ? getString(R.string.mandatory_field) : null);
    }

    @OnClick(R.id.share_button) void share() {
        validateFields();
        if (titleEditText.getError() == null && bodyEditText.getError() == null) {
            startSendActivity(titleEditText.getText().toString(), bodyEditText.getText().toString());
        }
    }

    private void startSendActivity(String title, String body) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, getResources().getText(R.string.share)));
    }
}
