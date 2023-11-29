package com.example.mobdev21_night_at_the_museum.domain.usecase

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.AppPreferences
import com.example.mobdev21_night_at_the_museum.domain.model.User

fun updateLikeItem(currentIsLike: Boolean, itemId: String?, onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
    if (itemId == null) {
        onFailureAction.invoke("Item key is null")
        return
    }

    val userRef = Firebase.firestore.collection("users").document(AppPreferences.getUserId())

    val updateLikeTask = if (currentIsLike) {
        userRef.update("fitems", FieldValue.arrayRemove(itemId))
    } else {
        userRef.update("fitems", FieldValue.arrayUnion(itemId))
    }

    updateLikeTask.addOnSuccessListener {
        userRef.get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)?.apply { key = it.id }
                AppPreferences.setUserInfo(user!!)
                onSuccessAction.invoke()
            }.addOnFailureListener {
                onFailureAction.invoke(it.message ?: "Like item failed")
            }
    }.addOnFailureListener {
        onFailureAction.invoke(it.message ?: "Like item failed")
    }
}

fun updateLikeStory(currentIsLike: Boolean, storyId: String?, onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
    if (storyId == null) {
        onFailureAction.invoke("Story key is null")
        return
    }

    val userRef = Firebase.firestore.collection("users").document(AppPreferences.getUserId())

    val updateLikeTask = if (currentIsLike) {
        userRef.update("fstories", FieldValue.arrayRemove(storyId))
    } else {
        userRef.update("fstories", FieldValue.arrayUnion(storyId))
    }

    updateLikeTask.addOnSuccessListener {
        userRef.get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)?.apply { key = it.id }
                AppPreferences.setUserInfo(user!!)
                onSuccessAction.invoke()
            }.addOnFailureListener {
                onFailureAction.invoke(it.message ?: "Like item failed")
            }
    }.addOnFailureListener {
        onFailureAction.invoke(it.message ?: "Like item failed")
    }
}


