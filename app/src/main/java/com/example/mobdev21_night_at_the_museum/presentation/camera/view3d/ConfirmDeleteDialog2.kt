package com.example.mobdev21_night_at_the_museum.presentation.camera.view3d

import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumDialog
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.view.DialogScreen
import com.example.mobdev21_night_at_the_museum.databinding.ConfirmDeleteDialog2Binding

class ConfirmDeleteDialog2 : MuseumDialog<ConfirmDeleteDialog2Binding>(R.layout.confirm_delete_dialog_2) {
    var onConfirmAction: (() -> Unit)? = null

    override fun getBackgroundId() = R.id.flConfirmDeleteRoot

    override fun screen() = DialogScreen().apply {
        isFullWidth = true
        isFullHeight = true
        isDismissByTouchOutSide = false
        isDismissByOnBackPressed = true
    }

    override fun onInitView() {
        binding.mcvConfirmDeleteContent.setOnSafeClick {}
        binding.tvConfirmDeleteYes.setOnSafeClick { onConfirmAction?.invoke() }
        binding.tvConfirmDeleteNo.setOnSafeClick { dismiss() }
    }
}
