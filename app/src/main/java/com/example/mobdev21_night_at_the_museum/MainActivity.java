package com.example.mobdev21_night_at_the_museum;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobdev21_night_at_the_museum.presentation.intro.IntroductionActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent introductionScreen = new Intent(getApplicationContext(), IntroductionActivity.class);
        startActivity(introductionScreen);
    }
}