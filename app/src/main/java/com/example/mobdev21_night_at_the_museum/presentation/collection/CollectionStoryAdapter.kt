package com.example.mobdev21_night_at_the_museum.presentation.collection

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobdev21_night_at_the_museum.R

//TODO: remove
data class Story(
    var title: String? = null,

    var description: String? = null,

    var thumbnail: String? = null,

    var collectionId: String? = null,

    var key: String? = null,

)

class CollectionStoryAdapter(private val storyList: List<Story>, private val context: Context) :
    RecyclerView.Adapter<CollectionStoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_story, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = storyList[position]
        holder.bind(story)
        holder.itemView.setOnClickListener(View.OnClickListener {
            Toast.makeText(context.getApplicationContext(), "Item Clicked: " + story.key, Toast.LENGTH_SHORT).show()
            // TODO: Create an Intent

        })
    }

    override fun getItemCount(): Int {
        return storyList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnailImgView: ImageView = itemView.findViewById(R.id.ivCollectionThumbnail)
        private val titleTextView: TextView = itemView.findViewById(R.id.tvCollectionTitle)

        fun bind(story: Story) {
            Glide.with(thumbnailImgView)
                .load(story.thumbnail)
                .placeholder(R.drawable.intro_bg) // Placeholder image
                .into(thumbnailImgView)

            titleTextView.text = story.title.toString()
        }
    }
}
