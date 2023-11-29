package com.example.mobdev21_night_at_the_museum.presentation.favorite.collection

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.databinding.FavoriteCollectionsFragmentBinding
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.presentation.RealMainActivity
import com.example.mobdev21_night_at_the_museum.presentation.collection.CollectionFragment
import com.example.mobdev21_night_at_the_museum.presentation.collection.all.CollectionsAdapter
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class FavoriteCollectionsFragment : MuseumFragment<FavoriteCollectionsFragmentBinding>(R.layout.favorite_collections_fragment) {
    companion object {
        const val COLLECTION_LIST_KEY = "ITEMS_KEY"
    }

    private val adapter by lazy { FavoriteCollectionAdapter() }
    private val viewModel by viewModels<FavoriteCollectionsViewModel>()

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        viewModel.collections = arguments?.getParcelableArrayList(COLLECTION_LIST_KEY) ?: emptyList()
    }

    override fun onInitView() {
        super.onInitView()
        (museumActivity as? RealMainActivity)?.apply {
            setBackIcon()
            expandAppBar()
        }

        adapter.listener = object : CollectionsAdapter.IListener {
            override fun onCollectionClick(collection: MCollection) {
                museumActivity.addFragmentNew(
                    CollectionFragment(),
                    bundleOf(CollectionFragment.COLLECTION_ID_KEY to collection.key),
                    containerId = R.id.flRealMainContainer
                )
            }
        }

        binding.cvFavoriteCollections.apply {
            setAdapter(this@FavoriteCollectionsFragment.adapter)
            setLayoutManager(COLLECTION_MODE.GRID_VERTICAL)
            setMaxItemHorizontal(2)

            val newList = mutableListOf<Any>()
            newList.add(viewModel.collections.size)
            newList.addAll(viewModel.collections)
            submitList(newList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (museumActivity as RealMainActivity).disableFragmentContainerScrollingBehavior()
    }
}
