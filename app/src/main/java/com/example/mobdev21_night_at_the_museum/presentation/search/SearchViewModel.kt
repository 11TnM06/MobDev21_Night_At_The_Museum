package com.example.mobdev21_night_at_the_museum.presentation.search

import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.domain.model.Story
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {
    var suggestTexts: List<SearchKeyAdapter.SearchKeyDisplay>? = null
    var searchData: MutableList<Any>? = null

    fun getSuggestText(inputKey: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            suggestTexts = listOf(
                SearchKeyAdapter.SearchKeyDisplay(inputKey),
            )
            onSuccess.invoke()
        }
    }

    fun getSearchData(keyword: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val db = Firebase.firestore

            val itemTask = db.collection("items")
                .orderBy("name")
                .startAt(keyword)
                .endAt(keyword + "\uf8ff")
                .limit(10)
                .get()

            val storyTask = db.collection("stories")
                .orderBy("title")
                .startAt(keyword)
                .endAt(keyword + "\uf8ff")
                .limit(10)
                .get()

            val collectionTask = db.collection("collections")
                .orderBy("name")
                .startAt(keyword)
                .endAt(keyword + "\uf8ff")
                .limit(10)
                .get()

            Tasks.whenAllSuccess<QuerySnapshot>(itemTask, storyTask, collectionTask)
                .continueWith {
                    val items = (itemTask.result?.documents ?: listOf()).mapNotNull {
                        it.toObject(Item::class.java)?.apply { key = it.id }
                    }

                    val stories = (storyTask.result?.documents ?: listOf()).mapNotNull {
                        it.toObject(Story::class.java)?.apply { key = it.id }
                    }

                    val collections = (collectionTask.result?.documents ?: listOf()).mapNotNull {
                        it.toObject(MCollection::class.java)?.apply { key = it.id }
                    }

                    searchData = mutableListOf()

                    if (items.isNotEmpty()) {
                        searchData?.add(SearchAdapter.SearchItemDisplay(items))
                    }

                    if (stories.isNotEmpty()) {
                        searchData?.add(SearchAdapter.SearchStoryDisplay(stories))
                    }

                    if (collections.isNotEmpty()) {
                        searchData?.add(SearchAdapter.SearchCollectionDisplay(collections))
                    }

                    onSuccess.invoke()
                }
        }
    }
}
