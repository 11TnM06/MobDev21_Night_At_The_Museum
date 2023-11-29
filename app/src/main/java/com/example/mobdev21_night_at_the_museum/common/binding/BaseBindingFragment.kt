package com.example.mobdev21_night_at_the_museum.common.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.common.view.BaseFragment

abstract class BaseBindingFragment<DB : ViewDataBinding>(layoutId: Int) : BaseFragment(layoutId) {
    protected val binding get() = _binding!!
    private var _binding: DB? = null

    open fun hasClickable() = false

    override fun attachView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(myInflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onInitView() {
        super.onInitView()
        if (!hasClickable()) {
            disableClickRootView()
        }
    }

    private fun disableClickRootView() {
        binding.root.isFocusable = true
        binding.root.isClickable = true
    }

}
