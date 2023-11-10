package com.example.mobdev21_night_at_the_museum;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.mobdev21_night_at_the_museum.databinding.ItemBottomSheetBinding;
import com.example.mobdev21_night_at_the_museum.model.Item;
import com.example.mobdev21_night_at_the_museum.presentation.item.ItemActivity;

import com.example.mobdev21_night_at_the_museum.presentation.intro.IntroductionActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent introductionScreen = new Intent(getApplicationContext(), IntroductionActivity.class);
        startActivity(introductionScreen);

//        Intent intent = new Intent(this, ItemActivity.class);
//        startActivity(intent);
    }
}