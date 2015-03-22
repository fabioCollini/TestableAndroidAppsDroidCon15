package it.cosenonjaviste.testableandroidapps.v5;

public class Main {
    public static void main(String[] args) {
        PostBatch batch = Dagger_MainComponent.create().getBatch();
        batch.execute();
    }
}
