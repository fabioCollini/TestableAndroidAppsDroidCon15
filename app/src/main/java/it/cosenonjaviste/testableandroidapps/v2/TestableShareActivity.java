package it.cosenonjaviste.testableandroidapps.v2;

public class TestableShareActivity extends ShareActivity {

    public String shareTitle;

    public String shareBody;

    @Override protected void startSendActivity(String title, String body) {
        shareTitle = title;
        shareBody = body;
    }
}
