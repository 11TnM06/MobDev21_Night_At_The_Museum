package com.example.mobdev21_night_at_the_museum.presentation.collection.all.all

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import kotlinx.coroutines.launch

class AllCollectionViewModel : BaseViewModel() {
    fun getListCollection(onSuccessAction: (data: List<MCollection>) -> Unit, onFailureAction: (message: String) -> Unit) {
        viewModelScope.launch {
            val db = Firebase.firestore
            val collectionRef = db.collection("collections")
            collectionRef.get()
                .addOnSuccessListener { result ->
                    val list = result.documents.mapNotNull { document ->
                        document.toObject(MCollection::class.java)?.apply { key = document.id }
                    }
                    onSuccessAction.invoke(list)
                }
                .addOnFailureListener { exception ->
                    onFailureAction.invoke(exception.message ?: "Error")
                }
        }
    }
}
