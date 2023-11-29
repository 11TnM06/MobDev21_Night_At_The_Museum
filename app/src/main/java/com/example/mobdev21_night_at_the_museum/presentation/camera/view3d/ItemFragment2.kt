package com.example.mobdev21_night_at_the_museum.presentation.camera.view3d

import com.example.mobdev21_night_at_the_museum.presentation.item.ItemFragment

class ItemFragment2: ItemFragment() {
    override fun onDestroy() {
        realMainActivity.finish()
        super.onDestroy()
    }
}
