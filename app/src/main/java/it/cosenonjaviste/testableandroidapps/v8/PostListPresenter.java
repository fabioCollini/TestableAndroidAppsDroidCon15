package it.cosenonjaviste.testableandroidapps.v8;

import android.view.View;
import android.widget.ListView;

import java.text.MessageFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import it.cosenonjaviste.testableandroidapps.ObservableHolder;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.SchedulerManager;
import it.cosenonjaviste.testableandroidapps.model.Author;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import rx.Observable;
import rx.Subscription;

public class PostListPresenter {

    private WordPressService wordPressService;

    private SchedulerManager schedulerManager;

    private ObservableHolder<List<Post>> observableHolder = new ObservableHolder<>();

    private Subscription subscription;

    private PostListModel model;

    private PostListActivity view;

    @InjectView(R.id.list) ListView listView;

    @InjectView(R.id.progress_layout) View progressLayout;

    @InjectView(R.id.error_layout) View errorLayout;

    @Inject public PostListPresenter(WordPressService wordPressService, SchedulerManager schedulerManager) {
        this.wordPressService = wordPressService;
        this.schedulerManager = schedulerManager;
    }

    public PostListModel getModel() {
        return model;
    }

    public void setModel(PostListModel model) {
        this.model = model;
    }

    public void resume(PostListActivity view) {
        this.view = view;
        if (model.getItems() == null && model.getErrorText() == null && !observableHolder.isRunning()) {
            reloadData();
        }
        subscription = observableHolder.getObservable().subscribe(l -> {
            model.setItems(l);
            model.setErrorText(null);
            observableHolder.clear();
            updateView(model, false);
        }, t -> {
            model.setErrorText(t.getMessage());
            updateView(model, false);
        });

        updateView(model, observableHolder.isRunning());
    }

    @OnClick(R.id.reload_button)
    public void reloadData() {
        Observable<List<Post>> observable = createListObservable();
        observableHolder.bind(observable.replay());
        updateView(model, observableHolder.isRunning());
    }

    private Observable<List<Post>> createListObservable() {
        return wordPressService
                .listPosts()
                .map(PostResponse::getPosts)
                .compose(schedulerManager.schedule());
    }

    @OnItemClick(R.id.list)
    public void onItemClick(int position) {
        Post post = getModel().getItems().get(position);
        Author author = post.getAuthor();
        String excerpt = post.getExcerpt();
        excerpt = excerpt.replace("<p>", "").replace("</p>", "");
        excerpt = excerpt.trim();
        if (excerpt.length() > 150) {
            excerpt = excerpt.substring(0, 150) + "...";
        }
        String body = MessageFormat.format("{0} {1}\n{2}", author.getFirstName(), author.getLastName(), excerpt);
        view.startShareActivity(new ShareModel(post.getTitle(), body));
    }

    public void pause() {
        subscription.unsubscribe();
    }

    public void destroy() {
        observableHolder.destroy();
    }


    public void updateView(PostListModel model, boolean runntingIsTask) {
        if (model.getErrorText() != null) {
            progressLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else if (runntingIsTask) {
            progressLayout.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        } else if (model.getItems() != null) {
            view.updateItems(model.getItems());
            progressLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }
}
