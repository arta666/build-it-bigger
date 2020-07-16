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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.contract.JokeContract;
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;
import com.udacity.gradle.builditbigger.presenter.MainPresenter;



import static com.udacity.gradle.builditbigger.R.string.interstitial_ad_unit_id;


/**
 * Created by Arman 2020
 */
public class MainActivityFragment extends Fragment implements JokeContract.View, View.OnClickListener {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    private static final int JOKE_ACTIVITY_REQ_CODE = 0;

    private FragmentMainBinding binding;

    private MainPresenter presenter;

    InterstitialAd mInterstitialAd;

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
        View view = binding.getRoot();
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(interstitial_ad_unit_id));
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btTellJok.setOnClickListener(this);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                presenter.start();
                presenter.fetchFromRemoteSource();
            }
        });

        requestNewInterstitial();


    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
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
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            startJokeActivity(joke);
        }




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
