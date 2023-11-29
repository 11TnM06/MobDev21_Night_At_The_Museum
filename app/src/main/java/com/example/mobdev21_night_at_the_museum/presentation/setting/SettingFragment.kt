package com.example.mobdev21_night_at_the_museum.presentation.setting

import com.example.mobdev21_night_at_the_museum.AppPreferences
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.databinding.SettingFragmentBinding
import com.example.mobdev21_night_at_the_museum.presentation.download.DownloadActivity
import com.example.mobdev21_night_at_the_museum.presentation.intro.IntroductionActivity

class SettingFragment : MuseumFragment<SettingFragmentBinding>(R.layout.setting_fragment) {

    override fun onInitView() {
        super.onInitView()
        realMainActivity.apply {
            setBackIcon()
        }
        initOnClick()
    }

    fun scrollToTop() {
        binding.nsvSettingFragment.smoothScrollTo(0, 0)
    }

    private fun initOnClick() {
        binding.constSettingOffline.setOnSafeClick {
            navigateTo(DownloadActivity::class.java)
        }
        binding.tvSettingLogOut.setOnSafeClick { logOut() }
    }

    private fun logOut() {
        AppPreferences.clearLoginInfo()
        museumActivity.navigateTo(IntroductionActivity::class.java)
        museumActivity.finish()
    }
}
