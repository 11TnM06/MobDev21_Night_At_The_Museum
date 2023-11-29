package com.example.mobdev21_night_at_the_museum.presentation.dialog

import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumDialog
import com.example.mobdev21_night_at_the_museum.common.extension.getAppString
import com.example.mobdev21_night_at_the_museum.common.extension.gone
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.extension.show
import com.example.mobdev21_night_at_the_museum.common.view.DialogScreen
import com.example.mobdev21_night_at_the_museum.databinding.LoadingDialogBinding

class LoadingDialog : MuseumDialog<LoadingDialogBinding>(R.layout.loading_dialog) {

    var title: String? = null
    var isEnableDismiss = false

    override fun getBackgroundId() = R.id.flLoadingRoot

    override fun screen() = DialogScreen().apply {
        isFullWidth = true
        isFullHeight = true
        isDismissByTouchOutSide = false
        isDismissByOnBackPressed = isEnableDismiss
    }

    override fun onInitView() {
        binding.tvLoadingDialogMessage.text = title ?: getAppString(R.string.please_wait)

        if (isEnableDismiss) {
            binding.mcvLoadingClose.show()
            binding.mcvLoadingClose.setOnSafeClick {
                dismiss()
            }
        } else {
            binding.mcvLoadingClose.gone()
        }
    }
}
