package com.udacity.gradle.builditbigger.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arman.jokviewer.app.Constant;
import com.arman.jokviewer.ui.activity.JokActivity;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.contract.JokeContract;
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;
import com.udacity.gradle.builditbigger.presenter.MainPresenter;

/**
 * Created by Arman 2020
 */
public class MainActivityFragment extends Fragment implements JokeContract.View, View.OnClickListener {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    private static final int JOKE_ACTIVITY_REQ_CODE = 0;

    private FragmentMainBinding binding;

    private MainPresenter presenter;


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null) {
            presenter = new MainPresenter(this);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btTellJok.setOnClickListener(this);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //When returning from jokeActivity, show the joke layout and hide the progress bar
        if (resultCode == Activity.RESULT_CANCELED) {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btTellJok) {
            presenter.start();
            presenter.fetchFromRemoteSource();
        }

    }

    @Override
    public void loadDownloadingScene() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadEmptyScreen() {
        binding.progressBar.setVisibility(View.GONE);

    }

    @Override
    public void loadJokeScene(String joke) {
        binding.progressBar.setVisibility(View.GONE);
        startJokeActivity(joke);




    }

    private void startJokeActivity(String joke) {
        Intent intent = new Intent(getActivity(), JokActivity.class);
        intent.putExtra(Constant.EXTRA_JOKE, joke);
        intent.putExtra(Constant.JOKE_KEY, Constant.KEY_PAID);
        startActivityForResult(intent, JOKE_ACTIVITY_REQ_CODE);
    }

    @Override
    public void onDestroyView() {
        presenter.stop();
        super.onDestroyView();

    }
}
