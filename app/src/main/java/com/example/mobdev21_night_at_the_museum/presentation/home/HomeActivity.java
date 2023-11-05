package com.example.mobdev21_night_at_the_museum.presentation.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    RecyclerView rv;
    RecyclerView cv;
    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager linearLayoutManager1;
    StreetViewAdapter streetAdapter;
    CollectionsAdapter collectionsAdapter;
    CollectionReference collection;
    ArrayList<String> museumImg, museumName, museumPlace;
    ArrayList<String> picCollections;
    ArrayList<ImageView> homeCollections = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_home_activity);

        homeCollections.add(findViewById(R.id.ivHomeCollection1));
        homeCollections.add(findViewById(R.id.ivHomeCollection2));
        homeCollections.add(findViewById(R.id.ivHomeCollection3));
        homeCollections.add(findViewById(R.id.ivHomeCollection4));
        homeCollections.add(findViewById(R.id.ivHomeCollection5));
        museumImg = new ArrayList<>();
        museumName = new ArrayList<>();
        museumPlace = new ArrayList<>();
        picCollections = new ArrayList<>();
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        cv = findViewById(R.id.rvCollectionsStories); // RecyclerView for collections
        rv = findViewById(R.id.horizontalRv);

        collectionsAdapter = new CollectionsAdapter(picCollections);
        linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        cv.setLayoutManager(linearLayoutManager1);
        cv.setAdapter(collectionsAdapter);

        // Initialize the StreetViewAdapter with an empty dataList
        streetAdapter = new StreetViewAdapter(museumName, museumPlace, museumImg);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(streetAdapter);
        // Fetch data from Firestore
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

                    // Notify the adapter that data has changed
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

                    // Notify the adapter that data has changed
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

    class StreetViewAdapter extends RecyclerView.Adapter<StreetViewAdapter.MyHolder> {
        ArrayList<String> name;
        ArrayList<String> place;
        ArrayList<String> thumbnailUrls;

        public StreetViewAdapter(ArrayList<String> name, ArrayList<String> place, ArrayList<String> thumbnailUrls) {
            this.name = name;
            this.place = place;
            this.thumbnailUrls = thumbnailUrls;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.home_street_view_sub_item2, null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {

            if (thumbnailUrls.size() != 0 && name.size() != 0 && place.size() != 0) {
                holder.tvHomeStreetViewName.setText(name.get(position));
                holder.tvHomeStreetViewPlace.setText(place.get(position));
                String imageUrl = thumbnailUrls.get(position);
                Picasso.get().load(imageUrl).into(holder.ivHomeStreetViewThumbnail);
            }

        }

        @Override
        public int getItemCount() {
            return name.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView tvHomeStreetViewName;
            TextView tvHomeStreetViewPlace;
            ImageView ivHomeStreetViewThumbnail;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                tvHomeStreetViewName = itemView.findViewById(R.id.tvHomeStreetViewName);
                tvHomeStreetViewPlace = itemView.findViewById(R.id.tvHomeStreetViewPlace);
                ivHomeStreetViewThumbnail = itemView.findViewById(R.id.ivHomeStreetViewThumbnail);
            }
        }

    }

    class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.CollectionHolder> {
        ArrayList<String> picCollections;

        public CollectionsAdapter(ArrayList<String> picCollections) {
            this.picCollections = picCollections;

        }

        @NonNull
        @Override
        public CollectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.home_item_you_may_like_item, null);
            return new CollectionHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CollectionHolder holder, int position) {
            if (picCollections.size() != 0) {

                String imageUrl = picCollections.get(position);
                // Load and display the image using Picasso
                Picasso.get().load(imageUrl).into(holder.ivPicCollection);
            }


        }

        @Override
        public int getItemCount() {
            return picCollections.size();
        }

        class CollectionHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            com.google.android.material.imageview.ShapeableImageView ivPicCollection;

            public CollectionHolder(@NonNull View itemView) {
                super(itemView);
                ivPicCollection = itemView.findViewById(R.id.ivPicCollection);
            }
        }

    }
}
