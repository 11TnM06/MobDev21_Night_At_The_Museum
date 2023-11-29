package com.example.mobdev21_night_at_the_museum.common.extension

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.net.Uri
import android.os.Build
import android.text.Html
import android.util.Base64
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.webkit.MimeTypeMap
import android.webkit.WebView
import android.widget.*
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.core.widget.CompoundButtonCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.IViewListener
import com.example.mobdev21_night_at_the_museum.common.LoaderFactory
import com.example.mobdev21_night_at_the_museum.common.ToastUtils
import com.example.mobdev21_night_at_the_museum.common.binding.BaseBindingActivity
import com.example.mobdev21_night_at_the_museum.common.binding.BaseBindingFragment
import com.example.mobdev21_night_at_the_museum.common.binding.CORNER_TYPE
import com.example.mobdev21_night_at_the_museum.common.exception.APIException
import com.example.mobdev21_night_at_the_museum.common.exception.DBException
import com.example.mobdev21_night_at_the_museum.common.exception.HandleExceptionImpl
import com.example.mobdev21_night_at_the_museum.common.usecase.FlowResult
import com.example.mobdev21_night_at_the_museum.common.usecase.UI_STATE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


const val DEFAULT_DEBOUNCE_INTERVAL = 350L

internal abstract class DebouncedOnClickListener(
    private val delayBetweenClicks: Long = DEFAULT_DEBOUNCE_INTERVAL
) : View.OnClickListener {
    private var lastClickTimestamp = -1L

    @Deprecated(
        message = "onDebouncedClick should be overridden instead.",
        replaceWith = ReplaceWith("onDebouncedClick(v)")
    )

    override fun onClick(v: View) {
        val now = System.currentTimeMillis()
        if (lastClickTimestamp == -1L || now >= (lastClickTimestamp + delayBetweenClicks)) {
            onDebouncedClick(v)
        }
        lastClickTimestamp = now
    }

    abstract fun onDebouncedClick(v: View)
}

fun View.onSafeClick(listener: View.OnClickListener?) {
    listener?.let {
        this.onDebouncedClick { _ ->
            it.onClick(this)
        }
    }
}

fun View.setOnSafeClick(
    delayBetweenClicks: Long = DEFAULT_DEBOUNCE_INTERVAL,
    click: (view: View) -> Unit
) {
    setOnClickListener(object : DebouncedOnClickListener(delayBetweenClicks) {
        override fun onDebouncedClick(v: View) = click(v)
    })
}

fun View.onDebouncedClick(
    delayBetweenClicks: Long = DEFAULT_DEBOUNCE_INTERVAL,
    click: (view: View) -> Unit
) {
    setOnClickListener(object : DebouncedOnClickListener(delayBetweenClicks) {
        override fun onDebouncedClick(v: View) = click(v)
    })
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.clickedInside(event: MotionEvent): Boolean {
    return clickedInside(event.rawX, event.rawY)
}

fun View.clickedInside(rowX: Float, rowY: Float): Boolean {
    val loc = this.getLocationPositionOnScreen()
    return (rowX >= loc[0])
            && (rowX <= loc[0] + width)
            && (rowY >= loc[1])
            && (rowY <= loc[1] + height)
}

fun View.getLocationPositionOnScreen(): IntArray {
    val loc = IntArray(2)
    this.getLocationOnScreen(loc)
    return loc
}

@Suppress("DEPRECATION")
fun AppCompatTextView.setTextHtml(string: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(string, HtmlCompat.FROM_HTML_MODE_LEGACY)
    } else {
        this.text = Html.fromHtml(string)
    }
}

fun AppCompatTextView.setDrawableStart(icon: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
}

fun AppCompatTextView.setColor(color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}

fun getAppString(@StringRes stringId: Int, context: Context? = getApplication()): String {
    return context?.getString(stringId) ?: ""
}

fun getAppString(
    @StringRes resId: Int,
    vararg formatArgs: Any?,
    context: Context? = getApplication()
): String {
    return context?.getString(resId, *formatArgs) ?: ""
}

fun getAppFont(@FontRes fontId: Int, context: Context? = getApplication()): Typeface? {
    context?.let {
        return ResourcesCompat.getFont(context, fontId)
    }
    return null
}

fun getAppDrawable(@DrawableRes drawableId: Int, context: Context? = getApplication()): Drawable? {
    if (context == null) {
        return null
    }
    return ContextCompat.getDrawable(context, drawableId)
}

fun getAppDimensionPixel(@DimenRes dimenId: Int, context: Context? = getApplication()) =
    context?.resources?.getDimensionPixelSize(dimenId) ?: -1

fun getAppDimension(@DimenRes dimenId: Int, context: Context? = getApplication()) =
    context?.resources?.getDimension(dimenId) ?: -1f

fun getAppColor(@ColorRes colorRes: Int, context: Context? = getApplication()) =
    context?.let { ContextCompat.getColor(it, colorRes) } ?: Color.TRANSPARENT


fun Context.getResIdFromAttribute(attr: Int): Int {
    if (attr == 0)
        return 0
    val typedValue = TypedValue()
    theme?.resolveAttribute(attr, typedValue, true)
    return typedValue.resourceId
}

fun Context.getStringFromAttribute(attr: Int, removeAlphaColor: Boolean = false): String {
    val value = getString(getResIdFromAttribute(attr))
    if (removeAlphaColor) return value.removeRange(0, 3)
    return value
}

fun Fragment.getContextInFragment(activity: (FragmentActivity) -> Unit = {}) {
    this.activity?.let {
        activity.invoke(it)
    }
}

fun TextView.textColor(color: String?) {
    setTextColor(Color.parseColor(color))
}

fun TextView.textHintColor(color: String?) {
    setHintTextColor(Color.parseColor(color))
}

fun Drawable.setColor(colorValue: String?) {
    when (this) {
        is ShapeDrawable -> paint.color = Color.parseColor(colorValue)
        is ColorDrawable -> color = Color.parseColor(colorValue)
    }
}


fun <DATA> MutableStateFlow<FlowResult<DATA>>.data(): DATA? {
    return this.value.data
}

fun <DATA> MutableStateFlow<FlowResult<DATA>>.success(data: DATA) {
    this.value = FlowResult.newInstance<DATA>().success(data)
}

fun <DATA> MutableStateFlow<FlowResult<DATA>>.failure(throwable: Throwable, data: DATA? = null) {
    this.value = FlowResult.newInstance<DATA>().failure(throwable, data)
}

fun <DATA> MutableStateFlow<FlowResult<DATA>>.loading(message: String? = null) {
    this.value = FlowResult.newInstance<DATA>().loading(message)
}

fun <DATA> MutableStateFlow<FlowResult<DATA>>.initial() {
    this.value = FlowResult.newInstance<DATA>().initial()
}

fun <DATA> MutableStateFlow<FlowResult<DATA>>.reset() {
    this.value = FlowResult.newInstance<DATA>().reset()
}

fun <T> Flow<T>.onException(onCatch: suspend (Throwable) -> Unit): Flow<T> {
    return catch { e ->
        when (e) {
            is APIException -> {
                e.printStackTrace()
                val exception = APIException(e.code, HandleExceptionImpl.getMessage(e))
                onCatch(exception)
            }
            is DBException -> {
                e.printStackTrace()
                val exception = DBException(e.code, HandleExceptionImpl.getMessage(e))
                onCatch(exception)
            }
            else -> {
                e.printStackTrace()
                onCatch(e)
            }
        }
    }
}

fun <T> BaseBindingActivity<*>.handleUiState(
    flowResult: FlowResult<T>,
    listener: IViewListener? = null,
    canShowLoading: Boolean = false,
    canHideLoading: Boolean = false,
    canShowError: Boolean = true,
    handleBlock: Boolean = true
) {
    when (flowResult.getUiState()) {
        UI_STATE.INITIAL -> {
            listener?.onInitial()
        }
        UI_STATE.LOADING -> {
            listener?.onLoading()
        }
        UI_STATE.FAILURE -> {
            listener?.onFailure()
        }
        UI_STATE.SUCCESS -> {
            listener?.onSuccess()
        }
    }
}

fun <T> BaseBindingFragment<*>.handleUiState(
    flowResult: FlowResult<T>,
    listener: IViewListener? = null,
    canShowLoading: Boolean = false,
    canHideLoading: Boolean = false,
    canShowError: Boolean = true,
    handleBlock: Boolean = true
) {
    when (flowResult.getUiState()) {
        UI_STATE.INITIAL -> {
            listener?.onInitial()
        }
        UI_STATE.LOADING -> {
            listener?.onLoading()
        }
        UI_STATE.FAILURE -> {
            listener?.onFailure()
        }
        UI_STATE.SUCCESS -> {
            listener?.onSuccess()
        }
    }
}

fun getPlaceHolderDefault(): Drawable? {
//    return getAppDrawable(R.drawable.ic_no_img_default)
    return null
}

private fun getPlaceHolderUser(): Drawable? {
//    return getAppDrawable(R.drawable.ic_avatar_default)
    return null
}

fun ImageView.loadUser(
    url: String?,
    ignoreCache: Boolean = false,
    placeHolder: Drawable? = getPlaceHolderUser()
) {
    LoaderFactory.glide().loadImage(
        view = this,
        url = url,
        placeHolder = placeHolder,
        ignoreCache = ignoreCache
    )
}

fun ImageView.loadImage(
    url: String?,
    ignoreCache: Boolean = false,
    placeHolder: Drawable? = getPlaceHolderDefault()
) {
    LoaderFactory.glide().loadImage(
        view = this,
        url = url,
        placeHolder = placeHolder,
        ignoreCache = ignoreCache
    )
}

fun ImageView.loadImageBlur(
    url: String?,
    ignoreCache: Boolean = false,
    placeHolder: Drawable? = getPlaceHolderDefault(),
    radius: Int = 100,
    sampling: Int = 10
) {
    LoaderFactory.glide().loadImageBlur(
        view = this,
        url = url,
        placeHolder = placeHolder,
        ignoreCache = ignoreCache,
        radius = radius,
        sampling = sampling
    )
}

fun ImageView.loadImage(
    drawable: Drawable?,
    ignoreCache: Boolean = false,
    placeHolder: Drawable? = getPlaceHolderDefault()
) {
    LoaderFactory.glide().loadImage(
        view = this,
        drawable = drawable,
        placeHolder = placeHolder,
        ignoreCache = ignoreCache
    )
}

fun ImageView.loadImageBase64(
    base64: String?,
    ignoreCache: Boolean = false
) {
    LoaderFactory.glide().loadImageBase64(
        view = this,
        base64 = base64,
        placeHolder = getPlaceHolderDefault(),
        ignoreCache = ignoreCache
    )
}

fun ImageView.loadRoundCornerImage(
    url: String?,
    corner: Int,
    ignoreCache: Boolean = false,
    cornerType: CORNER_TYPE = CORNER_TYPE.ALL
) {
    LoaderFactory.glide().loadRoundCornerImage(
        view = this,
        url = url,
        corner = corner,
        placeHolder = getPlaceHolderDefault(),
        ignoreCache = ignoreCache,
        cornerType = cornerType
    )
}

fun ImageView.loadCircleImage(
    url: String?,
    ignoreCache: Boolean = false
) {
    LoaderFactory.glide().loadCircleImage(
        view = this,
        url = url,
        placeHolder = getPlaceHolderUser(),
        ignoreCache = ignoreCache
    )
}

fun ImageView.loadGif(
    gif: Int,
    ignoreCache: Boolean = false
) {
    LoaderFactory.glide().loadGif(
        view = this,
        gif = gif,
        placeHolder = getPlaceHolderUser(),
        ignoreCache = ignoreCache
    )
}

fun ImageView.scale(factor: Float, reset: Boolean) {
    animate()
        .scaleX(factor)
        .scaleY(factor)
        .translationY(
            if (reset) {
                0f
            } else {
                -(height / 3f)
            }
        )
        .setDuration(100)
        .start()
    invalidate()
}

fun Context.getStatusBarHeight(): Int {
    return try {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        resources.getDimensionPixelSize(resourceId)
    } catch (e: Exception) {
        0
    }
}

fun Context.getNavigationBarHeight(): Int {
    return try {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        resources.getDimensionPixelSize(resourceId)
    } catch (e: Exception) {
        0
    }
}

fun getTypeImage(uri: Uri): String? {
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(getApplication().contentResolver.getType(uri))
}

fun RecyclerView.setMaxViewPoolSize(maxViewTypeId: Int, maxPoolSize: Int) {
    for (i in 0..maxViewTypeId) {
        recycledViewPool.setMaxRecycledViews(i, maxPoolSize)
    }
}

fun Float.roundToOneDecimalPlace(): Float {
    val df = DecimalFormat("#.#", DecimalFormatSymbols(Locale.ENGLISH)).apply {
        roundingMode = RoundingMode.HALF_UP
    }
    return df.format(this).toFloat()
}

fun <DATA> Fragment.coroutinesLaunch(
    flow: Flow<FlowResult<DATA>>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    launch: suspend (flowResult: FlowResult<DATA>) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(state = state) {
            flow.collect {
                launch.invoke(it)
            }
        }
    }
}

fun <DATA> AppCompatActivity.coroutinesLaunch(
    flow: Flow<FlowResult<DATA>>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    launch: suspend (flowResult: FlowResult<DATA>) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(state = state) {
            flow.collect {
                launch.invoke(it)
            }
        }
    }
}

fun Context.toast(msg: String?) {
    ToastUtils.show(this, msg, Toast.LENGTH_SHORT)
}

fun Context.toastUndeveloped() {
    val msg = getAppString(R.string.undeveloped)
    ToastUtils.show(this, msg, Toast.LENGTH_SHORT)
}

fun AppCompatActivity.toast(msg: String?) {
    ToastUtils.show(this, msg, Toast.LENGTH_SHORT)
}

fun toast(msg: String?) {
    ToastUtils.show(getApplication(), msg, Toast.LENGTH_SHORT)
}

fun AppCompatActivity.toastUndeveloped() {
    val msg = getAppString(R.string.undeveloped)
    ToastUtils.show(this, msg, Toast.LENGTH_SHORT)
}

fun AppCompatActivity.hideToast() {
    ToastUtils.hide()
}

fun Fragment.toast(msg: String?) {
    context?.let {
        ToastUtils.show(it, msg, Toast.LENGTH_SHORT)
    }
}

fun Fragment.toastUndeveloped() {
    val msg = getAppString(R.string.undeveloped)
    context?.let {
        ToastUtils.show(it, msg, Toast.LENGTH_SHORT)
    }
}

fun Fragment.navigateToShare(exTitle: String? = null, exContent: String? = null) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, exTitle)
        putExtra(Intent.EXTRA_TEXT, exContent)
    }
    startActivity(Intent.createChooser(intent, null))
}

fun Fragment.hideToast() {
    ToastUtils.hide()
}

fun getNavOptionWithoutAnim(
    @IdRes resId: Int? = null,
    inclusive: Boolean = false,
): NavOptions {
    val navOptions = NavOptions.Builder()
    resId?.let {
        navOptions.setPopUpTo(resId, inclusive)
    }
    return navOptions.build()
}

fun getUpNavOptions(
    @IdRes resId: Int? = null,
    inclusive: Boolean = false,
    isBackToPrevious: Boolean = false
): NavOptions {
    val navOptions = if (isBackToPrevious) {
        NavOptions.Builder()
            .setEnterAnim(R.anim.slide_pop_enter_right_to_left)
            .setExitAnim(R.anim.slide_pop_exit_left_to_right)
            .setPopEnterAnim(R.anim.slide_enter_left_to_right)
            .setPopExitAnim(R.anim.slide_exit_right_to_left)
    } else {
        NavOptions.Builder()
            .setEnterAnim(R.anim.slide_enter_left_to_right)
            .setExitAnim(R.anim.slide_exit_right_to_left)
            .setPopEnterAnim(R.anim.slide_pop_enter_right_to_left)
            .setPopExitAnim(R.anim.slide_pop_exit_left_to_right)
    }

    resId?.let {
        navOptions.setPopUpTo(resId, inclusive)
    }

    return navOptions.build()
}

const val DELAY_TRANSITION_SCREEN = 200L

fun ViewModel.waitTransitionScreen(onAction: () -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        delay(DELAY_TRANSITION_SCREEN)
        onAction.invoke()
    }
}

fun CheckBox.setTint(@ColorInt color: Int?) {
    if (color == null) {
        CompoundButtonCompat.setButtonTintList(this, null)
        return
    }
    CompoundButtonCompat.setButtonTintMode(this, PorterDuff.Mode.SRC_ATOP)
    CompoundButtonCompat.setButtonTintList(this, ColorStateList.valueOf(color))
}

fun EditText.selectionLast() {
    post {
        setSelection(text?.length ?: 0)
    }
}

fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference(): Int = snapMode
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}

fun SwipeRefreshLayout.hideRefresh() {
    isRefreshing = false
}

fun WebView.loadHtml(htmlData: String?) {
    if (htmlData != null) {
        val encodedHtml: String = Base64.encodeToString(htmlData.toByteArray(), Base64.NO_PADDING)
        loadData(encodedHtml, "text/html", "base64")
    }
}
