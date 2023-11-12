package com.example.mobdev21_night_at_the_museum.presentation.collection

import android.R.attr.button
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobdev21_night_at_the_museum.R
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.firebase.firestore.GeoPoint
import kotlinx.parcelize.RawValue
import android.content.Context


//TODO: remove. need move to model
data class Item(
    var name: String? = null,

    var thumbnail: String? = null,

    var time: String? = null,

    var description: String? = null,

    var streetView: @RawValue GeoPoint? = null,

    var model3d: String? = null,

    var collectionId: String? = null,

//    var collection: MCollection? = null,

    var creatorId: String? = null,

    var creatorName: String? = null,

//    var creator: Creator? = null,

//    @Deprecated("Use fun safeIsLiked() key instead")
//    var isLiked: Boolean? = null,

//    var details: List<ItemDetail>? = null

    var key: String? = null,

) {
}

class CollectionItemAdapter(private val itemList: List<Item>, private val context: Context) :
    RecyclerView.Adapter<CollectionItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener(View.OnClickListener {
            Toast.makeText(context.getApplicationContext(), "Item Clicked: " + item.key, Toast.LENGTH_SHORT).show()
        })

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val thumbnailImgView1: ImageView = itemView.findViewById(R.id.ivCollectionItem1)

        fun bind(item: Item) {
            Glide.with(thumbnailImgView1).load(item.thumbnail).placeholder(R.drawable.intro_bg).into(thumbnailImgView1)
        }
    }
}
