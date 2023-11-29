package com.example.mobdev21_night_at_the_museum.common.binding

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.common.navigation.FadeAnim
import com.example.mobdev21_night_at_the_museum.common.navigation.IScreenAnim
import com.example.mobdev21_night_at_the_museum.common.view.BaseFragment
import com.example.mobdev21_night_at_the_museum.presentation.RealMainActivity
import com.example.mobdev21_night_at_the_museum.presentation.collection.CollectionFragment

abstract class MuseumFragment<DB : ViewDataBinding>(layoutId: Int) : BaseBindingFragment<DB>(layoutId) {
    protected val museumActivity by lazy {
        requireActivity() as MuseumActivity<*>
    }

    protected val realMainActivity by lazy {
        requireActivity() as RealMainActivity
    }

    override fun onDestroy() {
        super.onDestroy()
        if (museumActivity is RealMainActivity) {
            setActionBarSearchIcon()
            realMainActivity.checkSearchEditText()
        }
    }

    override fun onResume() {
        super.onResume()
        if (museumActivity is RealMainActivity) {
            setActionBarColor()
            realMainActivity.checkSearchEditText()
        }
    }

    protected fun replaceFragmentNew(
        fragment: BaseFragment,
        bundle: Bundle? = null,
        keepToBackStack: Boolean = true,
        screenAnim: IScreenAnim = FadeAnim(),
        containerId: Int
    ) {
        realMainActivity.replaceFragmentNew(fragment, bundle, keepToBackStack, screenAnim, containerId)
    }

    protected fun addFragmentNew(
        fragment: BaseFragment,
        bundle: Bundle? = null,
        keepToBackStack: Boolean = true,
        screenAnim: IScreenAnim = FadeAnim(),
        containerId: Int
    ) {
        realMainActivity.addFragmentNew(fragment, bundle, keepToBackStack, screenAnim, containerId)
    }

    private fun setActionBarColor() {
        if (this is CollectionFragment) {
            realMainActivity.setTransparentActionBar()
        } else {
            realMainActivity.setWhiteActionBar()
        }
    }

    private fun setActionBarSearchIcon() {
        realMainActivity.checkSearchIcon()
    }
}

