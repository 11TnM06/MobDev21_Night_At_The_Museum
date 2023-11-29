package com.example.mobdev21_night_at_the_museum.presentation.home

import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.AppPreferences
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.common.extension.getApplication
import com.example.mobdev21_night_at_the_museum.domain.model.HomeData
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.domain.model.StreetView

class HomeViewModel : BaseViewModel() {
    var homeData: HomeData? = null
    var list = mutableListOf<Any>()
    var items: List<ItemYouMayLikeAdapter.ItemsDisplay>? = null

    fun getHomeData(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        val homeDataRef = Firebase.firestore
            .collection("home")
            .orderBy(FieldPath.documentId())
            .limit(1)
        homeDataRef.get().addOnSuccessListener { homeSnapShot ->
            this.homeData = homeSnapShot.documents.firstOrNull()?.toObject(HomeData::class.java)

            val listTask = mutableListOf<Task<QuerySnapshot>>()

            val streetViewRef = Firebase.firestore
                .collection("home/${homeSnapShot.documents.first().id}/streetViews")
                .whereNotEqualTo("place", null)
                .limit(4).get()

            val collectionRef = Firebase.firestore
                .collection("collections")
                .orderBy(FieldPath.documentId())
                .limit(5).get()

            val fitems = AppPreferences.getUserInfo().fitems ?: emptyList()
            val itemRef = if (fitems.isNotEmpty()) {
                Firebase.firestore
                    .collection("items")
                    .whereNotIn(FieldPath.documentId(), AppPreferences.getUserInfo().fitems ?: emptyList())
                    .limit(10).get()
            } else {
                Firebase.firestore
                    .collection("items")
                    .limit(10).get()
            }

            listTask.add(streetViewRef)
            listTask.add(collectionRef)
            listTask.add(itemRef)

            Tasks.whenAllSuccess<QuerySnapshot>(listTask)
                .continueWith {
                    // collection
                    homeData?.collections = collectionRef.result.documents.mapNotNull { document ->
                        document.toObject(MCollection::class.java)?.apply { key = document.id }
                    }
                    list.add(HomeAdapter.CollectionDisplay().apply { collections = homeData?.collections })

                    // street view
                    homeData?.streetViews = streetViewRef.result.documents.mapNotNull { document ->
                        document.toObject(StreetView::class.java)
                    }
                    list.add(HomeAdapter.StreetViewDisplay().apply { streetViews = homeData?.streetViews })

                    // item you may like
                    items = itemRef.result.documents.mapNotNull {
                        val item = it.toObject(Item::class.java)?.apply { key = it.id }
                        ItemYouMayLikeAdapter.ItemsDisplay(item)
                    }
                    list.add(HomeAdapter.ItemYouMayLikeDisplay().apply { this.items = this@HomeViewModel.items })

                    preLoadImage(onSuccessAction)
                }
                .addOnFailureListener {
                    onFailureAction.invoke(it.message ?: "Error")
                }
        }
    }

    private fun getItemDataFromDb() {
        val db = Firebase.firestore

        // get item that have not been liked by user


    }

    private fun preLoadImage(onSuccessAction: () -> Unit) {
        var count = items?.size ?: 0

        if (count == 0) {
            onSuccessAction.invoke()
        } else {
            items?.forEach {
                Glide.with(getApplication()).load(it.item?.thumbnail).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        updateCount()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        if (resource != null) {
                            it.ratio = resource.intrinsicWidth / resource.intrinsicHeight.toFloat()
                        }
                        updateCount()
                        return false
                    }

                    private fun updateCount() {
                        count--
                        if (count == 0) {
                            count = items?.size ?: 0
                            onSuccessAction.invoke()
                        }
                    }
                }).submit()
            }
        }
    }
}
