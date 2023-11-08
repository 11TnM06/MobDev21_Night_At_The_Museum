package com.example.mobdev21_night_at_the_museum.presentation.home;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobdev21_night_at_the_museum.R;
import com.google.firebase.FirebaseApp;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView museumView, collectionView;
    LinearLayoutManager linearLayoutManager, linearLayoutManager1;
    StreetViewAdapter streetAdapter;
    CollectionsAdapter collectionsAdapter;
    ArrayList<String> museumImg, museumName, museumPlace;
    ArrayList<String> picCollections;
    ArrayList<ImageView> homeCollections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_home_activity);

        initializeViews();
        setupAdapters();
        fetchDataFromFirestore();
    }

    private void initializeViews() {
        museumImg = new ArrayList<>();
        museumName = new ArrayList<>();
        museumPlace = new ArrayList<>();
        picCollections = new ArrayList<>();
        homeCollections = new ArrayList<>();

        homeCollections.add(findViewById(R.id.ivHomeCollection1));
        homeCollections.add(findViewById(R.id.ivHomeCollection2));
        homeCollections.add(findViewById(R.id.ivHomeCollection3));
        homeCollections.add(findViewById(R.id.ivHomeCollection4));
        homeCollections.add(findViewById(R.id.ivHomeCollection5));

        collectionView = findViewById(R.id.rvCollectionsStories);
        museumView = findViewById(R.id.rvMuseumViews);
    }

    private void setupAdapters() {
        collectionsAdapter = new CollectionsAdapter(picCollections);
        linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        collectionView.setLayoutManager(linearLayoutManager1);
        collectionView.setAdapter(collectionsAdapter);

        streetAdapter = new StreetViewAdapter(museumName, museumPlace, museumImg);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        museumView.setLayoutManager(linearLayoutManager);
        museumView.setAdapter(streetAdapter);
    }

    private void fetchDataFromFirestore() {
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("collections").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String thumbnailData = document.getString("thumbnail");
                    picCollections.add(thumbnailData);
                }
                collectionsAdapter.notifyDataSetChanged();
                loadFavCollections(picCollections, homeCollections);
            } else {
                Log.e("Firestore Data", "Error getting documents: " + task.getException());
            }
        });

        db.collection("exhibitions").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String thumbnailData = document.getString("thumbnail");
                    String nameData = document.getString("name");
                    String placeData = document.getString("place");
                    museumImg.add(thumbnailData);
                    museumName.add(nameData);
                    museumPlace.add(placeData);
                }
                streetAdapter.notifyDataSetChanged();
            } else {
                Log.e("Firestore Data", "Error getting documents: " + task.getException());
            }
        });
    }

    public void loadFavCollections(ArrayList<String> thumbnail, ArrayList<ImageView> homeCollections) {
        for (int i = 0; i < 5 && i < thumbnail.size() && i < homeCollections.size(); i++) {
            Picasso.get().load(thumbnail.get(i)).into(homeCollections.get(i));
        }
    }
}
