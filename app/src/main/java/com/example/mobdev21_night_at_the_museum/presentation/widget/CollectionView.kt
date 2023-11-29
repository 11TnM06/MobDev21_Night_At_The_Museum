package com.example.mobdev21_night_at_the_museum.presentation.widget

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Dimension
import androidx.annotation.IntRange
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.smoothSnapToPosition
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseAdapter

class CollectionView constructor(
    ctx: Context,
    attrs: AttributeSet?
) : RecyclerView(ctx, attrs) {

    companion object {

    }

    private var baseAdapter: BaseAdapter? = null
    private var layoutManagerMode: COLLECTION_MODE = COLLECTION_MODE.VERTICAL
    private var endlessScrollListener: BaseRecyclerViewEndlessScrollListener? = null
    private var loadMoreConsumer: (() -> Unit)? = null
    private var scrollConsumer: (() -> Unit)? = null
    private var emptyConsumer: (() -> Unit)? = null
    private var refreshConsumer: (() -> Unit)? = null
    private var hasFixedSize: Boolean = true
    private var isReverseView = false
    private var maxItemHorizontal: Int = 2

    init {
        initView(attrs)
    }

    fun setAdapter(adapter: BaseAdapter) {
        baseAdapter = adapter
        this.adapter = baseAdapter
    }

    fun setAdapter(adapter: BaseGridAdapter) {
        baseAdapter = adapter
        this.adapter = baseAdapter
    }

    fun isEmpty(): Boolean {
        return baseAdapter?.isEmpty() ?: true
    }

    fun setMaxItemHorizontal(@IntRange(from = 1, to = 10) number: Int) {
        maxItemHorizontal = number
    }

    fun setLayoutManager(
        mode: COLLECTION_MODE = COLLECTION_MODE.VERTICAL,
        isReverse: Boolean = false
    ) {
        layoutManagerMode = mode
        isReverseView = isReverse
        layoutManager = getLayoutManager(mode)
    }

    private fun getLayoutManager(mode: COLLECTION_MODE): LayoutManager {
        return when (mode) {
            COLLECTION_MODE.VERTICAL -> CustomLinearLayoutManager(context, LinearLayoutManager.VERTICAL, isReverseView)
            COLLECTION_MODE.HORIZONTAL -> CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, isReverseView)
            COLLECTION_MODE.GRID_VERTICAL -> getGridLayoutManager()
            COLLECTION_MODE.Pswrd -> getPswrdLayoutManager()
        }
    }

    fun setCustomPadding(
        @Dimension left: Int = 0,
        @Dimension top: Int = 0,
        @Dimension bottom: Int = 0,
        @Dimension right: Int = 0
    ) {
        updatePadding(left, top, right, bottom)
    }

    fun setCloseRefreshListener(consumer: (() -> Unit)? = null) {
        refreshConsumer = consumer
    }

    fun setLoadMoreListener(isReverse: Boolean = false, consumer: (() -> Unit)? = null) {
        loadMoreConsumer = consumer

        if (isReverse) {
            endlessScrollListener = object : EndlessUpScrollListener(this.layoutManager!!) {
                override fun onLoadMore() {
                    baseAdapter?.makeLoadMore()
                    baseAdapter?.itemCount?.let { smoothScrollToPosition(it) }
                    loadMoreConsumer?.invoke()
                }

                override fun onScrollAction() {
                    scrollConsumer?.invoke()
                }
            }
        } else {
            endlessScrollListener = object : BaseRecyclerViewEndlessScrollListener(this.layoutManager!!) {
                override fun onLoadMore() {
                    baseAdapter?.makeLoadMore()
                    baseAdapter?.itemCount?.let { smoothScrollToPosition(it) }
                    loadMoreConsumer?.invoke()
                }

                override fun onScrollAction() {
                    scrollConsumer?.invoke()
                }
            }
        }

        endlessScrollListener?.let {
            this.addOnScrollListener(it)
        }
    }

    fun setScrollListener(consumer: (() -> Unit)? = null) {
        scrollConsumer = consumer
    }

    fun setEmptyListener(consumer: (() -> Unit)? = null) {
        emptyConsumer = consumer
    }

    fun showLoading() {
        hideLoading()
        baseAdapter?.makeLoading()
    }

    fun hideLoading() {
        baseAdapter?.removeLoading()
    }

    fun scrollFastToPosition(position: Int) {
        this.smoothSnapToPosition(position)
    }

    fun submitList(newData: List<Any>? = null, hasLoadMore: Boolean = true) {
        hideLoading()
        if (baseAdapter != null && baseAdapter!!.dataList.isNotEmpty()) {
            baseAdapter?.removeLoadMore()
        }
        endlessScrollListener?.setLoadMore(hasLoadMore)
        baseAdapter?.submitList(newData)
        if (newData != null) {
            if (newData.isEmpty()) {
                baseAdapter?.makeEmpty()
            }
        } else {
            baseAdapter?.makeLoading()
        }
        endlessScrollListener?.isLoading = false
        refreshConsumer?.invoke()
    }

    fun removeEmpty() {
        baseAdapter?.removeEmpty()
    }


    private fun getGridLayoutManager(): LayoutManager {
        val spanCount = getOptimalSpanCount(maxItemHorizontal)
        val gridLayoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                /**
                 * tạm thời chỉ áp dụng span size lookup với BaseAdapter,
                 * còn với GroupData thì sẽ xử lý sau
                 */
                return if (baseAdapter is BaseGridAdapter) {
                    (baseAdapter as BaseGridAdapter).getItemSpanSize(position, spanCount)
                } else {
                    spanCount
                }
            }
        }

        return gridLayoutManager
    }

    private fun getPswrdLayoutManager(): LayoutManager {
        val spanCount = PswrdAdapter.SPAN_COUNT
        val gridLayoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (baseAdapter is PswrdAdapter) {
                    (baseAdapter as PswrdAdapter).getItemSpanSize(position)
                } else {
                    spanCount
                }
            }
        }

        return gridLayoutManager
    }

    private fun getOptimalSpanCount(maxItemHorizontal: Int): Int {
        var spanCount = 1
        if (maxItemHorizontal <= 0) {
            return 0
        }
        for (i in 1..maxItemHorizontal) {
            spanCount *= maxItemHorizontal
        }
        return spanCount
    }

    private fun initView(attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CollectionView, 0, 0)

        this.adapter = baseAdapter
        overScrollMode = OVER_SCROLL_NEVER
        setLayoutManager()
        this.apply {
            setHasFixedSize(hasFixedSize)
            itemAnimator = DefaultItemAnimator()
            setItemViewCacheSize(50)
        }

        ta.recycle()
    }
}
