package com.example.mobdev21_night_at_the_museum.presentation.home;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobdev21_night_at_the_museum.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    RecyclerView museumView, collectionView;
    LinearLayoutManager linearLayoutManager, linearLayoutManager1;

    StreetViewAdapter streetAdapter;
    CollectionsAdapter collectionsAdapter;
    CollectionReference collection;
    ArrayList<String> museumImg, museumName, museumPlace;
    ArrayList<String> picCollections;
    ArrayList<ImageView> homeCollections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_home_activity);

        //Init arrs
        museumImg = new ArrayList<>();
        museumName = new ArrayList<>();
        museumPlace = new ArrayList<>();
        picCollections = new ArrayList<>();
        homeCollections = new ArrayList<>();

        //Museum you may like
        homeCollections.add(findViewById(R.id.ivHomeCollection1));
        homeCollections.add(findViewById(R.id.ivHomeCollection2));
        homeCollections.add(findViewById(R.id.ivHomeCollection3));
        homeCollections.add(findViewById(R.id.ivHomeCollection4));
        homeCollections.add(findViewById(R.id.ivHomeCollection5));


        //Items you may like
        collectionView = findViewById(R.id.rvCollectionsStories); // RecyclerView for collections
        museumView = findViewById(R.id.rvMuseumViews); //RecyclerView for museums

        //Setup Collections Adapters
        collectionsAdapter = new CollectionsAdapter(picCollections);
        linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        collectionView.setLayoutManager(linearLayoutManager1);
        collectionView.setAdapter(collectionsAdapter);

        // Setup MuseumView Adapters
        streetAdapter = new StreetViewAdapter(museumName, museumPlace, museumImg);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        museumView.setLayoutManager(linearLayoutManager);
        museumView.setAdapter(streetAdapter);

        // Fetch data from Firestore
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("collections").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int documentCount = task.getResult().size();
                    Log.d("Firestore Data", "Document count: " + documentCount);

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String thumbnailData = (document.get("thumbnail")).toString();
                        picCollections.add(thumbnailData);
                    }

                    collectionsAdapter.notifyDataSetChanged();
                    loadFavCollections(picCollections, homeCollections);
                } else {
                    Log.e("Firestore Data", "Error getting documents: " + task.getException());
                }
            }
        });
        db.collection("exhibitions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int documentCount = task.getResult().size();
                    Log.d("Firestore Data", "Document count: " + documentCount);

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String thumbnailData = (document.get("thumbnail")).toString();
                        String nameData = (document.get("name")).toString();
                        String placeData = (document.get("place")).toString();
                        museumImg.add(thumbnailData);
                        museumName.add(nameData);
                        museumPlace.add(placeData);
                    }

                    streetAdapter.notifyDataSetChanged();

                } else {
                    Log.e("Firestore Data", "Error getting documents: " + task.getException());
                }
            }
        });

    }

    public void loadFavCollections(ArrayList<String> thumbnail, ArrayList<ImageView> homeCollections) {
        for (int i = 0; i < 5; i++) {
            Picasso.get().load(thumbnail.get(i)).into(homeCollections.get(i));
        }
    }

}