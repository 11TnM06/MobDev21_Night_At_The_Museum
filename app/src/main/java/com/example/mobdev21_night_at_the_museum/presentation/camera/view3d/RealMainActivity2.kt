package com.example.mobdev21_night_at_the_museum.presentation.camera.view3d

import androidx.core.os.bundleOf
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.presentation.RealMainActivity
import com.example.mobdev21_night_at_the_museum.presentation.item.ItemFragment

class RealMainActivity2: RealMainActivity() {
    companion object {
        const val ITEM_ID_KEY = "ITEMS_KEY"
    }

    var itemId: String? = null

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        itemId = intent.getStringExtra(ITEM_ID_KEY)
    }

    override fun onInitView() {
        super.onInitView()
        addFragmentNew(
            fragment = ItemFragment2(),
            bundleOf(ItemFragment.ITEM_ID_KEY to itemId),
            containerId = R.id.flRealMainContainer
        )
    }
}
