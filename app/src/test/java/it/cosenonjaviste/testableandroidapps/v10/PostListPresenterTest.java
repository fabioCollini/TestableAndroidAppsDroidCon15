package it.cosenonjaviste.testableandroidapps.v10;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.SchedulerManager;
import it.cosenonjaviste.testableandroidapps.model.Author;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import it.cosenonjaviste.testableandroidapps.mvplib.MvpTestContext;
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

    @Captor ArgumentCaptor<ShareModel> captor;

    @Captor ArgumentCaptor<List<Post>> itemsCaptor;

    private MvpTestContext<PostListModel, PostListActivity> testContext;

    @Before
    public void setUp() throws Exception {
        SchedulerManager schedulerManager = new SchedulerManager(Schedulers.immediate(), Schedulers.immediate());
        PostListPresenter postListPresenter = new PostListPresenter(wordPressService, schedulerManager);

        testContext = new MvpTestContext<>(view, postListPresenter);
    }

    @Test
    public void loadingPosts() {
        when(wordPressService.listPosts())
                .thenReturn(Observable.just(new PostResponse(new Post(), new Post(), new Post())));

        testContext.resume();

        verify(view).updateItems(itemsCaptor.capture());
        assertEquals(3, itemsCaptor.getValue().size());
    }

    @Test
    public void loadingError() {
        when(wordPressService.listPosts())
                .thenReturn(Observable.error(new RuntimeException("abc")));

        testContext.resume();

        assertEquals("abc", testContext.getTextView(R.id.error_text).getText());
    }

    @Test
    public void clickOnItem() {
        testContext.setModel(new PostListModel(createPost(1), createPost(2), createPost(3)));

        testContext.resume();
        testContext.clickOnItem(R.id.list, 1);

        verify(view).startShareActivity(eq(ShareActivity.class), captor.capture());
        assertEquals("title 2", captor.getValue().getTitle());
        assertEquals("name 2 last name 2\nexcerpt 2", captor.getValue().getBody());
    }

    private static Post createPost(int id) {
        return new Post(id, new Author(id, "name " + id, "last name " + id), "title " + id, new Date(), "url" + id, "excerpt " + id);
    }

}
