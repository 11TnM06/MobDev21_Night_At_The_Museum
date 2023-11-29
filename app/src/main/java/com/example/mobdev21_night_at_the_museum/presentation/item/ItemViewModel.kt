package com.example.mobdev21_night_at_the_museum.presentation.item

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.domain.usecase.updateLikeItem
import kotlinx.coroutines.launch

class ItemViewModel : BaseViewModel() {
    var item: Item? = null
    var itemId: String? = null
    private var itemData: MutableList<Any> = mutableListOf()
    var detailList = mutableListOf<ItemAdapter.DetailInfoDisplay>()

    fun getItemData(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        viewModelScope.launch {
            if (itemId == null) {
                throw Exception("Item id is null")
            }
            val db = Firebase.firestore
            val itemRef = db.collection("items").document(itemId!!)
            itemRef.get().addOnSuccessListener { itemSnapshot ->
                item = itemSnapshot.toObject(Item::class.java)?.apply { key = itemSnapshot.id }

                if (item?.collectionId == null) {
                    mapDataForAdapter(onSuccessAction)
                }

                val collectionRef = db.collection("collections").document(item?.collectionId!!)
                collectionRef.get().addOnSuccessListener { collectionSnapshot ->
                    item?.collection = collectionSnapshot.toObject(MCollection::class.java)
                    mapDataForAdapter(onSuccessAction)
                }.addOnFailureListener {
                    onFailureAction.invoke(it.message ?: "Get collection fail")
                }
            }.addOnFailureListener {
                onFailureAction.invoke(it.message ?: "Get item fail")
            }
        }
    }



    fun getDataList(isOpen: Boolean? = null): List<Any> {
        if (isOpen != null) {
            // update variable
            val index = itemData.indexOfFirst { it is ItemAdapter.DetailTitleDisplay }
            if (index != -1) {
                itemData[index] = ItemAdapter.DetailTitleDisplay().apply { this.isOpen = !isOpen }
            }

            // add or remove detail list
            val list = mutableListOf<Any>()
            list.addAll(itemData)

            if (!isOpen) {
                list.addAll(5, detailList)
            } else {
                list.removeAll(detailList)
            }

            itemData = list
        }
        return itemData
    }

    fun mapDataDetail() {
        viewModelScope.launch {
            detailList.clear()

            if (item?.details != null) {
                for (i in item?.details!!) {
                    detailList.add(ItemAdapter.DetailInfoDisplay().apply { itemDetail = i })
                }
            }

            detailList.lastOrNull()?.isLast = true
        }
    }

    private fun mapDataForAdapter(onSuccessAction: () -> Unit) {
        itemData.clear()

        itemData.add(ItemAdapter.TransparentDisplay())

        itemData.add(ItemAdapter.ChooseActionDisplay().apply {
            val actionList = mutableListOf(ItemActionAdapter.ActionDisplay(ACTION_TYPE.ZOOM_IN))
            actionList.add(ItemActionAdapter.ActionDisplay(ACTION_TYPE.AR))
            actionList.add(ItemActionAdapter.ActionDisplay(ACTION_TYPE.STREET))
            actions = actionList
        })

        itemData.add(ItemAdapter.TitleDisplay().apply {
            title = item?.name
            creator = item?.creatorName
            time = item?.time
            collectionName = item?.collection?.name
            collectionThumb = item?.collection?.icon
            isLike = item?.safeIsLiked() ?: false
        })

        itemData.add(ItemAdapter.DescriptionDisplay().apply {
            description = item?.description
        })

        itemData.add(ItemAdapter.DetailTitleDisplay().apply {
            isOpen = false
        })

        itemData.add(ItemAdapter.RecommendDisplay().apply { currentItemId = this@ItemViewModel.itemId })

        mapDataDetail()

        onSuccessAction.invoke()
    }

    fun likeUpdate(isLike: Boolean, onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        viewModelScope.launch {
            updateLikeItem(
                isLike, itemId,
                onSuccessAction = {
                    val index = itemData.indexOfFirst { it is ItemAdapter.TitleDisplay }
                    val item = itemData.getOrNull(index) as? ItemAdapter.TitleDisplay

                    if (item != null) {
                        itemData[index] = item.copy().apply { this.isLike = !this.isLike }
                        onSuccessAction.invoke()
                    } else {
                        onFailureAction.invoke("Item is null")
                    }
                },
                onFailureAction = {
                    onFailureAction.invoke(it)
                }
            )
        }
    }
}
