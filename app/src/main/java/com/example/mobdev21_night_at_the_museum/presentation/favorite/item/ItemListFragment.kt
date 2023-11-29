package com.example.mobdev21_night_at_the_museum.presentation.favorite.item

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.mobdev21_night_at_the_museum.AppPreferences
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.databinding.ItemListFragmentBinding
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.presentation.RealMainActivity
import com.example.mobdev21_night_at_the_museum.presentation.item.ItemFragment
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class ItemListFragment : MuseumFragment<ItemListFragmentBinding>(R.layout.item_list_fragment) {
    companion object {
        const val ITEMS_KEY = "ITEMS_KEY"
    }

    private val adapter by lazy { ItemListAdapter() }
    private val viewModel by viewModels<ItemListViewModel>()

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        viewModel.items = arguments?.getParcelableArrayList(ITEMS_KEY) ?: emptyList()
    }

    override fun onInitView() {
        super.onInitView()
        (museumActivity as? RealMainActivity)?.apply {
            setBackIcon()
            expandAppBar()
        }

        adapter.listener = object : ItemListAdapter.IListener {
            override fun onItemClick(item: Item?) {
                museumActivity.addFragmentNew(
                    ItemFragment(),
                    bundleOf(ItemFragment.ITEM_ID_KEY to item?.key),
                    containerId = R.id.flRealMainContainer
                )
            }
        }

        binding.cvItemList.apply {
            setAdapter(this@ItemListFragment.adapter)
            setLayoutManager(COLLECTION_MODE.Pswrd)
        }
        viewModel.calculateSizeOfListImage {
            val tempList = mutableListOf<Any>()
            tempList.add(AppPreferences.getUserInfo().fitems?.size ?: 0)
            tempList.addAll(viewModel.itemDisplays)
            binding.cvItemList.submitList(tempList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (museumActivity as? RealMainActivity)?.apply {
            disableFragmentContainerScrollingBehavior()
        }
    }
}
