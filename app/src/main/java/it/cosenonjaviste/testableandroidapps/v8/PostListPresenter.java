package it.cosenonjaviste.testableandroidapps.v8;

import java.text.MessageFormat;
import java.util.List;

import it.cosenonjaviste.testableandroidapps.ObservableHolder;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.lib.BaseContext;
import it.cosenonjaviste.testableandroidapps.lib.Bind;
import it.cosenonjaviste.testableandroidapps.lib.BindField;
import it.cosenonjaviste.testableandroidapps.lib.OnClick;
import it.cosenonjaviste.testableandroidapps.model.Author;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;

public class PostListPresenter {

    private final WordPressService wordPressService;

    private ObservableHolder<List<Post>> observableHolder = new ObservableHolder<>();

    private Subscription subscription;

    private PostListModel model;

    private PostListActivity view;
    private BaseContext andrularContext;
    private Scheduler ioScheduler;
    private Scheduler mainThreadscheduler;

    public PostListPresenter(WordPressService wordPressService, Scheduler ioScheduler, Scheduler mainThreadscheduler) {
        this.wordPressService = wordPressService;
        this.ioScheduler = ioScheduler;
        this.mainThreadscheduler = mainThreadscheduler;
    }

    public PostListModel getModel() {
        return model;
    }

    public void setModel(PostListModel model) {
        this.model = model;
    }

    public void resume(PostListActivity view, BaseContext andrularContext) {
        this.view = view;
        this.andrularContext = andrularContext;
        if (model.getItems() == null && model.getErrorText() == null && !observableHolder.isRunning()) {
            reloadData();
        }
        subscription = observableHolder.getObservable().subscribe(l -> {
            model.setItems(l);
            model.setErrorText(null);
            observableHolder.clear();
            andrularContext.updateView();
        }, t -> {
            model.setErrorText(t.getMessage());
            andrularContext.updateView();
        });

        andrularContext.updateView();
    }

    @Bind(value = R.id.progress_layout, field = BindField.VISIBILITY)
    public boolean isProgressVisible() {
        return observableHolder.isRunning();
    }

    @OnClick(R.id.reload_button)
    public void reloadData() {
        Observable<List<Post>> observable = createListObservable();
        observableHolder.bind(observable.replay());
        andrularContext.updateView();
    }

    private Observable<List<Post>> createListObservable() {
        return wordPressService
                .listPosts()
                .map(PostResponse::getPosts)
                .subscribeOn(ioScheduler)
                .observeOn(mainThreadscheduler);
    }

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
}
