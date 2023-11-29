package com.example.mobdev21_night_at_the_museum.presentation.profile

import android.text.style.ForegroundColorSpan
import com.example.mobdev21_night_at_the_museum.AppPreferences
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.SpannableBuilder
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumDialog
import com.example.mobdev21_night_at_the_museum.common.extension.*
import com.example.mobdev21_night_at_the_museum.common.view.DialogScreen
import com.example.mobdev21_night_at_the_museum.databinding.ProfileDialogBinding

class ProfileDialog : MuseumDialog<ProfileDialogBinding>(R.layout.profile_dialog) {
    var listener: IListener? = null

    override fun getBackgroundId() = R.id.flProfileBackground

    override fun screen() = DialogScreen().apply {
        isFullWidth = true
        isFullHeight = true
        isDismissByTouchOutSide = true
        isDismissByOnBackPressed = true
    }

    override fun onInitView() {
        initOnClick()
        setTitleColor()
        AppPreferences.getUserInfo().let {
            binding.ivProfileAvatar.loadImage(it.avatar, placeHolder = getAppDrawable(R.drawable.ic_no_picture))
            binding.tvProfileEmail.text = it.email
            binding.tvProfileFullName.text = it.name
        }
    }

    private fun initOnClick() {
        binding.ivProfileClose.setOnSafeClick { dismiss() }
        binding.llProfileContent.setOnSafeClick { /* do no thing */ }
        binding.flProfileDown.setOnSafeClick { toastUndeveloped() }
        binding.mcvProfileManageAccount.setOnSafeClick { toastUndeveloped() }
        binding.llProfileCollection.setOnSafeClick { toastUndeveloped() }
        binding.llProfileNearBy.setOnSafeClick { toastUndeveloped() }
        binding.llProfileSettings.setOnSafeClick { listener?.onSetting() }
        binding.llProfileFeedback.setOnSafeClick { toastUndeveloped() }
        binding.tvProfilePrivacyPolicy.setOnSafeClick { toastUndeveloped() }
        binding.tvProfileTermsOfService.setOnSafeClick { toastUndeveloped() }
    }
    private fun setTitleColor() {
        binding.tvProfileMobDev.text = SpannableBuilder("M")
            .withSpan(ForegroundColorSpan(getAppColor(R.color.blue)))
            .appendText("u")
            .withSpan(ForegroundColorSpan(getAppColor(R.color.yellow)))
            .appendText("s")
            .withSpan(ForegroundColorSpan(getAppColor(R.color.green)))
            .appendText("e")
            .withSpan(ForegroundColorSpan(getAppColor(R.color.red)))
            .appendText("u")
            .withSpan(ForegroundColorSpan(getAppColor(R.color.purple)))
            .appendText("m")
            .withSpan(ForegroundColorSpan(getAppColor(R.color.blue)))
            .spannedText
    }

    interface IListener {
        fun onSetting()
    }
}
