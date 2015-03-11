package it.cosenonjaviste.testableandroidapps.v6;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.testableandroidapps.ShareExecutor;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SharePresenterTest {

    @Mock ShareExecutor shareExecutor;

    @Mock ShareActivity view;

    @InjectMocks SharePresenter sharePresenter;

    @Test
    public void testValidationOk() {
        ShareModel model = new ShareModel();
        sharePresenter.init(view, model);

        sharePresenter.share("title", "body");

        verify(shareExecutor).startSendActivity(eq("title"), eq("body"));
    }

    @Test
    public void testValidationError() {
        ShareModel model = new ShareModel();
        sharePresenter.init(view, model);

        sharePresenter.share("", "body");

        assertTrue(model.getTitleError() != 0);
        assertTrue(model.getBodyError() == 0);

        verify(shareExecutor, never()).startSendActivity(anyString(), anyString());
    }
}
