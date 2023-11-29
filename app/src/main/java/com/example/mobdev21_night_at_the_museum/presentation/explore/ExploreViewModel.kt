package com.example.mobdev21_night_at_the_museum.presentation.explore

import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.AppPreferences
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.domain.model.Exhibition
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.domain.model.User
import kotlinx.coroutines.launch

class ExploreViewModel : BaseViewModel() {
    var exploreData: MutableList<Any> = mutableListOf()

    private var exhibitions = listOf<Exhibition>()
    private var items = listOf<Item>()

    fun getExploreDataFromDatabase(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        viewModelScope.launch {
            val exhibitionRef = Firebase.firestore
                .collection("exhibitions")
                .whereGreaterThan("endTime", Timestamp.now()).get()

            val itemRef = Firebase.firestore
                .collection("items").get()

            Tasks.whenAllSuccess<QuerySnapshot>(exhibitionRef, itemRef)
                .continueWith {
                    exhibitions = (exhibitionRef.result.documents).mapNotNull { exhibition ->
                        exhibition.toObject(Exhibition::class.java)?.apply { key = exhibition.id }
                    }
                    items = (itemRef.result.documents).mapNotNull { item ->
                        item.toObject(Item::class.java)?.apply { key = item.id }
                    }
                    onSuccessAction.invoke()
                }
                .addOnFailureListener {
                    onFailureAction.invoke(it.message ?: "")
                }
        }
    }

    fun getShuffledExploreData(): List<Any> {
        val list = mutableListOf<Any>()
        list.addAll(exhibitions)
        list.addAll(items)
        val list2 = mutableListOf<Any>()
        while (list.isNotEmpty()) {
            val randomIndex = (0 until list.size).random()
            list2.add(list[randomIndex])
            list.removeAt(randomIndex)
        }
        exploreData = list2
        return exploreData
    }

    fun likeItem(item: Item, onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        if (AppPreferences.getUserInfo().key == null) {
            throw Exception("User key is null")
        }
        if (item.key == null) {
            throw Exception("Item key is null")
        }

        val likeItemRef: Task<Void> = if (item.safeIsLiked()) {
            Firebase.firestore.collection("users")
                .document(AppPreferences.getUserInfo().key!!)
                .update("fitems", FieldValue.arrayRemove(item.key!!))
        } else {
            Firebase.firestore.collection("users")
                .document(AppPreferences.getUserInfo().key!!)
                .update("fitems", FieldValue.arrayUnion(item.key!!))
        }
        likeItemRef
            .addOnSuccessListener {
                val userRef = Firebase.firestore
                    .collection("users")
                    .document(AppPreferences.getUserInfo().key!!)

                userRef.get()
                    .addOnSuccessListener {
                        val user = it.toObject(User::class.java)?.apply { key = it.id }
                        AppPreferences.setUserInfo(user!!)

                        // create new item for resubmit list
                        val updateItemIndex = exploreData.indexOfFirst { explore ->
                            if (explore is Item) {
                                explore.key == item.key
                            } else {
                                false
                            }
                        }
                        val oldItem = exploreData.getOrNull(updateItemIndex) as Item
                        exploreData[updateItemIndex] = oldItem.copy().apply {
                            key = oldItem.key
                            mapIsLiked()
                        }

                        onSuccessAction.invoke()
                    }
                    .addOnFailureListener {
                        onFailureAction.invoke(it.message ?: "")
                    }
            }
            .addOnFailureListener {
                onFailureAction.invoke(it.message ?: "")
            }
    }
}
