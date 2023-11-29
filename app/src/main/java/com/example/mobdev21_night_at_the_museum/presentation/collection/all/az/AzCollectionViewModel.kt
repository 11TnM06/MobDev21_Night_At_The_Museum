package com.example.mobdev21_night_at_the_museum.presentation.collection.all.az

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import kotlinx.coroutines.launch

class AzCollectionViewModel : BaseViewModel() {
    lateinit var listLetterDisplay: List<AZCollectionAdapter.LetterDisplay>

    init {
        initListLetterDisplay()
    }

    fun getCollectionStartWithLetter(letter: Char, onSuccessAction: (data: List<MCollection>) -> Unit, onFailureAction: (message: String) -> Unit) {
        viewModelScope.launch {
            val db = Firebase.firestore
            val collectionRef = db.collection("collections").orderBy("name")
                .startAt(letter.toString())
                .endAt(letter.toString() + "\uf8ff")

            collectionRef.get()
                .addOnSuccessListener {
                    val list = it.documents.mapNotNull { document ->
                        document.toObject(MCollection::class.java)?.apply { key = document.id }
                    }
                    onSuccessAction.invoke(list)
                }
                .addOnFailureListener { exception ->
                    onFailureAction.invoke(exception.message ?: "Failed to get collection")
                }
        }
    }

    private fun initListLetterDisplay() {
        listLetterDisplay = ('A'..'Z').map { AZCollectionAdapter.LetterDisplay(it) }
        listLetterDisplay.firstOrNull()?.isSelected = true
    }
}
