package com.example.mobdev21_night_at_the_museum.presentation.favorite

import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.mobdev21_night_at_the_museum.AppPreferences
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.domain.model.*
import kotlinx.coroutines.launch

class FavoriteViewModel : BaseViewModel() {
    var listFavorite: MutableList<Any> = initFavoriteTabData()
    var listGallery: MutableList<Any> = mockGalleriesData()

    fun getFavoriteData(onSuccessAction: () -> Unit, onFailureAction: (message: String) -> Unit) {
        viewModelScope.launch {
            var isSuccessItems = false
            var isSuccessStories = false
            var isSuccessCollections = false

            // Get favorites data of user
            val ref = FirebaseDatabase.getInstance().getReference("Users/${AppPreferences.getUserInfo().key}/favorites")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val favoriteData = snapshot.getValue(FavoriteData::class.java)

                    // remove null in items
                    favoriteData?.items?.removeIf { it?.key == null }

                    // get items from list key, max 4 items
                    val itemList = mutableListOf<Item>()
                    var itemCount = favoriteData?.items?.size ?: 0
                    if (itemCount == 0) isSuccessItems = true
                    if (itemCount > 4) itemCount = 4
                    favoriteData?.items?.subList(0, itemCount)?.forEach {
                        val itemRef = FirebaseDatabase.getInstance().getReference("Items/${it?.key}")
                        itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                itemCount--
                                val item = snapshot.getValue(Item::class.java)
                                if (item != null) {
                                    itemList.add(item)

                                    // do success action when get 4 items or end of list
                                    if (itemCount <= 0 || itemList.size == 4) {
                                        (listFavorite.getOrNull(1) as? FavoriteAdapter.ItemDisplay)?.let { itemDisplay ->
                                            itemDisplay.count = favoriteData.items?.size ?: 0
                                            itemDisplay.itemList = itemList
                                        }
                                        isSuccessItems = true
                                        if (isSuccessStories && isSuccessCollections) {
                                            onSuccessAction.invoke()
                                        }
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                itemCount--
                                onFailureAction.invoke(error.message)
                            }
                        })
                    }



                    // remove null in stories
                    favoriteData?.stories?.removeIf { it?.key == null }

                    // get stories from list key, max 6 stories
                    val storyList = mutableListOf<Story>()
                    var storyCount = favoriteData?.stories?.size ?: 0
                    if (storyCount == 0) isSuccessStories = true
                    if (storyCount > 6) storyCount = 6
                    favoriteData?.stories?.subList(0, storyCount)?.forEach {
                        val itemRef = FirebaseDatabase.getInstance().getReference("Stories/${it?.key}")
                        itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                storyCount--
                                val story = snapshot.getValue(Story::class.java)
                                if (story != null) {
                                    storyList.add(story)

                                    // do success action when get 6 stories or end of list
                                    if (storyCount <= 0 || storyList.size == 6) {
                                        (listFavorite.getOrNull(2) as? FavoriteAdapter.StoryDisplay)?.let { storyDisplay ->
                                            storyDisplay.count = favoriteData.stories?.size ?: 0
                                            storyDisplay.storyList = storyList
                                        }
                                        isSuccessStories = true
                                        if (isSuccessItems && isSuccessCollections) {
                                            onSuccessAction.invoke()
                                        }
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                storyCount--
                                onFailureAction.invoke(error.message)
                            }
                        })
                    }


                    // remove null in collections
                    favoriteData?.collections?.removeIf { it?.key == null }

                    // get collections from list key, max 6 collections
                    val collectionList = mutableListOf<MCollection>()
                    var collectionCount = favoriteData?.collections?.size ?: 0
                    if (collectionCount == 0) isSuccessCollections = true
                    if (collectionCount > 6) collectionCount = 6
                    favoriteData?.collections?.subList(0, collectionCount)?.forEach {
                        val itemRef = FirebaseDatabase.getInstance().getReference("Collections/${it?.key}")
                        itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                collectionCount--
                                val collection = snapshot.getValue(MCollection::class.java)
                                if (collection != null) {
                                    collectionList.add(collection)

                                    // do success action when get 6 collections or end of list
                                    if (collectionCount <= 0 || collectionList.size == 6) {
                                        (listFavorite.getOrNull(3) as? FavoriteAdapter.CollectionDisplay)?.let { collectionDisplay ->
                                            collectionDisplay.count = favoriteData.collections?.size ?: 0
                                            collectionDisplay.collectionList = collectionList
                                        }
                                        isSuccessCollections = true
                                        if (isSuccessItems && isSuccessStories) {
                                            onSuccessAction.invoke()
                                        }
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                collectionCount--
                                onFailureAction.invoke(error.message)
                            }
                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailureAction.invoke(error.message)
                }
            })
        }
    }

    private fun mockGalleriesData(): MutableList<Any> {
        val list = mutableListOf<Any>()
        list.add(FavoriteAdapter.HeaderDisplay().apply {
            avatarUrl = AppPreferences.getUserInfo().avatar
        })
        list.add(Gallery().apply {
            title = "Gallery 1"
            description = "Description 1"
            items = listOf(
                Item(thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg")
            )
        })
        list.add(Gallery().apply {
            title = "Gallery 2"
            description = "Description 2"
            items = listOf(
                Item(thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg"),
                Item(thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg"),
                Item(thumbnail = "https://i.pinimg.com/564x/e4/78/fa/e478fae5d5ecf6f8537f248f6cab0d15.jpg")
            )
        })
        list.add(Gallery().apply {
            title = "Gallery 3"
            description = "Description 3"
            items = listOf(
                Item(thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg"),
                Item(thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg")
            )
        })
        return list
    }

    private fun mockFavoriteTabData(): MutableList<Any> {
        val list = mutableListOf<Any>()
        list.add(FavoriteAdapter.HeaderDisplay().apply {
            avatarUrl = AppPreferences.getUserInfo().avatar
        })
        list.add(FavoriteAdapter.ItemDisplay().apply {
            count = 42
            itemList = listOf(
                Item(thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg"),
                Item(thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg"),
                Item(thumbnail = "https://i.pinimg.com/564x/e4/78/fa/e478fae5d5ecf6f8537f248f6cab0d15.jpg"),
                Item(thumbnail = "https://i.pinimg.com/564x/75/bd/2e/75bd2e6f99d705642531b3f214ff4f70.jpg")
            )
        })
        list.add(FavoriteAdapter.StoryDisplay().apply {
            count = 30
            storyList = listOf(
                Story(
                    thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg",
                    title = "Story 1",
                    description = "Description 1"
                ),
                Story(
                    thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg",
                    title = "Story 2",
                    description = "Description 2"
                ),
                Story(
                    thumbnail = "https://i.pinimg.com/564x/e4/78/fa/e478fae5d5ecf6f8537f248f6cab0d15.jpg",
                    title = "Story 3",
                    description = "Description 3"
                ),
                Story(
                    thumbnail = "https://i.pinimg.com/564x/75/bd/2e/75bd2e6f99d705642531b3f214ff4f70.jpg",
                    title = "Story 4",
                    description = "Description 4"
                ),
                Story(
                    thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg",
                    title = "Story 5",
                    description = "Description 5"
                ),
                Story(
                    thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg",
                    title = "Story 6",
                    description = "Description 6"
                ),
            )
        })
        list.add(FavoriteAdapter.CollectionDisplay().apply {
            count = 13
            collectionList = listOf(
                MCollection(thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg"),
                MCollection(thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg"),
                MCollection(thumbnail = "https://i.pinimg.com/564x/e4/78/fa/e478fae5d5ecf6f8537f248f6cab0d15.jpg"),
                MCollection(thumbnail = "https://i.pinimg.com/564x/75/bd/2e/75bd2e6f99d705642531b3f214ff4f70.jpg"),
                MCollection(thumbnail = "https://i.pinimg.com/736x/10/2e/19/102e192f5ec83d1c10fa9b3241a50b1f.jpg"),
                MCollection(thumbnail = "https://i.pinimg.com/564x/a3/0d/94/a30d9464d035e1f30dd088d2ba89103d.jpg")
            )
        })
        return list
    }

    private fun initFavoriteTabData(): MutableList<Any> {
        val list = mutableListOf<Any>()
        list.add(FavoriteAdapter.HeaderDisplay().apply {
            avatarUrl = AppPreferences.getUserInfo().avatar
        })
        list.add(FavoriteAdapter.ItemDisplay().apply {
            count = 0
            itemList = listOf()
        })
        list.add(FavoriteAdapter.StoryDisplay().apply {
            count = 0
            storyList = listOf()
        })
        list.add(FavoriteAdapter.CollectionDisplay().apply {
            count = 0
            collectionList = listOf()
        })
        return list
    }
}
