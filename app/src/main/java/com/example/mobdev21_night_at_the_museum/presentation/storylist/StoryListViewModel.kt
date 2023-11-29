package com.example.mobdev21_night_at_the_museum.presentation.storylist

import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.domain.model.StreetView

class StoryListViewModel : BaseViewModel() {
    var streetViews: List<StreetView> = emptyList()
}
