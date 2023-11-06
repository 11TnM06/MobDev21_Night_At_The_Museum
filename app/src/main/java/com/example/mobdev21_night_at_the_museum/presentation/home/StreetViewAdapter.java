package com.example.mobdev21_night_at_the_museum.presentation.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobdev21_night_at_the_museum.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class StreetViewAdapter extends RecyclerView.Adapter<StreetViewAdapter.MyHolder> {
    private ArrayList<String> name;
    private ArrayList<String> place;
    private ArrayList<String> thumbnailUrls;

    public StreetViewAdapter(ArrayList<String> name, ArrayList<String> place, ArrayList<String> thumbnailUrls) {
        this.name = name;
        this.place = place;
        this.thumbnailUrls = thumbnailUrls;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_street_view_sub_item2, parent, false);
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

    static class MyHolder extends RecyclerView.ViewHolder {
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
