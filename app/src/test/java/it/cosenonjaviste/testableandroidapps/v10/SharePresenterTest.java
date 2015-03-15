package it.cosenonjaviste.testableandroidapps.v10;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.testableandroidapps.R;
import it.cosenonjaviste.testableandroidapps.ShareExecutor;
import it.cosenonjaviste.testableandroidapps.mvplib.MvpTestContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SharePresenterTest {

    @Mock ShareExecutor shareExecutor;

    @Mock ShareActivity view;

    @InjectMocks SharePresenter sharePresenter;

    private MvpTestContext<ShareModel, ShareActivity> testContext;

    @Before
    public void setUp() {
        when(view.getString(anyInt())).thenReturn("abc");

        testContext = new MvpTestContext<>(view, sharePresenter);

        testContext.resume();
    }

    @Test
    public void testValidationOk() {
        testContext.getTextView(R.id.share_title).setText("title");
        testContext.getTextView(R.id.share_body).setText("body");

        testContext.clickOnView(R.id.share_button);

        verify(shareExecutor).startSendActivity(eq("title"), eq("body"));
    }

    @Test
    public void testValidationError() {
        testContext.getTextView(R.id.share_body).setText("body");
        testContext.clickOnView(R.id.share_button);

        assertNotNull(testContext.getTextView(R.id.share_title).getError());
        assertNull(testContext.getTextView(R.id.share_body).getError());

        verify(shareExecutor, never()).startSendActivity(anyString(), anyString());
    }
}
