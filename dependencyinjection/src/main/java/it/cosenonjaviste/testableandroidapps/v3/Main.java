package it.cosenonjaviste.testableandroidapps.v3;

public class Main {
    public static void main(String[] args) {
        PostBatch batch = Dagger_MainComponent.create().getBatch();
        batch.execute();
    }
}
