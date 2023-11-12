package com.example.mobdev21_night_at_the_museum.presentation.collection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobdev21_night_at_the_museum.R;
import com.example.mobdev21_night_at_the_museum.presentation.home.StreetViewAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CollectionActivity extends AppCompatActivity {
    RecyclerView storyView, itemView, streetView;
    LinearLayoutManager linearLayoutManager, linearLayoutManager2;
    GridLayoutManager gridLayoutManager;
    StreetViewAdapter streetAdapter;
    CollectionStoryAdapter collectionStoryAdapter;
    CollectionStreetViewAdapter collectionStreetViewAdapter;
    CollectionItemAdapter collectionItemAdapter;

    TextView tvMuseumName, tvMuseumPlace, tvMuseumDescription, tvReadMore, tvReadLess, tvCollectionStoriesViewAll;
    ImageView ivMuseumIcon, ivMuseumThumbnail;

    LinearLayout llCollectionHeaderFollow, llCollectionHeaderFollowing;
    ArrayList<Story> storyList;
    ArrayList<Item> itemList;
    ArrayList<StreetView> streetViewList;

    ArrayList<String> itemThumbnailList;

    String collectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        Intent i = getIntent();
        collectionId = i.getStringExtra("id");
        initializeViews();
        setupAdapters();
        setupHandlers();
        fetchDataFromFirestore();
    }

    private void initializeViews() {
        storyList = new ArrayList<Story>();
        streetViewList = new ArrayList<StreetView>();
        itemList = new ArrayList<Item>();
        itemThumbnailList = new ArrayList<>();

        tvMuseumName = findViewById(R.id.tvCollectionHeaderName);
        tvMuseumPlace = findViewById(R.id.tvCollectionHeaderPlace);
        tvMuseumDescription = findViewById(R.id.tvCollectionDescription);
        tvReadMore = findViewById(R.id.tvCollectionDescriptionReadMore);
        tvReadLess = findViewById(R.id.tvCollectionDescriptionReadLess);
        llCollectionHeaderFollow = findViewById(R.id.llCollectionHeaderFollow);
        llCollectionHeaderFollowing = findViewById(R.id.llCollectionHeaderFollowing);
        tvCollectionStoriesViewAll = findViewById(R.id.tvCollectionStoriesViewAll);


        ivMuseumIcon = findViewById(R.id.ivCollectionHeaderIcon);
        ivMuseumThumbnail = findViewById(R.id.ivCollectionHeaderThumbnail);

        storyView = findViewById(R.id.rvCollectionStories);
        streetView = findViewById(R.id.rvCollectionStreetView);
        itemView = findViewById(R.id.rvCollectionItems);
    }

    private void setupAdapters() {
        collectionStoryAdapter = new CollectionStoryAdapter(storyList, getApplicationContext());
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        storyView.setLayoutManager(linearLayoutManager);
        storyView.setAdapter(collectionStoryAdapter);

        collectionStreetViewAdapter = new CollectionStreetViewAdapter(streetViewList, getApplicationContext());
        linearLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        streetView.setLayoutManager(linearLayoutManager2);
        streetView.setAdapter(collectionStreetViewAdapter);

        collectionItemAdapter = new CollectionItemAdapter(itemList, getApplicationContext());
        gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        itemView.setLayoutManager(gridLayoutManager);
        itemView.setAdapter(collectionItemAdapter);

    }

    private void setupHandlers() {
        // Read Description
        tvReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMuseumDescription.setMaxLines(10);
                tvReadMore.setVisibility(View.GONE);
                tvReadLess.setVisibility(View.VISIBLE);

            }
        });

        tvReadLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMuseumDescription.setMaxLines(3);
                tvReadMore.setVisibility(View.VISIBLE);
                tvReadLess.setVisibility(View.GONE);
            }
        });

        // Following
        llCollectionHeaderFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llCollectionHeaderFollowing.setVisibility(View.VISIBLE);
                llCollectionHeaderFollow.setVisibility(View.GONE);
                //TODO: set to user
            }
        });

        llCollectionHeaderFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llCollectionHeaderFollow.setVisibility(View.VISIBLE);
                llCollectionHeaderFollowing.setVisibility(View.GONE);
                //TODO: set to user
            }
        });

        //story view all
        tvCollectionStoriesViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ViewAll click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDataFromFirestore() {
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("collections").document(collectionId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String thumbnailData = document.getString("thumbnail");
                    String iconData = document.getString("icon");

                    tvMuseumName.setText(document.getString("name"));
                    tvMuseumPlace.setText(document.getString("place"));
                    tvMuseumDescription.setText(document.getString("description"));

                    Glide.with(this)
                            .load(iconData)
                            .into(ivMuseumIcon);

                    Glide.with(this)
                            .load(thumbnailData)
                            .into(ivMuseumThumbnail);

                } else {
                    Log.d("Firestore Data", "No such document");
                }
            } else {
                Log.e("Firestore Data", "Error getting document: " + task.getException());
            }
        });

        db.collection("stories").whereEqualTo("collectionId", collectionId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot storyQuerySnapshot = task.getResult();

                for (QueryDocumentSnapshot document : storyQuerySnapshot) {
                    Story story = document.toObject(Story.class);
                    story.setKey(document.getId());

                    storyList.add(story);
                }
                collectionStoryAdapter.notifyDataSetChanged();
            } else {
                Log.e("Firebase Data", "Error getting documents: " + task.getException());
            }
        });

        db.collection("items").whereEqualTo("collectionId", collectionId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot storyQuerySnapshot = task.getResult();

                for (QueryDocumentSnapshot document : storyQuerySnapshot) {
                    Log.d("items_document", String.valueOf(document));
                    String thumbnailData = document.getString("thumbnail");
                    itemThumbnailList.add(thumbnailData);

                    Item item = document.toObject(Item.class);
                    item.setKey(document.getId());
                    itemList.add(item);
                }

                collectionItemAdapter.notifyDataSetChanged();

            } else {
                Log.e("Firebase Data", "Error getting documents: " + task.getException());
            }
        });

        db.collection("streetview").whereEqualTo("collectionId", collectionId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot storyQuerySnapshot = task.getResult();

                for (QueryDocumentSnapshot document : storyQuerySnapshot) {
                    StreetView streetview = document.toObject(StreetView.class);
                    streetview.setKey(document.getId());

                    streetViewList.add(streetview);
                }
                collectionStreetViewAdapter.notifyDataSetChanged();


            } else {
                Log.e("Firebase Data", "Error getting documents: " + task.getException());
            }
        });
    }

}