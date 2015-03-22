package it.cosenonjaviste.testableandroidapps.v5;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import it.cosenonjaviste.testableandroidapps.model.Post;

@Singleton
public class EmailSender {

    @Inject public EmailSender() {
    }

    public void sendEmail(Post p) {
        try {
            Thread.sleep(new Random().nextInt(500));
        } catch (InterruptedException ignored) {
        }
        System.out.println("email " + p.getTitle() + " sent!");
    }
}
