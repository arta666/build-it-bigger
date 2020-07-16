package com.udacity.gradle.builditbigger.presenter;

import android.os.AsyncTask;
import com.udacity.gradle.builditbigger.Network.ServiceAsync;
import com.udacity.gradle.builditbigger.contract.JokeContract;

public class MainPresenter implements JokeContract.Presenter {

    private final JokeContract.View view;
    ServiceAsync mServiceAsync;

    public MainPresenter(JokeContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        if (mServiceAsync != null && mServiceAsync.getStatus() == AsyncTask.Status.RUNNING){
            mServiceAsync.cancel(true);
        }

    }

    @Override
    public void fetchFromRemoteSource() {
        if (mServiceAsync == null || mServiceAsync.getStatus() != AsyncTask.Status.RUNNING){
            mServiceAsync = new ServiceAsync(this,view);
            mServiceAsync.execute();
        }
    }

}
