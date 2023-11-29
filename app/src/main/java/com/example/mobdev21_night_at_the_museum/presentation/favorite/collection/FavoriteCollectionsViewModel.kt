package com.example.mobdev21_night_at_the_museum.presentation.favorite.collection

import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection

class FavoriteCollectionsViewModel : BaseViewModel() {
    var collections: List<MCollection> = emptyList()
}
