package it.cosenonjaviste.testableandroidapps.v2;

import org.junit.Before;
import org.junit.Test;

import it.cosenonjaviste.testableandroidapps.model.Post;
import it.cosenonjaviste.testableandroidapps.model.PostResponse;

import static org.junit.Assert.assertEquals;

public class PostsBatchTest {

    private PostsBatch postsBatch;

    private EmailSenderSpy emailSenderSpy;

    @Before public void init() {
        emailSenderSpy = new EmailSenderSpy();
        postsBatch = new PostsBatch(
                new WordPressServiceStub(new PostResponse(new Post(), new Post(), new Post())),
                emailSenderSpy
        );
    }

    @Test
    public void testExecute() {
        postsBatch.execute();
        assertEquals(3, emailSenderSpy.getEmailSent());
    }
}
