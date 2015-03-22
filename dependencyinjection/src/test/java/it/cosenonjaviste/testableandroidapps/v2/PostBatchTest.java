package it.cosenonjaviste.testableandroidapps.v2;

import org.junit.Before;
import org.junit.Test;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;

import static org.junit.Assert.assertEquals;

public class PostBatchTest {

    private PostBatch postBatch;

    private EmailSenderSpy emailSenderSpy;

    private WordPressServiceStub serviceStub;

    @Before public void init() {
        emailSenderSpy = new EmailSenderSpy();
        serviceStub = new WordPressServiceStub(new PostResponse(new Post(), new Post(), new Post()));
        postBatch = new PostBatch(serviceStub, emailSenderSpy);
    }

    @Test
    public void testExecute() {
        postBatch.execute();
        assertEquals(3, emailSenderSpy.getEmailCount());
    }
}
