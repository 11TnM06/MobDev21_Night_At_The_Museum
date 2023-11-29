package com.example.mobdev21_night_at_the_museum.presentation.widget

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndlessUpScrollListener(
    private var layoutManager: RecyclerView.LayoutManager
) : BaseRecyclerViewEndlessScrollListener(layoutManager) {

    private var isScrolledUp = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        isScrolledUp = dy < 0

        if (isLoading || lastPage || !isScrolledUp) return

        val totalItemCount = layoutManager.itemCount
        var pastVisibleItems = 0
        when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                pastVisibleItems = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> pastVisibleItems = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            is LinearLayoutManager -> pastVisibleItems = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

        if (!isLoading && totalItemCount > 0) {
            if (pastVisibleItems == totalItemCount - 1 && !lastPage) {
                onLoadMore()
                isLoading = true
            }
            if (lastPage) {
                onLastPage()
            }
        }
    }
}
