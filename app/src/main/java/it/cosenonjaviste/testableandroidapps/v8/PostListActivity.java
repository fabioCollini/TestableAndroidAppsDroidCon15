package it.cosenonjaviste.testableandroidapps.v8;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.cosenonjaviste.testableandroidapps.ApplicationComponent;
import it.cosenonjaviste.testableandroidapps.CnjApplication;
import it.cosenonjaviste.testableandroidapps.PostAdapter;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.RetainedFragment;
import it.cosenonjaviste.testableandroidapps.model.Post;


public class PostListActivity extends ActionBarActivity {

    public static final String MODEL = "model";

    @InjectView(R.id.list) ListView listView;

    private PostAdapter adapter;

    private PostListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationComponent component = ((CnjApplication) getApplicationContext()).getComponent();
        component.inject(this);

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        adapter = new PostAdapter(getLayoutInflater());
        listView.setAdapter(adapter);

        RetainedFragment<PostListPresenter> retainedFragment = RetainedFragment.getOrCreate(this, "retained");
        presenter = retainedFragment.get();
        if (presenter == null) {
            presenter = component.getPostListPresenterV8();
            retainedFragment.init(presenter, PostListPresenter::destroy);
        }

        ButterKnife.inject(presenter, findViewById(android.R.id.content));

        PostListModel model;
        if (savedInstanceState != null) {
            model = Parcels.unwrap(savedInstanceState.getParcelable(MODEL));
        } else {
            model = new PostListModel();
        }
        presenter.setModel(model);
    }

    public void startShareActivity(ShareModel model) {
        ShareActivity.createAndStart(this, model);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MODEL, Parcels.wrap(presenter.getModel()));
    }

    @Override protected void onResume() {
        super.onResume();
        presenter.resume(this);
    }

    @Override protected void onPause() {
        super.onPause();
        presenter.pause();
    }

    public void updateItems(List<Post> items) {
        adapter.setItems(items);
    }
}
