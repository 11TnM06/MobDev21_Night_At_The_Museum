package com.example.mobdev21_night_at_the_museum.common.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.mobdev21_night_at_the_museum.common.navigation.FadeAnim
import com.example.mobdev21_night_at_the_museum.common.navigation.IScreenAnim

abstract class BaseFragment(@LayoutRes protected val layoutId: Int) : Fragment(), BaseView {
    protected val TAG = this::class.java.simpleName
    private val baseActivity by lazy {
        requireActivity() as BaseActivity
    }
    protected lateinit var myInflater: LayoutInflater
    private lateinit var callback: OnBackPressedCallback
    protected lateinit var viewRoot: View


    init {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onPrepareInitView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::myInflater.isInitialized) {
            myInflater = LayoutInflater.from(requireActivity())
        }
        viewRoot = attachView(inflater, container, savedInstanceState)
        onInitBinding()
        return viewRoot
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        if (hasCustomBackPressByFragment()) {
            setBackPressedDispatcher()
        }
        setHasOptionsMenu(isAttachMenuToFragment())
        onInitView()
        onObserverViewModel()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onCleaned()
    }

    override fun onPrepareInitView() {
        setupStatusBar().let {
            setStatusColor(it.color, it.isDarkText)
        }
    }

    override fun onInitView() {
    }

    override fun onCleaned() {
        super.onCleaned()
    }

    open fun isAttachMenuToFragment(): Boolean = true

    open fun hasCustomBackPressByFragment(): Boolean = false

    open fun onBackPressByFragment() {}

    open fun getContainerId(): Int = LAYOUT_INVALID

    open fun setupStatusBar(): StatusBar {
        return baseActivity.setupStatusBar()
    }

    open fun attachView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(layoutId, container, false)
    }

    open fun isSoftInputAdjustResize(): Boolean {
        return baseActivity.isSoftInputAdjustResize()
    }

    //region navigate screen
    fun navigateTo(
        clazz: Class<out BaseActivity>,
        onCallback: (Intent) -> Unit = {}
    ) {
        baseActivity.navigateTo(clazz, onCallback)
    }

    fun navigateTo(
        clazz: Class<out BaseActivity>,
        bundle: Bundle, onCallback: (Intent) -> Unit = {}
    ) {
        baseActivity.navigateTo(clazz, bundle, onCallback)
    }

    fun navigateTo(
        clazz: Class<out BaseActivity>,
        fragmentClass: Class<out BaseFragment>,
        onCallback: (Intent) -> Unit = {}
    ) {
        baseActivity.navigateTo(clazz, fragmentClass, onCallback)
    }

    fun navigateTo(
        clazz: Class<out BaseActivity>,
        fragmentClass: Class<out BaseFragment>,
        bundle: Bundle, onCallback: (Intent) -> Unit = {}
    ) {
        baseActivity.navigateTo(clazz, fragmentClass, bundle, onCallback)
    }

    fun navigateBack() {
        baseActivity.navigateBack()
    }

    fun replaceFragment(
        fragment: BaseFragment,
        bundle: Bundle? = null,
        keepToBackStack: Boolean = true,
        screenAnim: IScreenAnim = FadeAnim()
    ) {
        baseActivity.replaceFragment(fragment, bundle, keepToBackStack, screenAnim)
    }

    fun addFragment(
        fragment: BaseFragment,
        bundle: Bundle? = null,
        keepToBackStack: Boolean = true,
        screenAnim: IScreenAnim = FadeAnim()
    ) {
        baseActivity.addFragment(fragment, bundle, keepToBackStack, screenAnim)
    }

    fun backFragment() {
        baseActivity.backFragment()
    }

    fun backFragment(tag: String) {
        baseActivity.backFragment(tag)
    }

    fun clearStackFragment() {
        baseActivity.clearStackFragment()
    }

    fun getCurrentFragment(): Fragment? {
        return baseActivity.getCurrentFragment()
    }

    fun replaceFragmentInsideFragment(
        fragment: BaseFragment,
        bundle: Bundle? = null,
        keepToBackStack: Boolean = true,
        screenAnim: IScreenAnim = FadeAnim()
    ) {
        includeFragment(
            fragment,
            bundle,
            getContainerId(),
            true,
            keepToBackStack,
            screenAnim
        )
    }

    fun addFragmentInsideFragment(
        fragment: BaseFragment,
        bundle: Bundle? = null,
        keepToBackStack: Boolean = true,
        screenAnim: IScreenAnim = FadeAnim()
    ) {
        includeFragment(
            fragment,
            bundle,
            getContainerId(),
            false,
            keepToBackStack,
            screenAnim
        )
    }
    //endregion

    fun setStatusColor(color: Int = android.R.color.black, isDarkText: Boolean = true) {
        baseActivity.setStatusColor(color, isDarkText)
    }

    private fun backScreenByFragmentManager(tag: String? = null) {
        if (tag != null) {
            baseActivity.backFragment(tag)
        } else {
            baseActivity.backFragment()
        }
    }

    private fun setBackPressedDispatcher() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressByFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    //region fragment backstack
    private fun includeFragment(
        fragment: Fragment,
        bundle: Bundle?,
        containerId: Int,
        isReplace: Boolean,
        keepToBackStack: Boolean,
        screenAnim: IScreenAnim
    ) {
        if (getContainerId() == LAYOUT_INVALID) {
            throw IllegalArgumentException("Cần phải gán container id để replace fragment")
        }
        try {
            val tag = fragment::class.java.simpleName
            bundle?.let {
                fragment.arguments = it
            }
            childFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    screenAnim.enter(),
                    screenAnim.exit(),
                    screenAnim.popEnter(),
                    screenAnim.popExit()
                )
                if (isReplace) {
                    replace(containerId, fragment, tag)
                } else {
                    add(containerId, fragment, tag)
                }
                if (keepToBackStack) {
                    addToBackStack(tag)
                }
                commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    //endregion
}
