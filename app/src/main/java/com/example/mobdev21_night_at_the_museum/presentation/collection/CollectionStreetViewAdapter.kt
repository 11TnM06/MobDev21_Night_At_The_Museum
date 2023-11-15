package com.example.mobdev21_night_at_the_museum.presentation.collection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobdev21_night_at_the_museum.R
import com.google.firebase.firestore.GeoPoint
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

//TODO: remove
data class StreetView(
    var name: String? = null,

    var place: String? = null,

    var location: @RawValue GeoPoint? = null,

    var thumbnail: String? = null,

    var key: String? = null

)

class CollectionStreetViewAdapter(private val streetViewList: List<StreetView>, private val context: Context) :
    RecyclerView.Adapter<CollectionStreetViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_streetview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val elem = streetViewList[position]
        holder.bind(elem)
        holder.itemView.setOnClickListener(View.OnClickListener {
            Toast.makeText(context.getApplicationContext(), "StreetView Clicked: " + elem.key, Toast.LENGTH_SHORT).show()
        })
    }

    override fun getItemCount(): Int {
        return streetViewList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnailImgView: ImageView = itemView.findViewById(R.id.ivCollectionStreetViewThumbnail)
        private val nameTextView: TextView = itemView.findViewById(R.id.tvCollectionStreetViewName)

        fun bind(st: StreetView) {
            Glide.with(thumbnailImgView)
                .load(st.thumbnail)
                .placeholder(R.drawable.ic_google) // Placeholder image
                .into(thumbnailImgView)

            nameTextView.text = st.name.toString()
        }
    }
}
