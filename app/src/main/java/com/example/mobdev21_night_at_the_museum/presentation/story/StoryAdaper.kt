package com.example.mobdev21_night_at_the_museum.presentation.story

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.domain.model.Story
import com.example.mobdev21_night_at_the_museum.presentation.story.storydetail.StoryDetail


class StoryAdaper(private val stories: ArrayList<Story>): RecyclerView.Adapter<StoryAdaper.ViewHolder>() {

    private var onClickListener: View.OnClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView
        val imageView : ImageView
        val tvStoryTitle : TextView

        init {
            // Define click listener for the ViewHolder's Vie
            imageView = view.findViewById(R.id.ivStoryThumbnail)
            tvStoryTitle = view.findViewById(R.id.tvStoryTitle)
//            textView = view.findViewById(.id.textView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.story_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stories[position]

        Glide.with(holder.imageView.context).load(item.thumbnail).signature(ObjectKey(System.currentTimeMillis())).into(holder.imageView);
        holder.tvStoryTitle.text = item.title

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, StoryDetail::class.java)
            intent.putExtra("position", position)
            intent.putExtra("storyDetails", ArrayList(item.storyDetailModels))
            context.startActivity(intent)
        }
    }

    // A function to bind the onclickListener.



}