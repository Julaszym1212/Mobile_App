
package com.game.a2048_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.game.module.Game;

import me.aflak.libraries.callback.FingerprintDialogCallback;
import me.aflak.libraries.dialog.FingerprintDialog;


// TODO: 19.07.2020 spojrzeć na bindingi

// TODO: 19.07.2020 spojrzeć na dao

public class MainActivity extends AppCompatActivity implements FingerprintDialogCallback {

    MainActivity mainActivity = this;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();
        loadData();
    }

    private void loadData(){
        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.settings), MODE_PRIVATE);
        boolean isDarkTheme = preferences.getBoolean(getResources().getString(R.string.darkTheme), false);
        setTheme(isDarkTheme);
    }

    // TODO: 19.07.2020 spojrzeć na te i podobne funkcje
    private void setTheme(boolean isDarkTheme) {
        ImageView darkThemeView = (ImageView) findViewById(R.id.darkThemeView);
        if (isDarkTheme == true) {
            darkThemeView.setImageResource(R.drawable.dark_theme_on);
        } else {
            darkThemeView.setImageResource(R.drawable.dark_theme_off);
        }
    }

    private void initButtons() {
        configureStartGameButton();
        configureAuthenticateButton();
    }

    private View.OnClickListener authentication = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (FingerprintDialog.isAvailable(mainActivity)) {
                FingerprintDialog.initialize(mainActivity)
                        .title(R.string.fingerprint_title)
                        .message(R.string.fingerprint_message)
                        .callback(mainActivity)
                        .show();
            }
        }
    };

    private View.OnClickListener initializeBoardActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, BoardActivity.class));
        }
    };

    private void configureStartGameButton() {
        Button startGame = (Button) findViewById(R.id.startGameButton);
        startGame.setBackgroundResource(R.drawable.main_activity_button);
        startGame.setOnClickListener(initializeBoardActivity);
    }

    private void configureAuthenticateButton() {
        Button authenticationButton = (Button) findViewById(R.id.authenticateButton);
        authenticationButton.setBackgroundResource(R.drawable.fingerprint);
        authenticationButton.setOnClickListener(authentication);
    }

    @Override
    public void onAuthenticationSucceeded() {
        Game.getInstance().setUserAuthenticated(true);
    }

    @Override
    public void onAuthenticationCancel() {
        // FIXME: 11.07.2020
    }


}

