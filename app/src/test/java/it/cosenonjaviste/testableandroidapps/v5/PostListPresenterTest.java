package it.cosenonjaviste.testableandroidapps.v5;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;

import it.cosenonjaviste.testableandroidapps.model.Author;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostListPresenterTest {

    @Mock WordPressService wordPressService;

    @Mock PostListActivity view;

    private PostListPresenter postListPresenter;

    @Before
    public void setUp() throws Exception {
        postListPresenter = new PostListPresenter(wordPressService, Schedulers.immediate(), Schedulers.immediate());
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

        verify(view).startShareActivity(eq("title 2"), eq("name 2 last name 2\nexcerpt 2"));
    }

    private static Post createPost(int id) {
        return new Post(id, new Author(id, "name " + id, "last name " + id), "title " + id, new Date(), "url" + id, "excerpt " + id);
    }

}
