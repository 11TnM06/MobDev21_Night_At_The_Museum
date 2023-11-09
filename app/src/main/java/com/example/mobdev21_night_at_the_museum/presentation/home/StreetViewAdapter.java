package com.example.mobdev21_night_at_the_museum.presentation.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobdev21_night_at_the_museum.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StreetViewAdapter extends RecyclerView.Adapter<StreetViewAdapter.MyHolder> {
    private ArrayList<DocumentSnapshot> museum;
    private Context context;

    // Define an interface for the item click listener
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public StreetViewAdapter(ArrayList<DocumentSnapshot> museum, Context context) {
        this.museum = museum;
        this.context = context;
    }

    // Set the click listener for the RecyclerView items
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_street_view_sub_item2, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (museum.size() != 0) {
            String name = museum.get(position).get("museumName").toString();
            String place = museum.get(position).get("place").toString();
            String imageUrl = museum.get(position).get("thumbnail").toString();

            holder.tvHomeStreetViewName.setText(name);
            holder.tvHomeStreetViewPlace.setText(place);
            Picasso.get().load(imageUrl).into(holder.ivHomeStreetViewThumbnail);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create an Intent to start your target activity
//                    Intent intent = new Intent(context, IntroductionActivity.class);
//
//                    // Add any data you want to pass to the target activity
//                    intent.putExtra("item_name", );
//                    intent.putExtra("item_place", place.get(holder.getAdapterPosition()));
//
//                    context.startActivity(intent);
                    Toast.makeText(context.getApplicationContext(), "Item clicked" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Set the click listener for the item
    }


    @Override
    public int getItemCount() {
        return museum.size();
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
