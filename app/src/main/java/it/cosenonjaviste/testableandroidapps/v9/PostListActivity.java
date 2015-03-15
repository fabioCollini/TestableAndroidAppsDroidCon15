package it.cosenonjaviste.testableandroidapps.v9;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.List;

import butterknife.ButterKnife;
import it.cosenonjaviste.testableandroidapps.ApplicationComponent;
import it.cosenonjaviste.testableandroidapps.CnjApplication;
import it.cosenonjaviste.testableandroidapps.PostAdapter;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.RetainedFragment;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.mvplib.MvpContext;
import it.cosenonjaviste.testableandroidapps.mvplib.Presenter;


public class PostListActivity extends ActionBarActivity {

    private PostAdapter adapter;

    private MvpContext<PostListModel, PostListActivity> mvpContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        adapter = new PostAdapter(getLayoutInflater());
        ((ListView) findViewById(R.id.list)).setAdapter(adapter);

        RetainedFragment<Presenter<PostListModel, PostListActivity>> retainedFragment = RetainedFragment.getOrCreate(this, "retained", () -> {
            ApplicationComponent component = ((CnjApplication) getApplicationContext()).getComponent();
            return component.getPostListPresenterV9();
        });

        mvpContext = new MvpContext<>(this, savedInstanceState != null ? savedInstanceState : getIntent().getExtras(), retainedFragment);

        ButterKnife.inject(retainedFragment.get(), findViewById(android.R.id.content));
    }

    public void startShareActivity(ShareModel model) {
        ShareActivity.createAndStart(this, model);
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

    public void updateItems(List<Post> items) {
        adapter.setItems(items);
    }
}
