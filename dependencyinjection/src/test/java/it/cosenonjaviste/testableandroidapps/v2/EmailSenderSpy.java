package it.cosenonjaviste.testableandroidapps.v2;

import it.cosenonjaviste.testableandroidapps.model.Post;

public class EmailSenderSpy extends EmailSender {

    private int emailCount;

    @Override public void sendEmail(Post p) {
        emailCount++;
    }

    public int getEmailCount() {
        return emailCount;
    }
}
