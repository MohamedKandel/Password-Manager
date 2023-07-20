package com.Mkandeel.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class Loading_Screen extends AppCompatActivity {

    @BindView(R.id.img_load)
    GifImageView imgLoad;

    private final int SPLASH_SCREEN_TIME = 3000;
    private Functions functions;
    private State state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadin__screen);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        functions = new Functions(this);
        state = new State(this);

        boolean isDark = state.getState();
        if (isDark) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            imgLoad.setImageResource(R.drawable.load);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            imgLoad.setImageResource(R.drawable.light);
        }

        String username = getIntent().getStringExtra("name");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Loading_Screen.this,Account_List.class);
                intent.putExtra("name",username);
                startActivity(intent);
                //finish();
                finishAffinity();
            }
        },SPLASH_SCREEN_TIME);
    }
}