package com.example.mobdev21_night_at_the_museum.presentation.streetview.list

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.domain.model.StreetView
import kotlinx.coroutines.launch

class AllStreetViewModel : BaseViewModel() {
    var countAndStreetViews: List<StreetView>? = null

    fun getAllStreetView(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            getStreetViewList(onSuccess)
        }
    }

    private fun getStreetViewList(onSuccess: () -> Unit) {
        val query = Firebase.firestore
            .collection("home/md5P7vYwbF1E7BJzHnaM/streetViews")
            .limit(30)

        query.get().addOnSuccessListener { streetViewsSnapshot ->
            countAndStreetViews = (streetViewsSnapshot.documents.mapNotNull {
                it.toObject(StreetView::class.java)?.apply { key = it.id }
            })

            onSuccess.invoke()
        }
    }
}
