package com.example.mobdev21_night_at_the_museum.common.binding

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.common.view.BaseActivity

abstract class BaseBindingActivity<DB : ViewDataBinding> (layoutId: Int) : BaseActivity(layoutId) {

    protected val binding
        get() = _binding!!
    private var _binding: DB? = null

    protected var initSetFullScreen = false

    init {

    }

    //region lifecycle
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onInitView() {
        super.onInitView()
//        setFullScreen()
    }

    override fun attachView() {
        _binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }
    //endregion

    private fun setFullScreen() {
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

    fun setNormalScreen() {
        window?.apply {
            WindowCompat.setDecorFitsSystemWindows(this, true)
            //reset view
            (binding.root.layoutParams as ViewGroup.MarginLayoutParams).setMargins(0, 0, 0, 0)
            binding.root.requestLayout()
        }
    }
}
