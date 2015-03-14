package it.cosenonjaviste.testableandroidapps.v8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.lib.AndrularMvpTestContext;
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
public class PostListTest {

    @Mock WordPressService wordPressService;

    @Mock PostListActivity view;

    @Captor ArgumentCaptor<ShareModel> captor;

    private AndrularMvpTestContext<PostListModel, PostListActivity> andrularTestContext;

    @Before
    public void setUp() throws Exception {
        PostListPresenter postListPresenter = new PostListPresenter(wordPressService, Schedulers.immediate(), Schedulers.immediate());
        andrularTestContext = new AndrularMvpTestContext<>(view, postListPresenter);
    }

    @Test
    public void testLoading() {
        when(wordPressService.listPosts())
                .thenReturn(Observable.just(new PostResponse(createPost(1), createPost(2), createPost(3))));

        andrularTestContext.resume();

        assertEquals(3, andrularTestContext.getListSize(R.id.list));
    }

    @Test
    public void loadingError() {
        when(wordPressService.listPosts())
                .thenReturn(Observable.error(new RuntimeException("abc")));

        andrularTestContext.resume();

        assertEquals("abc", andrularTestContext.getText(R.id.error_text));
    }

    @Test
    public void clickOnItem() {
        when(wordPressService.listPosts())
                .thenReturn(Observable.just(new PostResponse(createPost(1), createPost(2), createPost(3))));

        andrularTestContext.resume();

        andrularTestContext.clickOnItem(R.id.list, 1);

        verify(view).startShareActivity(captor.capture());
        assertEquals("title 2", captor.getValue().getTitle());
        assertEquals("name 2 last name 2\nexcerpt 2", captor.getValue().getBody());
    }

    private static Post createPost(int id) {
        return new Post(id, new Author(id, "name " + id, "last name " + id), "title " + id, new Date(), "url" + id, "excerpt " + id);
    }
}
