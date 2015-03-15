package it.cosenonjaviste.testableandroidapps.v10;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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
import it.cosenonjaviste.testableandroidapps.mvplib.MvpContext;
import it.cosenonjaviste.testableandroidapps.mvplib.Presenter;
import rx.Observable;
import rx.Subscription;

public class PostListPresenter implements Presenter<PostListModel, PostListActivity> {

    private WordPressService wordPressService;

    private SchedulerManager schedulerManager;

    private ObservableHolder<List<Post>> observableHolder = new ObservableHolder<>();

    private Subscription subscription;

    private MvpContext<PostListModel, PostListActivity> mvpContext;

    @InjectView(R.id.list) ListView listView;

    @InjectView(R.id.progress_layout) View progressLayout;

    @InjectView(R.id.error_layout) View errorLayout;

    @InjectView(R.id.error_text) TextView errorText;

    @Inject public PostListPresenter(WordPressService wordPressService, SchedulerManager schedulerManager) {
        this.wordPressService = wordPressService;
        this.schedulerManager = schedulerManager;
    }

    public PostListModel getModel() {
        return mvpContext.getModel();
    }

    @Override public void resume(MvpContext<PostListModel, PostListActivity> mvpContext) {
        this.mvpContext = mvpContext;
        PostListModel model = getModel();
        if (model.getItems() == null && model.getErrorText() == null && !observableHolder.isRunning()) {
            reloadData();
        }

        updateView(model, observableHolder.isRunning());

        subscription = observableHolder.getObservable().subscribe(l -> {
            model.setItems(l);
            model.setErrorText(null);
            observableHolder.clear();
            updateView(model, false);
        }, t -> {
            model.setErrorText(t.getMessage());
            updateView(model, false);
        });
    }

    @OnClick(R.id.reload_button)
    public void reloadData() {
        Observable<List<Post>> observable = createListObservable();
        observableHolder.bind(observable.replay());
        updateView(getModel(), observableHolder.isRunning());
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
        mvpContext.getView().startShareActivity(ShareActivity.class, new ShareModel(post.getTitle(), body));
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

    public void updateView(PostListModel model, boolean runntingIsTask) {
        if (model.getErrorText() != null) {
            errorText.setText(model.getErrorText());
            progressLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else if (runntingIsTask) {
            progressLayout.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        } else if (model.getItems() != null) {
            mvpContext.getView().updateItems(model.getItems());
            progressLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }
}
