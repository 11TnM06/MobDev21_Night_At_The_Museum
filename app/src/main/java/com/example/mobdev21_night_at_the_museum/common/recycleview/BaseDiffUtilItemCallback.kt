package com.example.mobdev21_night_at_the_museum.common.recycleview

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

open class BaseDiffUtilItemCallback<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.javaClass.declaredFields.contentEquals(newItem.javaClass.declaredFields)
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.javaClass === newItem.javaClass
    }
}

@Deprecated("không sử dụng trực tiếp, sắp tới sẽ chuyển thành abstract class")
open class BaseDiffUtilCallback<T : Any>(
    private val oldData: List<T>,
    private val newData: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldData.count()

    override fun getNewListSize() = newData.count()

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = true

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = false

    fun getOldItem(position: Int) = oldData[position]

    fun getNewItem(position: Int) = newData[position]
}
