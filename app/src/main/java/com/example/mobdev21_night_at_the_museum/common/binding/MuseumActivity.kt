package com.example.mobdev21_night_at_the_museum.common.binding

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.common.navigation.FadeAnim
import com.example.mobdev21_night_at_the_museum.common.navigation.IScreenAnim
import com.example.mobdev21_night_at_the_museum.common.view.BaseActivity
import com.example.mobdev21_night_at_the_museum.common.view.BaseFragment
import com.example.mobdev21_night_at_the_museum.presentation.RealMainActivity

abstract class MuseumActivity<DB : ViewDataBinding>(layoutId: Int) : BaseBindingActivity<DB>(layoutId) {
    var mContainerId: Int = 0

    override fun getContainerId() = mContainerId

    fun replaceFragmentNew(
        fragment: BaseFragment,
        bundle: Bundle? = null,
        keepToBackStack: Boolean = true,
        screenAnim: IScreenAnim = FadeAnim(),
        containerId: Int
    ) {
        this.mContainerId = containerId
        super.replaceFragment(fragment, bundle, keepToBackStack, screenAnim)
    }

    fun addFragmentNew(
        fragment: BaseFragment,
        bundle: Bundle? = null,
        keepToBackStack: Boolean = true,
        screenAnim: IScreenAnim = FadeAnim(),
        containerId: Int,
        isEnableFragmentContainerScrollingBehavior: Boolean = false
    ) {
        this.mContainerId = containerId
        super.addFragment(fragment, bundle, keepToBackStack, screenAnim)
        if (this is RealMainActivity) {
            if (isEnableFragmentContainerScrollingBehavior) {
                enableFragmentContainerScrollingBehavior()
            } else {
                disableFragmentContainerScrollingBehavior()
            }
        }
    }

    fun setFullScreen() {
        window?.apply {
            WindowCompat.setDecorFitsSystemWindows(this, false)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v: View, windowInsets: WindowInsetsCompat ->
            if (!initSetFullScreen) {
                val systemBarInset = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.updatePadding(0, systemBarInset.top, 0, systemBarInset.bottom)
                initSetFullScreen = true
            }
            windowInsets
        }
    }

    fun navigateToAndClearStack(clazz: Class<out BaseActivity>, onCallback: (Intent) -> Unit = {}) {
        val intent = Intent(this, clazz)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        finishAffinity()
        onCallback.invoke(intent)
        startActivity(intent)
        animOpenScreen()
    }

    fun hasFragment(clazz: Class<out BaseFragment>): Boolean {
        return supportFragmentManager.fragments.any { it::class.java == clazz }
    }

    fun goToLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}
