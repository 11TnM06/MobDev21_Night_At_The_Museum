package com.example.mobdev21_night_at_the_museum.presentation.adddata

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.common.extension.getApplication
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.domain.model.Page
import com.example.mobdev21_night_at_the_museum.domain.model.Story
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class AddDataViewModel : BaseViewModel() {

    var itemList: MutableList<Item> = mutableListOf()
    var streetViewList: List<StreetView> = listOf()
    var storyList: List<Story> = listOf()

    fun addData() {
        viewModelScope.launch {
            mapItemList()
            performAddToDatabase()
        }
    }

    data class StreetViewGson(
        var name: String? = null,

        var lat: Double? = null,

        var lng: Double? = null,

        var thumbnail: String? = null
    )

    data class StreetView(
        var name: String? = null,

        var location: GeoPoint? = null,

        var thumbnail: String? = null
    )

    fun addStreetViewList() {
        viewModelScope.launch {
            val inputStream = getApplication().resources.openRawResource(R.raw.street_view_1)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val json = reader.readText()
            streetViewList = Gson().fromJson(json, Array<StreetViewGson>::class.java).toList().map {
                StreetView(
                    name = it.name,
                    location = GeoPoint(it.lat ?: 0.0, it.lng ?: 0.0),
                    thumbnail = it.thumbnail
                )
            }

            val db = Firebase.firestore
            val batch = db.batch()

            streetViewList.take(1).forEach {
                val docRef = db.collection("home/md5P7vYwbF1E7BJzHnaM/streetViews").document()
                batch.set(docRef, it)
            }

            batch.commit()
                .addOnSuccessListener { toast("Success") }
                .addOnFailureListener { toast("Failure") }
        }
    }

    data class GsonStory(
        var name: String? = null,

        var desc: String? = null,

        var thumbnail: String? = null,

        var pages: List<GsonPage>? = null
    )

    data class GsonPage(
        var desc: String? = null,

        var thumbnail: String? = null
    )

    fun addStoryList() {
        viewModelScope.launch {
            val inputStream = getApplication().resources.openRawResource(R.raw.story_1)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val json = reader.readText()
            storyList = Gson().fromJson(json, Array<GsonStory>::class.java).toList().mapNotNull { gsonStory ->

                val pages = gsonStory.pages?.toMutableList()

                pages?.removeIf { it.thumbnail.isNullOrEmpty() || it.desc.isNullOrEmpty() }

                Story(
                    title = gsonStory.name,
                    description = gsonStory.desc,
                    thumbnail = gsonStory.thumbnail,
                    pages = pages?.map {
                        Page(
                            description = it.desc,
                            thumbnail = "https:" + it.thumbnail
                        )
                    },
                    collectionId = "aCziNZYzC09cCeqizUAv"
                )
            }

            val db = Firebase.firestore
            val batch = db.batch()

            storyList.forEach {
                val docRef = db.collection("stories").document()
                batch.set(docRef, it)
            }

            batch.commit()
                .addOnSuccessListener { toast("Success") }
                .addOnFailureListener { toast("Failure") }
        }
    }

    fun removeSameNameSV() {
        // remove streetview that have same name firestore
        val map = mutableMapOf<String, String>()
        val db = Firebase.firestore
        db.collection("home/md5P7vYwbF1E7BJzHnaM/streetViews")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (map.containsKey(document.data["name"].toString())) {
                        db.collection("home/md5P7vYwbF1E7BJzHnaM/streetViews")
                            .document(document.id)
                            .delete()
                    } else {
                        map[document.data["name"].toString()] = document.id
                    }
                }
            }
    }

    private fun performAddToDatabase() {
        val db = Firebase.firestore
        val batch = db.batch()

        itemList.forEach {
            val docRef = db.collection("items").document()
            batch.set(docRef, it)
        }

        batch.commit()
            .addOnSuccessListener { toast("Success") }
            .addOnFailureListener { toast("Failure") }
    }

    private fun mapItemList() {
        val inputStream = getApplication().resources.openRawResource(R.raw.item_2)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val json = reader.readText()
        this.itemList = Gson().fromJson(json, Array<Item>::class.java).toMutableList()
        itemList.removeIf {
            it.thumbnail.isNullOrEmpty() || it.description.isNullOrEmpty() || it.name.isNullOrEmpty()
        }
        itemList.forEach {
            it.collectionId = "aCziNZYzC09cCeqizUAv"
        }
    }
}
