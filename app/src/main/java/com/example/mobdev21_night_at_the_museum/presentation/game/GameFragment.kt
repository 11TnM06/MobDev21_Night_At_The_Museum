package com.example.mobdev21_night_at_the_museum.presentation.game

import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.getAppString
import com.example.mobdev21_night_at_the_museum.databinding.CollectionsEmptyItemBinding

class GameFragment: MuseumFragment<CollectionsEmptyItemBinding>(R.layout.collections_empty_item) {
    override fun onInitView() {
        super.onInitView()
        binding.tvCollectionsEmpty.text = getAppString(R.string.undeveloped)
    }
}
