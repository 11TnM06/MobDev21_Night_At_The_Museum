package com.example.mobdev21_night_at_the_museum.presentation.item;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobdev21_night_at_the_museum.R;
import com.example.mobdev21_night_at_the_museum.databinding.ItemBinding;
import com.example.mobdev21_night_at_the_museum.model.Item;
import com.example.mobdev21_night_at_the_museum.model.ItemDetail;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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

        /** Get Data & Binding **/
        ItemBinding itemBinding = DataBindingUtil.setContentView(this, R.layout.item);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items").child("02fyOHMaLhrXm2ZYx3aX");

        Item mockItem = new Item().mockItem();
        dataBindingItem(itemBinding, mockItem);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // The item with the specified itemID exists
                    // You can access the item data using dataSnapshot.getValue()
                    Item item = dataSnapshot.getValue(Item.class); // Item is your data model class

                    // Use the retrieved item as needed
                    Log.d("FIRE", "onDataChange: " + item.getName());
                    Log.d("FIRE", "onDataChange: " + item.getCreatorName());
//                    dataBindingItem(itemBinding, item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FIRE ERROR", "The read failed: " + error.getCode());
            }
        });

        // Read more
        TextView tvItemDescription = findViewById(R.id.tvItemDescription);
        TextView tvItemDescriptionReadMore = findViewById(R.id.tvItemDescriptionReadMore);
        tvItemDescriptionReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvItemDescriptionReadMore.getText() == getApplication().getString(R.string.read_more)) {
                    tvItemDescription.setMaxLines(Integer.MAX_VALUE);
                    tvItemDescriptionReadMore.setText(getApplication().getString(R.string.read_less));
                } else {
                    tvItemDescription.setMaxLines(3);
                    tvItemDescriptionReadMore.setText(getApplication().getString(R.string.read_more));
                }
            }
        });

        // Detail
        FrameLayout flItemDetailTitleContainer = findViewById(R.id.flItemDetailTitleContainer);
        TextView tvbsDetail = findViewById(R.id.tvbsDetail);
        flItemDetailTitleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvbsDetail.getVisibility() == View.GONE) {
                    tvbsDetail.setVisibility(View.VISIBLE);
                } else {
                    tvbsDetail.setVisibility(View.GONE);
                }
            }
        });
    }

    private void dataBindingItem(ItemBinding itemBinding, Item mockItem) {
        itemBinding.setItem(mockItem);

        // Update item image
        ImageView imageView = findViewById(R.id.ivItemImage);
        String imageUrl = mockItem.getThumbnail();
        Picasso.get().load(imageUrl).into(imageView);

        // Update Detail in bottom sheet
        TextView tvbsDetail = findViewById(R.id.tvbsDetail);
        String tvDetail = "";
        for (ItemDetail itemDetail: mockItem.getDetails()) {
            tvDetail += itemDetail.toString();
        }
        tvbsDetail.setText(Html.fromHtml(tvDetail));
    }
}
