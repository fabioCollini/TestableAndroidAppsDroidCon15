package it.cosenonjaviste.testableandroidapps.v7;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;

import it.cosenonjaviste.testableandroidapps.SchedulerManager;
import it.cosenonjaviste.testableandroidapps.model.Author;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostListPresenterTest {

    @Mock WordPressService wordPressService;

    @Mock PostListActivity view;

    @Captor ArgumentCaptor<ShareModel> captor;

    private PostListPresenter postListPresenter;

    @Before
    public void setUp() throws Exception {
        SchedulerManager schedulerManager = new SchedulerManager(Schedulers.immediate(), Schedulers.immediate());
        postListPresenter = new PostListPresenter(wordPressService, schedulerManager);
    }

    @Test
    public void loadingPosts() {
        when(wordPressService.listPosts())
                .thenReturn(Observable.just(new PostResponse(new Post(), new Post(), new Post())));

        PostListModel model = new PostListModel();
        postListPresenter.setModel(model);
        postListPresenter.resume(view);

        assertEquals(3, model.getItems().size());
    }

    @Test
    public void loadingError() {
        when(wordPressService.listPosts())
                .thenReturn(Observable.error(new RuntimeException("abc")));

        PostListModel model = new PostListModel();
        postListPresenter.setModel(model);
        postListPresenter.resume(view);

        assertEquals("abc", model.getErrorText());
    }

    @Test
    public void clickOnItem() {
        PostListModel model = new PostListModel();
        model.setItems(Arrays.asList(createPost(1), createPost(2), createPost(3)));
        postListPresenter.setModel(model);
        postListPresenter.resume(view);

        postListPresenter.onItemClick(1);

        verify(view).startShareActivity(captor.capture());
        assertEquals("title 2", captor.getValue().getTitle());
        assertEquals("name 2 last name 2\nexcerpt 2", captor.getValue().getBody());
    }

    private static Post createPost(int id) {
        return new Post(id, new Author(id, "name " + id, "last name " + id), "title " + id, new Date(), "url" + id, "excerpt " + id);
    }

}
