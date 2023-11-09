package com.example.mobdev21_night_at_the_museum.presentation.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobdev21_night_at_the_museum.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.CollectionHolder> {
    private ArrayList<DocumentSnapshot> picCollections;
    Context context;

    public CollectionsAdapter(ArrayList<DocumentSnapshot> picCollections, Context context) {
        this.picCollections = picCollections;
        this.context = context;
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
            String thumbnailURL = picCollections.get(position).get("thumbnail").toString();
            Picasso.get().load(thumbnailURL).into(holder.ivPicCollection);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context.getApplicationContext(), "Item clicked" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
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
