package it.cosenonjaviste.testableandroidapps.v2;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;
import it.cosenonjaviste.testableandroidapps.model.WordPressService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MockitoPostBatchTest {

    private WordPressService service;

    private EmailSender emailSender;

    private PostBatch postBatch;

    @Before public void init() {
        emailSender = Mockito.mock(EmailSender.class);
        service = Mockito.mock(WordPressService.class);
        postBatch = new PostBatch(service, emailSender);
    }

    @Test
    public void testExecute() {
        when(service.listPosts())
                .thenReturn(new PostResponse(new Post(), new Post(), new Post()));

        postBatch.execute();

        verify(emailSender, times(3)).sendEmail(any(Post.class));
    }
}
