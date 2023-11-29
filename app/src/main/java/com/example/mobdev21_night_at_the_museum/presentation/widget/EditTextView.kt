package com.example.mobdev21_night_at_the_museum.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.*
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.getAppColor
import com.example.mobdev21_night_at_the_museum.common.extension.getAppFont

internal class EditTextView @JvmOverloads constructor(ctx: Context, attrs: AttributeSet? = null) :
    LinearLayout(ctx, attrs) {

    lateinit var tvEditTextViewLabel: AppCompatTextView
    lateinit var llEditTextViewContent: LinearLayoutCompat
    lateinit var ivEditTextViewLeft: AppCompatImageView
    lateinit var ivEditTextViewClear: AppCompatImageView
    lateinit var ivEditTextViewRight: AppCompatImageView
    lateinit var edtEditTextViewContent: AppCompatEditText
    lateinit var tvEditTextViewContent: AppCompatTextView
    lateinit var tvEditTextViewError: AppCompatTextView

    private var isEnable: Boolean = true
    private var isVisibleClear: Boolean = false
    private var isActiveFocus: Boolean = false
    private var backGroundId: Int = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_edit_text_view, this, true)
        initView(context, attrs)
    }

    // Sự kiện khi click vào icon info
    private var onClickIconInfoConsumer: (() -> Unit)? = null

    // text change
    private var textChangeConsumer: ((String) -> Unit)? = null

    // Sự kiện khi click vào icon bên trái
    private var onClickIconLeftConsumer: (() -> Unit)? = null

    // Sự kiện khi click vào icon bên phải
    private var onClickIconRightConsumer: (() -> Unit)? = null

    // Sự kiện khi submit bàn phím
    private var onSubmitConsumer: (() -> Unit)? = null

    //sự kiện unfocus edittext
    private var onUnFocusConsumer: (() -> Unit)? = null

    //sự kiện unfocus edittext
    private var onFocusConsumer: (() -> Unit)? = null

    // Biến cho biết background có đang được hiển thị hay không
    private var isVisibleBackground: Boolean = true

    // Sẽ không ẩn hiện nút clear đối với trường nhập là mật khẩu
    private var isInputPassword: Boolean = false

    @SuppressLint("CheckResult")
    internal fun initView(context: Context, attrs: AttributeSet?) {
        orientation = VERTICAL
        tvEditTextViewLabel = findViewById(R.id.tvEditTextViewLabel)
        llEditTextViewContent = findViewById(R.id.llEditTextViewContent)
        ivEditTextViewLeft = findViewById(R.id.ivEditTextViewLeft)
        ivEditTextViewClear = findViewById(R.id.ivEditTextViewClear)
        ivEditTextViewRight = findViewById(R.id.ivEditTextViewRight)
        edtEditTextViewContent = findViewById(R.id.edtEditTextViewContent)
        tvEditTextViewContent = findViewById(R.id.tvEditTextViewContent)
        tvEditTextViewError = findViewById(R.id.tvEditTextViewError)

        ivEditTextViewLeft.setOnClickListener { onClickLeft() }
        ivEditTextViewClear.setOnClickListener { onClickClear() }
        ivEditTextViewRight.setOnClickListener { onClickRight() }

        // typed array
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.EditTextView, 0, 0)
        isActiveFocus = typedArray.getBoolean(R.styleable.EditTextView_edt_text_active_focus, false)

        // update label
        tvEditTextViewLabel.text = typedArray.getString(R.styleable.EditTextView_edt_text_label)
        tvEditTextViewLabel.visibility =
            if (tvEditTextViewLabel.text.toString().isEmpty()) View.GONE else View.VISIBLE
        tvEditTextViewLabel.setTextColor(
            typedArray.getColor(
                R.styleable.EditTextView_edt_text_label_color,
                getAppColor(R.color.black, context)
            )
        )

        // update content
        edtEditTextViewContent.setText(typedArray.getString(R.styleable.EditTextView_edt_text_content))
        tvEditTextViewContent.text = typedArray.getString(R.styleable.EditTextView_edt_text_content)

        //enable onClick view
        isEnable = typedArray.getBoolean(R.styleable.EditTextView_edt_text_enable, true)
        tvEditTextViewContent.visibility = if (isEnable) View.GONE else View.VISIBLE
        edtEditTextViewContent.visibility = if (isEnable) View.VISIBLE else View.GONE

        // text color
        edtEditTextViewContent.setTextColor(
            typedArray.getColor(
                R.styleable.EditTextView_edt_text_content_color,
                getAppColor(R.color.black, context)
            )
        )

        // Hint Color & hint
        edtEditTextViewContent.hint = typedArray.getString(R.styleable.EditTextView_edt_text_hint)
        edtEditTextViewContent.setHintTextColor(
            typedArray.getColor(
                R.styleable.EditTextView_edt_text_hint_color,
                getAppColor(R.color.gray, context)
            )
        )

        // input type
        val inputType =
            typedArray.getInt(R.styleable.EditTextView_android_inputType, EditorInfo.TYPE_NULL)
        if (inputType != EditorInfo.TYPE_NULL) {
            edtEditTextViewContent.inputType = inputType
            edtEditTextViewContent.typeface = getAppFont(R.font.roboto_medium)
        }

        // max length
        val maxLength =
            typedArray.getInt(R.styleable.EditTextView_android_maxLength, Integer.MAX_VALUE)
        edtEditTextViewContent.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))


        // ime options
        val imeOptions =
            typedArray.getInt(R.styleable.EditTextView_android_imeOptions, EditorInfo.IME_NULL)
        if (imeOptions != EditorInfo.IME_NULL) {
            edtEditTextViewContent.imeOptions = imeOptions
        }

        // content lines
        edtEditTextViewContent.setLines(
            typedArray.getInt(
                R.styleable.EditTextView_android_lines,
                1
            )
        )
        edtEditTextViewContent.maxLines =
            typedArray.getInt(R.styleable.EditTextView_android_maxLines, 1)

        // background content
        isVisibleBackground =
            typedArray.getBoolean(R.styleable.EditTextView_edt_text_is_visible_background, true)
        if (!isVisibleBackground) {
            llEditTextViewContent.background = null
        }

        if (isActiveFocus) {
            llEditTextViewContent.setBackgroundResource(R.drawable.shape_rectange_white_bg_orange_stroke_corner_8)
        } else {
            llEditTextViewContent.setBackgroundResource(R.drawable.shape_rectange_white_bg_gray_stroke_corner_8)
        }

        // Di chuyển con trỏ về cuối vị trí
        edtEditTextViewContent.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                setSelection(edtEditTextViewContent)
                onFocusConsumer?.invoke()
                ivEditTextViewLeft.setColorFilter(
                    getAppColor(R.color.black, context),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )
                ivEditTextViewRight.setColorFilter(
                    getAppColor(R.color.black, context),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )
            } else {
                onUnFocusConsumer?.invoke()

                ivEditTextViewLeft.setColorFilter(
                    getAppColor(R.color.gray, context),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )
                ivEditTextViewRight.setColorFilter(
                    getAppColor(R.color.gray, context),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )
            }

            // Ẩn/hiện nút clear
            setVisibleButtonClear()
        }

        // icon
        val iconLeft = typedArray.getDrawable(R.styleable.EditTextView_edt_text_ic_left)
        ivEditTextViewLeft.visibility = if (iconLeft == null) View.GONE else View.VISIBLE
        ivEditTextViewLeft.setImageDrawable(iconLeft)

        val iconRight =
            typedArray.getDrawable(R.styleable.EditTextView_edt_text_ic_right)
//        ivEditTextViewRight.setPadding(getAppDimensionPixel(ai.ftech.base.R.dimen.fbase_dimen_6))
        ivEditTextViewRight.visibility = if (iconRight == null) View.GONE else View.VISIBLE
        ivEditTextViewRight.setImageDrawable(iconRight)

        //show hide password
        val isShowHidePass =
            typedArray.getBoolean(R.styleable.EditTextView_edt_text_show_hide_password, false)
        if (isShowHidePass)
            onShowOrHidePassword(
                typedArray.getBoolean(
                    R.styleable.EditTextView_edt_text_show_password,
                    false
                )
            )

        //edit text horizontal padding
        val editTextHorizontalPadding = typedArray.getDimensionPixelSize(
            R.styleable.EditTextView_edt_text_horizontal_padding,
            -1
        )
        if (editTextHorizontalPadding != -1) {
            edtEditTextViewContent.setPadding(
                editTextHorizontalPadding,
                edtEditTextViewContent.paddingTop,
                editTextHorizontalPadding,
                edtEditTextViewContent.paddingBottom
            )
        }

        //edit text horizontal padding
        val editTextVerticalPadding =
            typedArray.getDimensionPixelSize(R.styleable.EditTextView_edt_text_vertical_padding, -1)
        if (editTextVerticalPadding != -1) {
            edtEditTextViewContent.setPadding(
                edtEditTextViewContent.paddingLeft,
                editTextVerticalPadding,
                edtEditTextViewContent.paddingRight,
                editTextVerticalPadding
            )
        }

        //text change listener
        edtEditTextViewContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {}

            override fun beforeTextChanged(
                charSequence: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textChangeConsumer?.invoke(charSequence.toString())
                setVisibleButtonClear()
            }
        })

        //submit listener
        edtEditTextViewContent.setOnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE) {
                onSubmitConsumer?.invoke()
                true
            }
            false
        }

        //check block copy paste edittext
        if (typedArray.getBoolean(R.styleable.EditTextView_edt_text_disable_copy_paste, false)) {
            edtEditTextViewContent.setOnLongClickListener {
                true
            }
        }

        // recycle
        typedArray.recycle()
    }


    fun setVisibleButtonClear() {
        ivEditTextViewClear.visibility =
            if (getContent().isNotEmpty() && !isInputPassword && isVisibleClear) View.VISIBLE else View.GONE
    }

    fun getEditText() = edtEditTextViewContent

    fun setContent(
        value: String?,
        isRemoveTextChange: Boolean = true,
        isHint: Boolean = false
    ): EditTextView {
        edtEditTextViewContent.setText(if (value.isNullOrEmpty()) "" else value)
        tvEditTextViewContent.text = value
        if (isHint)
            tvEditTextViewContent.setTextColor(getAppColor(R.color.neutral_6))
        else
            tvEditTextViewContent.setTextColor(getAppColor(R.color.black))
        setSelection(edtEditTextViewContent)
        setVisibleButtonClear()
        return this
    }

    fun setError(error: CharSequence?): EditTextView {
        isActiveFocus = true
        tvEditTextViewError.text = error
        tvEditTextViewError.visibility = if (error.isNullOrEmpty()) View.GONE else View.VISIBLE
        if (isVisibleBackground) {
            if (error.isNullOrEmpty()) {
                if (backGroundId != 0) {
                    llEditTextViewContent.setBackgroundResource(backGroundId)
                } else
                    setContentBackgroundNormal()
            } else {
                setContentBackgroundError()
            }
        } else {
            llEditTextViewContent.background = null
        }

        return this
    }

    fun addFilter(newFilter: InputFilter): EditTextView {
        edtEditTextViewContent.apply {
            filters = arrayOf(*filters, newFilter)
        }
        return this
    }

    fun getContent(): String {
        return if (isEnable) {
            edtEditTextViewContent.text.toString()
        } else {
            tvEditTextViewContent.text.toString()
        }
    }


    fun isEmpty(context: Context): Boolean {
        setError(null)

        if (getContent().isEmpty()) {
            setError(context.getString(R.string.error_field_must_not_be_empty))
            return true
        }

        return false
    }

    private fun onShowOrHidePassword(
        isShowPassword: Boolean,
        drawableIdShow: Int = R.drawable.ic_show_password_gray,
        drawableIdHide: Int = R.drawable.ic_hide_password_gray
    ) {
        // Trường này đánh dấu để cho biết input này đang nhập mật khẩu, nên sẽ không hiển thị nút clear
        isInputPassword = true

        // Hiển thị icon con mắt
        ivEditTextViewRight.visibility = View.VISIBLE

        // Gán icon con mắt
        if (isShowPassword) {
            ivEditTextViewRight.setImageResource(drawableIdShow)
            edtEditTextViewContent.transformationMethod = null
        } else {
            ivEditTextViewRight.setImageResource(drawableIdHide)
            edtEditTextViewContent.transformationMethod = PasswordTransformationMethod()
        }


        // Gán sự kiện vào nút bên phải
        ivEditTextViewRight.setOnClickListener {
            ivEditTextViewRight.setImageResource(if (edtEditTextViewContent.transformationMethod != null) drawableIdShow else drawableIdHide)
            edtEditTextViewContent.transformationMethod =
                if (edtEditTextViewContent.transformationMethod != null) null else PasswordTransformationMethod()
            setSelection(edtEditTextViewContent)
        }
    }

    fun setContentBackground(resId: Int) {
        backGroundId = resId
        isVisibleBackground = true
        llEditTextViewContent.setBackgroundResource(resId)
    }

    fun setContentBackgroundError() {
        llEditTextViewContent.setBackgroundResource(R.drawable.shape_rectange_white_bg_red_stroke_corner_8px)
    }

    fun setContentBackgroundNormal() {
        llEditTextViewContent.setBackgroundResource(R.drawable.shape_rectange_white_bg_gray_stroke_corner_8)
    }

    fun setContentBackGroundNotEdit() {
        llEditTextViewContent.setBackgroundResource(R.drawable.shape_rectangle_gray_bg_corner_8)
    }


    fun setImageRightResId(resId: Int): EditTextView {
        if (resId <= 0) {
            ivEditTextViewRight.setImageDrawable(null)
            ivEditTextViewRight.visibility = View.GONE
        } else {
            ivEditTextViewRight.setImageResource(resId)
            ivEditTextViewRight.visibility = View.VISIBLE
        }

        return this
    }


    fun setImageRightDrawable(drawable: Drawable?): EditTextView {
        ivEditTextViewRight.setImageDrawable(drawable)
        ivEditTextViewRight.visibility = if (drawable == null) View.GONE else View.VISIBLE
        return this
    }

    fun onClickLeft() {
        onClickIconLeftConsumer?.invoke()
    }

    fun onClickClear() {
        // Xóa nội dung
        edtEditTextViewContent.setText("")
        tvEditTextViewContent.text = ""
        ivEditTextViewClear.visibility = View.GONE

//         Hiển thị lại bàn phím
        setFocusNoDelay(context, edtEditTextViewContent)
    }

    fun onClickRight() {
        onClickIconRightConsumer?.invoke()
    }

    fun onClickInfo() {
        onClickIconInfoConsumer?.invoke()
    }

    fun setOnTextChangeConsumer(textChangeConsumer: ((String) -> Unit)?): EditTextView {
        this.textChangeConsumer = textChangeConsumer
        return this
    }

    fun setSubmitConsumer(onSubmitConsumer: (() -> Unit)?): EditTextView {
        this.onSubmitConsumer = onSubmitConsumer
        return this
    }

    fun setOnClickIconLeftConsumer(onClickIconLeftConsumer: (() -> Unit)?): EditTextView {
        this.onClickIconLeftConsumer = onClickIconLeftConsumer
        return this
    }

    fun setOnClickContentConsumer(onClickContentConsumer: (() -> Unit)?): EditTextView {
        tvEditTextViewContent.setOnClickListener { onClickContentConsumer?.invoke() }
        return this
    }

    fun setOnClickIconRightConsumer(onClickIconRightConsumer: (() -> Unit)?): EditTextView {
        this.onClickIconRightConsumer = onClickIconRightConsumer
        return this
    }

    fun setOnClickIconInfoConsumer(onClickIconInfoConsumer: (() -> Unit)?): EditTextView {
        this.onClickIconInfoConsumer = onClickIconInfoConsumer
        return this
    }

    fun setOnUnFocusConsumer(onUnFocusConsumer: (() -> Unit)?): EditTextView {
        this.onUnFocusConsumer = onUnFocusConsumer
        return this
    }

    fun setOnFocusConsumer(onFocusConsumer: (() -> Unit)?): EditTextView {
        this.onFocusConsumer = onFocusConsumer
        return this
    }

    fun setMaxLength(maxLength: Int): EditTextView {
        edtEditTextViewContent.filters += InputFilter.LengthFilter(maxLength)
        return this
    }

    fun requestFocusView(): EditTextView {
        edtEditTextViewContent.requestFocus()
        return this
    }

    fun setTextAlign(type: Int = View.TEXT_ALIGNMENT_TEXT_START): EditTextView {
        edtEditTextViewContent.textAlignment = type
        tvEditTextViewContent.textAlignment = type
        return this
    }

    fun setIsVisibleClear(isVisibleClear: Boolean): EditTextView {
        this.isVisibleClear = isVisibleClear
        return this
    }

    fun disable(disable: Boolean): EditTextView {
        edtEditTextViewContent.isEnabled = !disable
        if (disable) {
            ivEditTextViewClear.visibility = View.INVISIBLE
        } else {
            setVisibleButtonClear()
        }

        return this
    }

    fun setLabel(value: String?): EditTextView {
        tvEditTextViewLabel.text = value
        tvEditTextViewLabel.visibility = View.VISIBLE
        return this
    }

    fun setSizeLabel(value: Float): EditTextView {
        tvEditTextViewContent.textSize = value
        tvEditTextViewLabel.visibility = View.VISIBLE
        return this
    }

    fun getLabel(): String {
        return tvEditTextViewLabel.text.toString()

    }

    fun setInputType(inputType: Int): EditTextView {
        edtEditTextViewContent.inputType = inputType
        return this
    }

    fun setSelection(textView: AppCompatEditText?) {
        textView?.post { setSelectionNoDelay(textView) }
    }

    fun setSelectionNoDelay(textView: AppCompatEditText?) {
        textView?.apply {
            val text = text.toString()
            if (this is EditText && text.isNotEmpty()) {
                setSelection(text.length)
            }
        }
    }

//    fun isValidPass(pass: String): Boolean {
//        //return pass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#^])[A-Za-z\\d@$!%*?&#^]{8,}$".toRegex())
//        return pass.matches("^(?=.*?[A-Z]{1,})(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}$".toRegex())
//    }

    fun setFocusNoDelay(context: Context, textView: AppCompatEditText?) {
        textView?.apply {
            requestFocus()
            val inputMethodManager: InputMethodManager? =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            setSelection(this)
        }
    }
}
