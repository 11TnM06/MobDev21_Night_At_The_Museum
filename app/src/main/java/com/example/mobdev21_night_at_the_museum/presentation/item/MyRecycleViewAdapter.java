package com.example.mobdev21_night_at_the_museum.presentation.item;

import com.example.mobdev21_night_at_the_museum.R;

import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {

    List<Pair<Integer, Integer>> listActionItems;

    public MyRecycleViewAdapter(List<Pair<Integer, Integer>> listActionItems) {
        this.listActionItems = listActionItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Drawable img = holder.tvActionItem.getContext().getResources().getDrawable( listActionItems.get(position).first);
        img.setBounds(0, 0, 0, 0);
        holder.tvActionItem.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        holder.tvActionItem.setText(listActionItems.get(position).second);
    }

    @Override
    public int getItemCount() {
        return listActionItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvActionItem;
        ImageView ivActionItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActionItem = itemView.findViewById(R.id.tvActionItem);
//            ivActionItem = itemView.findViewById(R.id.ivActionItem);
        }
    }
}
