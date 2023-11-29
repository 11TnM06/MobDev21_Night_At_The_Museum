package com.example.mobdev21_night_at_the_museum.presentation.favorite

import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.AppPreferences
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.common.extension.getAppString
import com.example.mobdev21_night_at_the_museum.domain.model.*
import kotlinx.coroutines.launch

class FavoriteViewModel2 : BaseViewModel() {
    companion object {
        const val MAX_COUNT_ITEM = 4
        const val MAX_COUNT_STORY = 6
        const val MAX_COUNT_COLLECTION = 6
    }

//    val headerDisplay by lazy {
//        FavoriteAdapter.HeaderDisplay().apply {
//            avatarUrl = AppPreferences.getUserInfo().avatar
//        }
//    }
    var isFavorite = true
    val itemDisplay by lazy {
        FavoriteAdapter.ItemDisplay().apply {
            count = 0
            itemList = listOf()
        }
    }
    val storyDisplay by lazy {
        FavoriteAdapter.StoryDisplay().apply {
            count = 0
            storyList = listOf()
        }
    }
    val collectionDisplay by lazy {
        FavoriteAdapter.CollectionDisplay().apply {
            count = 0
            collectionList = listOf()
        }
    }
    val galleryEmpty by lazy { FavoriteAdapter.GalleryEmpty() }
//    val listFavorite by lazy { initFavoriteTabData() }
//    val listGallery by lazy { initGalleryTabData() }
    var items = listOf<Item>()
    var collections = listOf<MCollection>()
    var stories = listOf<Story>()
    var galleries = listOf<Gallery>()

    fun getFavoriteData(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        viewModelScope.launch {
            if (AppPreferences.getUserInfo().key == null) {
                onFailureAction("User key is null")
                return@launch
            }
            val itemListRef = Firebase.firestore
                .collection("users")
                .document(AppPreferences.getUserInfo().key!!)
            itemListRef.get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        val user = it.toObject(User::class.java)
                        user?.key = it.id
                        if (user != null) {
                            AppPreferences.setUserInfo(user)
                            getItemsListFromKeys(onSuccessAction, onFailureAction)
                        } else {
                            onFailureAction.invoke("User is null")
                        }
                    }
                }
                .addOnFailureListener {
                    onFailureAction.invoke(getAppString(R.string.fail) + ": ${it.message}")
                }
        }
    }

    fun getShortListFavorite(): MutableList<Any> {
        val list = mutableListOf<Any>()
        list.add(FavoriteAdapter.HeaderDisplay().apply {
            avatarUrl = AppPreferences.getUserInfo().avatar
            isFavoriteTab = isFavorite
        })
        list.add(itemDisplay.apply {
            count = items.size
            itemList = items.take(MAX_COUNT_ITEM)
        })
        list.add(storyDisplay.apply {
            count = stories.size
            storyList = stories.take(MAX_COUNT_STORY)
        })
        list.add(collectionDisplay.apply {
            count = collections.size
            collectionList = collections.take(MAX_COUNT_COLLECTION)
        })
        return list
    }

    fun getShortListGallery(): MutableList<Any> {
        val list = mutableListOf<Any>()
        list.add(FavoriteAdapter.HeaderDisplay().apply {
            avatarUrl = AppPreferences.getUserInfo().avatar
            isFavoriteTab = isFavorite
        })
        if (galleries.isNotEmpty()) {
            list.addAll(galleries)
        } else {
            list.add(galleryEmpty)
        }
        return list
    }

    private fun getItemsListFromKeys(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        val db = Firebase.firestore
        var itemListRef: Task<QuerySnapshot>? = null
        var storyRef: Task<QuerySnapshot>? = null
        var collectionRef: Task<QuerySnapshot>? = null
        var galleryRef: Task<QuerySnapshot>? = null
        val listTask = mutableListOf<Task<QuerySnapshot>>()

        if (!AppPreferences.getUserInfo().fitems.isNullOrEmpty()) {
            itemListRef = db.collection("items")
                .whereIn(FieldPath.documentId(), AppPreferences.getUserInfo().fitems!!).get()
            listTask.add(itemListRef)
        }

        if (!AppPreferences.getUserInfo().fstories.isNullOrEmpty()) {
            storyRef = db.collection("stories")
                .whereIn(FieldPath.documentId(), AppPreferences.getUserInfo().fstories!!).get()
            listTask.add(storyRef)
        }

        if (!AppPreferences.getUserInfo().fcollections.isNullOrEmpty()) {
            collectionRef = db.collection("collections")
                .whereIn(FieldPath.documentId(), AppPreferences.getUserInfo().fcollections!!).get()
            listTask.add(collectionRef)
        }

        if (AppPreferences.getUserInfo().key != null) {
            galleryRef = db
                .collection("users")
                .document(AppPreferences.getUserInfo().key!!)
                .collection("galleries")
                .orderBy("createTime", Query.Direction.DESCENDING)
                .get()
            listTask.add(galleryRef)
        }


        Tasks.whenAllSuccess<QuerySnapshot>(listTask)
            .continueWith {
                items = (itemListRef?.result?.documents ?: listOf()).mapNotNull {
                    it.toObject(Item::class.java)?.apply { key = it.id }
                }
                stories = (storyRef?.result?.documents ?: listOf()).mapNotNull {
                    it.toObject(Story::class.java)?.apply { key = it.id }
                }
                collections = (collectionRef?.result?.documents ?: listOf()).mapNotNull {
                    it.toObject(MCollection::class.java)?.apply { key = it.id }
                }
                galleries = (galleryRef?.result?.documents ?: listOf()).mapNotNull {
                    it.toObject(Gallery::class.java)?.apply {
                        key = it.id
                        items = (idOfItems ?: listOf()).mapNotNull { itemKey ->
                            this@FavoriteViewModel2.items.find { item -> item.key == itemKey }
                        }
                    }
                }
                onSuccessAction.invoke()
            }
            .addOnFailureListener {
                onFailureAction.invoke(getAppString(R.string.fail) + ": ${it.message}")
            }
    }

    private fun initFavoriteTabData(): MutableList<Any> {
        val list = mutableListOf<Any>()
        list.add(FavoriteAdapter.HeaderDisplay().apply {
            avatarUrl = AppPreferences.getUserInfo().avatar
        })
        list.add(itemDisplay)
        list.add(storyDisplay)
        list.add(collectionDisplay)
        return list
    }

    private fun initGalleryTabData(): MutableList<Any> {
        val list = mutableListOf<Any>()
        list.add(FavoriteAdapter.HeaderDisplay().apply {
            avatarUrl = AppPreferences.getUserInfo().avatar
        })
        list.add(galleryEmpty)
        return list
    }
}
