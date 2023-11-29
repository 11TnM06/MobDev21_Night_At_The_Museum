package com.example.mobdev21_night_at_the_museum.presentation.collection

import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.AppPreferences
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.common.extension.getApplication
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.domain.model.User

class CollectionViewModel : BaseViewModel() {
    var collectionId: String? = null
    var list = mutableListOf<Any>()

    var count = 0

    fun getCollectionData(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        if (collectionId == null) {
            onFailureAction.invoke("Collection id is null")
        }

        list.clear()

        val collectionRef = Firebase.firestore.collection("collections")
            .document(collectionId!!)

        collectionRef.get()
            .addOnSuccessListener {
                val collection = it.toObject(MCollection::class.java)?.apply { key = it.id }

                if (collection != null) {
                    list.add(CollectionAdapter.HeaderDisplay().apply {
                        this.collection = collection
                    })
                    list.add(CollectionAdapter.DescriptionDisplay(collection.description))
                    list.add(CollectionAdapter.StoriesDisplay(collectionId))
                    getItemsData(onSuccessAction)
                } else {
                    onFailureAction.invoke("Collection is null")
                }
            }
            .addOnFailureListener {
                onFailureAction.invoke(it.message ?: "Get collection failed")
            }
    }

    fun follow(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        updateFollowInDatabase(true, onSuccessAction, onFailureAction)
    }

    fun unFollow(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        updateFollowInDatabase(false, onSuccessAction, onFailureAction)
    }

    private fun updateFollowInDatabase(isAdd: Boolean, onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        if (AppPreferences.getUserInfo().key == null) {
            throw Exception("User key is null")
        }
        if (collectionId == null) {
            throw Exception("Collection id is null")
        }

        val followRef = Firebase.firestore.collection("users")
            .document(AppPreferences.getUserInfo().key!!)

        val followTask: Task<Void> = if (isAdd) {
            followRef.update("fcollections", FieldValue.arrayUnion(collectionId!!))
        } else {
            followRef.update("fcollections", FieldValue.arrayRemove(collectionId!!))
        }

        followTask.addOnSuccessListener {
            Firebase.firestore
                .collection("users")
                .document(AppPreferences.getUserInfo().key!!).get()
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)?.apply { key = it.id }
                    AppPreferences.setUserInfo(user!!)

                    val newHeaderDisplay = (list.first() as CollectionAdapter.HeaderDisplay).copy()
                    list[0] = newHeaderDisplay
                    onSuccessAction.invoke()
                }
                .addOnFailureListener {
                    onFailureAction.invoke(it.message ?: "Follow/Unfollow failed")
                }
        }.addOnFailureListener {
            onFailureAction.invoke(it.message ?: "Follow/Unfollow failed")
        }
    }

    private fun getItemsData(onSuccessAction: () -> Unit) {
        val db = Firebase.firestore
        val storiesRef = db.collection("items").whereEqualTo("collectionId", collectionId)
        storiesRef.get().addOnSuccessListener { storiesSnapshot ->
            val items = storiesSnapshot.documents.mapNotNull {
                it.toObject(CollectionItemAdapter.ItemsDisplay::class.java)?.apply { id = it.id }
            }

            count = items.size

            if (count == 0) {
                onSuccessAction.invoke()
            } else {
                items.forEach {
                    Glide.with(getApplication()).load(it.thumbnail).listener(object : RequestListener<Drawable> {
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
                                count = items.size
                                list.add(CollectionAdapter.ItemsDisplay(collectionId, items))
                                onSuccessAction.invoke()
                            }
                        }
                    }).submit()
                }
            }
        }
    }
}
