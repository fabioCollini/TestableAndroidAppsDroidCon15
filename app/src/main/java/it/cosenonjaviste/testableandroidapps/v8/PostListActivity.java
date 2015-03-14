package it.cosenonjaviste.testableandroidapps.v8;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.RetainedFragment;
import it.cosenonjaviste.testableandroidapps.lib.AndrularMvpContext;


public class PostListActivity extends ActionBarActivity {

    private AndrularMvpContext<PostListModel, PostListActivity> andrularContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        andrularContext = new AndrularMvpContext<>(this,
                savedInstanceState != null ? savedInstanceState : getIntent().getExtras(),
                RetainedFragment.getOrCreate(getSupportFragmentManager(), "retained", () -> {
                    ApplicationComponent appComponent = ((CnjApplication) getApplicationContext()).getComponent();
                    return appComponent.createPostListPresenter();
                })
        );
    }

    public void startShareActivity(ShareModel model) {
        ShareActivity.createAndStart(this, model);
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
