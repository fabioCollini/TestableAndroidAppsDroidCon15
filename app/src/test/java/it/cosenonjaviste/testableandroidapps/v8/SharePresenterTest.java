package it.cosenonjaviste.testableandroidapps.v8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.ShareExecutor;
import it.cosenonjaviste.testableandroidapps.lib.AndrularMvpTestContext;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SharePresenterTest {

    @Mock ShareExecutor shareExecutor;

    @Mock ShareActivity view;

    @InjectMocks SharePresenter sharePresenter;

    private AndrularMvpTestContext<ShareModel, ShareActivity> andrularTestContext;

    @Before
    public void setUp() throws Exception {
        andrularTestContext = new AndrularMvpTestContext<>(view, sharePresenter);
    }

    @Test
    public void testValidationOk() {
        andrularTestContext.resume();

        andrularTestContext.writeText(R.id.share_title, "title");
        andrularTestContext.writeText(R.id.share_body, "body");

        andrularTestContext.click(R.id.share_button);

        verify(shareExecutor).startSendActivity(eq("title"), eq("body"));
    }

    @Test
    public void testValidationError() {
        andrularTestContext.resume();

        andrularTestContext.writeText(R.id.share_title, "title");

        andrularTestContext.click(R.id.share_button);

        verify(shareExecutor, never()).startSendActivity(anyString(), anyString());
    }
}
