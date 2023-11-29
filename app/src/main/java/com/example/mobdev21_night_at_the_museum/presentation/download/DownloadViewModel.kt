package com.example.mobdev21_night_at_the_museum.presentation.download

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.common.extension.getApplication
import kotlinx.coroutines.launch
import java.io.File

class DownloadViewModel : BaseViewModel() {
    var dataList: MutableList<DownloadAdapter.DownloadItem> = mutableListOf()

    fun getDownloadedModel(onSuccessAction: () -> Unit) {
        viewModelScope.launch {
            val itemIdList = getApplication().filesDir.listFiles()?.filter {
                it.name.endsWith(".glb")
            }?.mapNotNull {
                it.nameWithoutExtension
            }
            if (itemIdList.isNullOrEmpty()) {
                onSuccessAction.invoke()
            } else {
                getDownloadItemDataFromDb(itemIdList, onSuccessAction)
            }
        }
    }

    fun deleteModelFromInternalStorage(id: String?, onSuccessAction: () -> Unit) {
        viewModelScope.launch {
            val file = File(getApplication().filesDir, "$id.glb")
            if (file.delete()) {
                dataList.removeIf { it.id == id }
            }
            onSuccessAction.invoke()
        }
    }

    private fun getDownloadItemDataFromDb(itemIdList: List<String>, onSuccessAction: () -> Unit) {
        viewModelScope.launch {
            val db = Firebase.firestore
            val itemsRef = db.collection("items").whereIn(FieldPath.documentId(), itemIdList)
            itemsRef.get().addOnSuccessListener { itemsSnapshot ->
                dataList = itemsSnapshot.mapNotNull {
                    it.toObject(DownloadAdapter.DownloadItem::class.java).apply {
                        id = it.id
                        size = getSizeInMb(File(getApplication().filesDir, "$id.glb").length())
                    }
                }.toMutableList()
                onSuccessAction.invoke()
            }
        }
    }

    private fun getSizeInMb(size: Long): String {
        return "%.2f".format(size / 1024.0 / 1024.0) + " MB"
    }
}
