package it.cosenonjaviste.testableandroidapps.v5;

public class Main {
    public static void main(String[] args) {
        PostBatch batch = DaggerMainComponent.create().getBatch();
        batch.execute();
    }
}
