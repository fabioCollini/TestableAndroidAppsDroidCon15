package it.cosenonjaviste.testableandroidapps.v4;

import it.cosenonjaviste.testableandroidapps.model.Post;

public class EmailSenderSpy extends EmailSender {

    private int emailSent;

    @Override public void sendEmail(Post p) {
        emailSent++;
    }

    public int getEmailSent() {
        return emailSent;
    }
}
