package com.example.mobdev21_night_at_the_museum.presentation.camera.view3d

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.viewModelScope
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.common.extension.getApplication
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.presentation.camera.view3d.control.View3dControllerAdapter
import kotlinx.coroutines.launch
import java.io.File

class View3dViewModel : BaseViewModel() {
    var modelRenderable: ModelRenderable? = null
    var tempFile: File? = null
    var listItemDisplay: MutableList<View3dControllerAdapter.ItemDisplay> = mutableListOf()
    var downloadMap: MutableMap<Long, String> = mutableMapOf()
    var selectItemId: String? = null
    val downloadManager = getApplication().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    fun updateItemDownloadState(itemKey: String?, downloadStatus: DOWNLOAD_STATUS, onSuccessAction: () -> Unit) {
        viewModelScope.launch {
            val index = listItemDisplay.indexOfFirst { it.item.key == itemKey }
            if (index == -1) {
                throw Exception("Item not found")
            }
            val oldItem = listItemDisplay[index]
            listItemDisplay[index] = oldItem.copy().apply { this.downloadStatus = downloadStatus }
            onSuccessAction.invoke()
        }
    }

    fun updateSelectedItem(itemId: String?, onSuccessAction: () -> Unit, onBuildStart: () -> Unit, onBuildEnd: () -> Unit) {
        viewModelScope.launch {
            // update old select
            val oldSelectedIndex = listItemDisplay.indexOfFirst { it.isSelected }
            if (oldSelectedIndex != -1) {
                val oldItem = listItemDisplay[oldSelectedIndex]
                listItemDisplay[oldSelectedIndex] = oldItem.copy().apply { isSelected = false }
            }

            // update new select
            val index = listItemDisplay.indexOfFirst { it.item.key == itemId }
            if (index != -1) {
                val oldItem = listItemDisplay[index]
                listItemDisplay[index] = oldItem.copy().apply { isSelected = true }
            }
            selectItemId = itemId

            updateDownloadState(index, onSuccessAction, onBuildStart, onBuildEnd)
        }
    }

    fun getListItem(onSuccessAction: () -> Unit, onFailAction: (String) -> Unit) {
        viewModelScope.launch {
            val db = Firebase.firestore
            val itemsRef = db.collection("items")
            val query = itemsRef.whereNotEqualTo("model3d", null)
            query.get().addOnSuccessListener { itemsSnapshot ->
                listItemDisplay = itemsSnapshot.documents.mapNotNull { itemSnapshot ->
                    val item = itemSnapshot.toObject(Item::class.java)?.apply { key = itemSnapshot.id }
                    var itemDisplay: View3dControllerAdapter.ItemDisplay? = null
                    if (item?.key != null) {
                        itemDisplay = View3dControllerAdapter.ItemDisplay(item)
                        if (isFileExist(item.key!!)) {
                            itemDisplay.downloadStatus = DOWNLOAD_STATUS.DOWNLOADED
                        }
                    }
                    itemDisplay
                }.toMutableList()
                listItemDisplay.firstOrNull { it.item.key == selectItemId }?.isSelected = true
                onSuccessAction.invoke()
            }.addOnFailureListener {
                onFailAction.invoke(it.message ?: "Unknown error")
            }
        }
    }

    fun buildModel(itemId: String?, onStartAction: () -> Unit, onSuccessAction: () -> Unit, onFailAction: (String) -> Unit) {
        if (itemId == null) {
            onFailAction.invoke("itemId is null")
            return
        }
        if (selectItemId == itemId) {
            onStartAction.invoke()
        }
        this.modelRenderable = null
        this.tempFile = getFileFromDevice(itemId)
        val renderableSource = RenderableSource
            .builder()
            .setSource(getApplication(), Uri.parse(tempFile?.path), RenderableSource.SourceType.GLB)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()
        ModelRenderable
            .builder()
            .setSource(getApplication(), renderableSource)
            .setRegistryId(tempFile?.path)
            .build()
            .thenAccept { modelRenderable: ModelRenderable? ->
                if (modelRenderable != null) {
                    if (selectItemId == itemId) {
                        this.modelRenderable = modelRenderable
                        onSuccessAction.invoke()
                    }
                } else {
                    onFailAction.invoke("modelRenderable is null")
                }
            }
    }

    private fun updateDownloadState(index: Int, onSuccessAction: () -> Unit, onBuildStart: () -> Unit = {}, onBuildFinished: () -> Unit = {}) {
        val itemDisplay = listItemDisplay[index]
        when (itemDisplay.downloadStatus) {
            DOWNLOAD_STATUS.DOWNLOADING -> {
                onSuccessAction.invoke()
            }

            DOWNLOAD_STATUS.DOWNLOADED -> {
                val itemId = itemDisplay.item.key
                onSuccessAction.invoke()
                buildModel(
                    itemId,
                    onStartAction = {
                        onBuildStart.invoke()
                    },
                    onSuccessAction = {
                        onBuildFinished.invoke()
                    },
                    onFailAction = { toast("Render $itemId thất bại: $it") }
                )
            }

            DOWNLOAD_STATUS.NOT_DOWNLOADED -> {
                listItemDisplay[index].downloadStatus = DOWNLOAD_STATUS.DOWNLOADING
                onSuccessAction.invoke()
                getFileFromUrl(
                    itemDisplay.item,
                    onStartAction = {},
                    onFailureAction = { toast("Download thất bại: $it") }
                )
            }
        }
    }

    private fun getFileFromUrl(item: Item, onStartAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        val url = item.model3d
        if (url == null) {
            onFailureAction.invoke("Url is null")
            return
        }

        val fileName = item.key + ".glb"

        val downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val file = File(downloadDirectory, fileName)
        if (file.exists() && !file.delete()) {
            onFailureAction.invoke("Delete file failed")
            return
        }
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Downloading GLB file")
            .setDescription("Downloading $fileName")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        onStartAction.invoke()
        val downloadId = downloadManager.enqueue(request)
        downloadMap[downloadId] = item.key!!
    }

    private fun getFileFromDevice(itemId: String): File {
        val fileName = "$itemId.glb"
        val downloadDirectory = getApplication().filesDir
        return File(downloadDirectory, fileName)
    }

    private fun isFileExist(itemId: String): Boolean {
        val file = getFileFromDevice(itemId)
        return file.exists()
    }

    fun copyFileFromDownloadToInternalStorage(itemId: String, onSuccessAction: () -> Unit) {
        val fileName = "$itemId.glb"
        val file = File(getApplication().filesDir, fileName)
        val downloadFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
        downloadFile.copyTo(file, true)
        downloadFile.delete()
        onSuccessAction.invoke()
    }
}
