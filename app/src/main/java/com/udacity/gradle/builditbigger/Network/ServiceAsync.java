package com.udacity.gradle.builditbigger.Network;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.app.Endpoints;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.contract.JokeContract;

import java.io.IOException;

public class ServiceAsync extends AsyncTask<Void, Integer, String> {

    private static final String TAG = ServiceAsync.class.getSimpleName();
    public static final int STATE_LOADING = 1;
    public static final int STATE_EMPTY = 2;
    public static final int STATE_SHOW_RESULT = 3;
    private JokeContract.Presenter presenter;
    private JokeContract.View view;

    private static MyApi myApiService = null;


    public ServiceAsync(JokeContract.Presenter presenter, JokeContract.View view) {
        this.presenter = presenter;
        this.view = view;
    }

    public ServiceAsync() {
    }

    @Override
    protected String doInBackground(Void... params) {
        publishProgress(STATE_LOADING);
        if (myApiService == null) {  // Only do this once
            myApiService = getApiBuilder().build();
        }

        try {
            String result = getJokeFromApi();
            if (!TextUtils.isEmpty(result)) {
                return result;
            } else {
                publishProgress(STATE_EMPTY);
            }
        } catch (IOException e) {
            publishProgress(STATE_EMPTY);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (view != null) {
            switch (values[0]) {
                case STATE_LOADING:
                    view.loadDownloadingScene();
                    break;
                case STATE_EMPTY:
                    view.loadEmptyScreen();
                    break;
            }
        }

    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null && !TextUtils.isEmpty(result)) {
            if (view != null) {
                view.loadJokeScene(result);

            }
        }
    }

    private MyApi.Builder getApiBuilder() {
        return new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl(Endpoints.LOCALE_URL)
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
    }

    private String getJokeFromApi() throws IOException {
        if (myApiService == null) {
            myApiService = getApiBuilder().build();
        }
        return myApiService.getJoke().execute().getData();
    }
}