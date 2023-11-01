package com.example.mobdev21_night_at_the_museum.domain.model

data class Story(
    var title: String? = null,

    var description: String? = null,

    var thumbnail: String? = null,

    var collectionId: String? = null,

    var storyDetailModels: ArrayList<StoryDetailModel>? = null
)


