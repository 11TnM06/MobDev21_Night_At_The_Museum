package com.example.mobdev21_night_at_the_museum.presentation.story.storydetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.domain.model.StoryDetailModel

class StoryDetailAdapter (private val storyDetailModels : ArrayList<StoryDetailModel>) : RecyclerView.Adapter<StoryDetailAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //        val textView: TextView
        val imageView : ImageView
//        val tvStoryTitle : TextView

        init {
            // Define click listener for the ViewHolder's Vie
            imageView = view.findViewById(R.id.story_detail_image)
//            tvStoryTitle = view.findViewById(R.id.tvStoryTitle)
//            textView = view.findViewById(.id.textView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.strory_detail_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = storyDetailModels[position]
        Glide.with(holder.imageView.context).load(item.thumbnail).signature(ObjectKey(System.currentTimeMillis())).into(holder.imageView);


    }


    override fun getItemCount(): Int {
        return storyDetailModels.size
    }



}