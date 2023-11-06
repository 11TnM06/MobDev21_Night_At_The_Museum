package com.example.mobdev21_night_at_the_museum.presentation.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobdev21_night_at_the_museum.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.CollectionHolder> {
    private ArrayList<String> picCollections;

    public CollectionsAdapter(ArrayList<String> picCollections) {
        this.picCollections = picCollections;
    }

    @NonNull
    @Override
    public CollectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_you_may_like_item, parent, false);
        return new CollectionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionHolder holder, int position) {
        if (picCollections.size() != 0) {
            String imageUrl = picCollections.get(position);
            Picasso.get().load(imageUrl).into(holder.ivPicCollection);
        }
    }

    @Override
    public int getItemCount() {
        return picCollections.size();
    }

    static class CollectionHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        com.google.android.material.imageview.ShapeableImageView ivPicCollection;

        public CollectionHolder(@NonNull View itemView) {
            super(itemView);
            ivPicCollection = itemView.findViewById(R.id.ivPicCollection);
        }
    }
}
