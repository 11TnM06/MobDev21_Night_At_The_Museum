package com.example.mobdev21_night_at_the_museum.presentation.dialog

import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumDialog
import com.example.mobdev21_night_at_the_museum.common.view.DialogScreen
import com.example.mobdev21_night_at_the_museum.databinding.LoadingDialogBinding

class LoadingDialog2 : MuseumDialog<LoadingDialogBinding>(R.layout.loading_dialog_2) {

    override fun getBackgroundId() = R.id.flLoadingRoot

    override fun screen() = DialogScreen().apply {
        isFullWidth = true
        isFullHeight = true
        isDismissByTouchOutSide = false
        isDismissByOnBackPressed = false
    }

    override fun onInitView() {
    }
}
