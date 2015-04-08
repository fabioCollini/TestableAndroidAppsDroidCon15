package it.cosenonjaviste.testableandroidapps.v4;

public class Main {
    public static void main(String[] args) {
        PostBatch batch = DaggerMainComponent.create().getBatch();
        batch.execute();
    }
}
