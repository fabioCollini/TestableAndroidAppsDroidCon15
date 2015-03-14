package it.cosenonjaviste.testableandroidapps.v8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.lib.AndrularMvpTestContext;
import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostListTest {

    @Mock WordPressService wordPressService;

    @Mock PostListActivity view;

    @Captor ArgumentCaptor<ShareModel> captor;

    private AndrularMvpTestContext andrularTestContext;

    @Before
    public void setUp() throws Exception {
        PostListPresenter postListPresenter = new PostListPresenter(wordPressService, Schedulers.immediate(), Schedulers.immediate());
        PostListModel model = new PostListModel();
        andrularTestContext = new AndrularMvpTestContext(view, postListPresenter);
    }

    @Test
    public void testLoading() {
        when(wordPressService.listPosts())
                .thenReturn(Observable.just(new PostResponse(new Post(), new Post(), new Post())));

        andrularTestContext.resume();

        assertEquals(3, andrularTestContext.getListSize(R.id.list));
    }
}
