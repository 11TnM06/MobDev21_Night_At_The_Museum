package com.example.mobdev21_night_at_the_museum.presentation.item;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobdev21_night_at_the_museum.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {
    private BottomSheetBehavior bottomSheetBehavior;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        // Bottom Sheet
        LinearLayout bottomSheetLayout = findViewById(R.id.sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // Horizontal RecycleView -> list action buttons
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView = findViewById(R.id.rvItemActions);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        List<Pair<Integer, Integer>> listActionItems = new ArrayList<>();
        Pair<Integer, Integer> button1 = new Pair<>(R.drawable.ic_zoom_in, R.string.zoom_in);
        Pair<Integer, Integer> button2 = new Pair<>(R.drawable.ic_ar, R.string.view_in_ar);
        Pair<Integer, Integer> button3 = new Pair<>(R.drawable.ic_street_view_sized, R.string.street_view);
        listActionItems.add(button1);
        listActionItems.add(button2);
        listActionItems.add(button3);
        Log.d("TAG", "onCreate: " + listActionItems.toString());
        recyclerView.setAdapter(new MyRecycleViewAdapter(listActionItems));
    }
}
