package it.cosenonjaviste.testableandroidapps.v4;

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
import it.cosenonjaviste.testableandroidapps.ObservableHolder;
import it.cosenonjaviste.testableandroidapps.PostAdapter;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.RetainedObservableFragment;
import it.cosenonjaviste.testableandroidapps.model.Author;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PostListActivity extends ActionBarActivity {

    public static final String MODEL = "model";

    @InjectView(R.id.list) ListView listView;

    @InjectView(R.id.progress_layout) View progressLayout;

    @InjectView(R.id.error_layout) View errorLayout;

    private RetainedObservableFragment<ObservableHolder<List<Post>>> retainedFragment;

    private Subscription subscription;

    private PostAdapter adapter;

    private PostListModel model;

    @Inject WordPressService wordPressService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((CnjApplication) getApplicationContext()).getComponent().inject(this);

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        adapter = new PostAdapter(getLayoutInflater());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> startShareActivity(position));

        retainedFragment = RetainedObservableFragment.getOrCreate(this, "retained");
        if (retainedFragment.get() == null) {
            retainedFragment.init(new ObservableHolder<>(), ObservableHolder::destroy);
        }


        if (savedInstanceState != null) {
            model = Parcels.unwrap(savedInstanceState.getParcelable(MODEL));
            if (model.getItems() == null && model.getErrorText() == null && !retainedFragment.get().isRunning()) {
                reloadData();
            }
            updateView();
        } else {
            model = new PostListModel();
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
        ShareActivity.createAndStart(this, post.getTitle(), body);
    }

    @OnClick(R.id.reload_button) void reloadData() {
        Observable<List<Post>> observable = createListObservable();
        retainedFragment.get().bind(observable.replay());
        updateView();
    }

    protected Observable<List<Post>> createListObservable() {
        return wordPressService
                .listPosts()
                .map(PostResponse::getPosts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MODEL, Parcels.wrap(model));
    }

    @Override protected void onResume() {
        super.onResume();
        subscription = retainedFragment.get().getObservable().subscribe(l -> {
            model.setItems(l);
            model.setErrorText(null);
            retainedFragment.get().clear();
            updateView();
        }, t -> {
            model.setErrorText(t.getMessage());
            updateView();
        });
    }

    private void updateView() {
        if (model.getErrorText() != null) {
            progressLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else if (retainedFragment.get().isRunning()) {
            progressLayout.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        } else if (model.getItems() != null) {
            adapter.setItems(model.getItems());
            progressLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    @Override protected void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }
}
