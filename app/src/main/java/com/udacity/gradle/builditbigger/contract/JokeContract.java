package com.udacity.gradle.builditbigger.contract;

public interface JokeContract {

    public interface Presenter {
        public void start();
        public void stop();
        public void fetchFromRemoteSource();
    }

    public interface View {
        public void loadDownloadingScene();
        public void loadEmptyScreen();
        public void loadJokeScene(String joke);
    }
}
