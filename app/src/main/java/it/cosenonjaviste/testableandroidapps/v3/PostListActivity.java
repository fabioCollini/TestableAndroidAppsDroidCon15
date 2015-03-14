package it.cosenonjaviste.testableandroidapps.v3;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;

import org.parceler.Parcels;

import java.text.MessageFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import it.cosenonjaviste.testableandroidapps.CnjApplication;
import it.cosenonjaviste.testableandroidapps.ObservableHolder;
import it.cosenonjaviste.testableandroidapps.PostAdapter;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.SchedulerManager;
import it.cosenonjaviste.testableandroidapps.RetainedFragment;
import it.cosenonjaviste.testableandroidapps.model.Author;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import rx.Observable;
import rx.Subscription;


public class PostListActivity extends ActionBarActivity {

    public static final String ITEMS = "items";

    @InjectView(R.id.list) ListView listView;

    @InjectView(R.id.progress_layout) View progressLayout;

    @InjectView(R.id.error_layout) View errorLayout;

    private RetainedFragment<ObservableHolder<List<Post>>> retainedFragment;

    private Subscription subscription;

    private PostAdapter adapter;

    @Inject WordPressService wordPressService;

    @Inject SchedulerManager schedulerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((CnjApplication) getApplicationContext()).getComponent().inject(this);

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        adapter = new PostAdapter(getLayoutInflater());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> startShareActivity(position));

        retainedFragment = RetainedFragment.getOrCreate(this, "retained");
        if (retainedFragment.get() == null) {
            retainedFragment.init(new ObservableHolder<>(), ObservableHolder::destroy);
        }


        if (savedInstanceState != null) {
            restoreUi(savedInstanceState);
        } else {
            reloadData();
        }
    }

    private void startShareActivity(int position) {
        Post post = adapter.getItem(position);
        Author author = post.getAuthor();
        String excerpt = post.getExcerpt();
        excerpt = excerpt.replace("<p>", "").replace("</p>", "");
        excerpt = excerpt.trim();
        if (excerpt.length() > 150) {
            excerpt = excerpt.substring(0, 150) + "...";
        }
        String body = MessageFormat.format("{0} {1}\n{2}", author.getFirstName(), author.getLastName(), excerpt);
        ShareActivity.createAndStart(PostListActivity.this, post.getTitle(), body);
    }

    @OnClick(R.id.reload_button) void reloadData() {
        progressLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        Observable<List<Post>> observable = createListObservable();
        retainedFragment.get().bind(observable.replay());
    }

    protected Observable<List<Post>> createListObservable() {
        return wordPressService
                .listPosts()
                .map(PostResponse::getPosts)
                .compose(schedulerManager.schedule());
    }

    private void restoreUi(Bundle savedInstanceState) {
        List<Post> savedList = Parcels.unwrap(savedInstanceState.getParcelable(ITEMS));
        if (savedList != null) {
            adapter.addItems(savedList);
        } else if (retainedFragment.get().isRunning()) {
            progressLayout.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        } else {
            progressLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ITEMS, Parcels.wrap(adapter.getItems()));
    }

    @Override protected void onResume() {
        super.onResume();
        subscription = retainedFragment.get().getObservable().subscribe(l -> {
            adapter.addItems(l);
            retainedFragment.get().clear();
            progressLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }, t -> {
            progressLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        });
    }

    @Override protected void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }
}
