package com.arman.jokviewer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.arman.jokviewer.databinding.ActivityJokBinding;

import static com.arman.jokviewer.app.Constant.EXTRA_JOKE;
import static com.arman.jokviewer.app.Constant.JOKE_KEY;
import static com.arman.jokviewer.app.Constant.KEY_PAID;

public class JokActivity extends AppCompatActivity {


    ActivityJokBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJokBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setUpActionBar();

        Intent intent = getIntent();

        if (!intentHasJoke(intent)) {
            finish();
            return;
        }

        String joke = intent.getStringExtra(JOKE_KEY);

        setJokToTextView(joke);



    }


    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private boolean intentHasJoke(Intent intent) {
        return intent != null && intent.hasExtra(EXTRA_JOKE);
    }

    private void setJokToTextView(String joke) {
        if (!TextUtils.isEmpty(joke) && joke.equals(KEY_PAID)){
            binding.tvJok.setText(joke);
        }else {
            binding.tvJok.setText(R.string.msg_jok_not_available);
        }
    }
}