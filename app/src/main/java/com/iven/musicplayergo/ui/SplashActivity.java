package com.iven.musicplayergo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.iven.musicplayergo.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 5000;
    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //initializing the Google Admob SDK
        MobileAds.initialize(this, initializationStatus -> {

        });

        startSplash();
    }

    private void loadInterAd() {

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(SplashActivity.this,
                "ca-app-pub-6074653133300509/1051038191",
                adRequest,
                new InterstitialAdLoadCallback() {

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd i) {
                        super.onAdLoaded(i);

                        interstitialAd = i;

                        interstitialAd.show(SplashActivity.this);

                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                super.onAdClicked();
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            }
                        });
                    }
                });
    }

    private void startSplash() {

        Thread splashThread = new Thread() {

            @Override
            public void run() {
                try {
                    sleep(SPLASH_DURATION);

                    runOnUiThread(() -> loadInterAd());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        splashThread.start();
    }
}
