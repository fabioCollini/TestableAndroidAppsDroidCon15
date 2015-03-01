package it.cosenonjaviste.testableandroidapps.v6;

import javax.inject.Inject;

import it.cosenonjaviste.testableandroidapps.model.Post;

public class EmailSender {

    @Inject public EmailSender() {
    }

    public void sendEmail(Post p) {
        System.out.println("email " + p.getTitle() + " sent!");
    }
}
