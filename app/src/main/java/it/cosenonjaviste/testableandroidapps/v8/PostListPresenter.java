package it.cosenonjaviste.testableandroidapps.v8;

import java.text.MessageFormat;
import java.util.List;

import it.cosenonjaviste.testableandroidapps.ObservableHolder;
import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.lib.AndrularMvpContext;
import it.cosenonjaviste.testableandroidapps.lib.Bind;
import it.cosenonjaviste.testableandroidapps.lib.BindField;
import it.cosenonjaviste.testableandroidapps.lib.OnClick;
import it.cosenonjaviste.testableandroidapps.lib.OnItemClick;
import it.cosenonjaviste.testableandroidapps.lib.Presenter;
import it.cosenonjaviste.testableandroidapps.model.Author;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;

public class PostListPresenter implements Presenter<PostListModel, PostListActivity> {

    private final WordPressService wordPressService;

    private ObservableHolder<List<Post>> observableHolder = new ObservableHolder<>();

    private Subscription subscription;

    private AndrularMvpContext<PostListModel, PostListActivity> andrularContext;
    private Scheduler ioScheduler;
    private Scheduler mainThreadscheduler;

    public PostListPresenter(WordPressService wordPressService, Scheduler ioScheduler, Scheduler mainThreadscheduler) {
        this.wordPressService = wordPressService;
        this.ioScheduler = ioScheduler;
        this.mainThreadscheduler = mainThreadscheduler;
    }

    public void resume(AndrularMvpContext<PostListModel, PostListActivity> andrularContext) {
        this.andrularContext = andrularContext;
        PostListModel model = andrularContext.getModel();
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

    @OnItemClick(R.id.list)
    public void onItemClick(int position) {
        Post post = andrularContext.getModel().getItems().get(position);
        Author author = post.getAuthor();
        String excerpt = post.getExcerpt();
        excerpt = excerpt.replace("<p>", "").replace("</p>", "");
        excerpt = excerpt.trim();
        if (excerpt.length() > 150) {
            excerpt = excerpt.substring(0, 150) + "...";
        }
        String body = MessageFormat.format("{0} {1}\n{2}", author.getFirstName(), author.getLastName(), excerpt);
        andrularContext.getView().startShareActivity(new ShareModel(post.getTitle(), body));
    }

    public void pause() {
        subscription.unsubscribe();
    }

    public void destroy() {
        observableHolder.destroy();
    }

    @Override public PostListModel createDefaultModel() {
        return new PostListModel();
    }
}
