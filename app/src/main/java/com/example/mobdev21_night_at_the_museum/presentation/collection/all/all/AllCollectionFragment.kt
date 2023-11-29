package com.example.mobdev21_night_at_the_museum.presentation.collection.all.all

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.databinding.AllCollectionFragmentBinding
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.presentation.collection.CollectionFragment
import com.example.mobdev21_night_at_the_museum.presentation.collection.all.CollectionsAdapter
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class AllCollectionFragment: MuseumFragment<AllCollectionFragmentBinding>(R.layout.all_collection_fragment) {
    private val viewModel by viewModels<AllCollectionViewModel>()
    private val adapter by lazy { CollectionsAdapter() }

    override fun onInitView() {
        super.onInitView()
        initRecyclerView()
        viewModel.getListCollection(
            onSuccessAction = {
                binding.cvAllCollectionTabAll.submitList(it)
            },
            onFailureAction = {
                toast(it)
            }
        )
    }

    private fun initRecyclerView() {
        adapter.listener = object : CollectionsAdapter.IListener {
            override fun onCollectionClick(collection: MCollection) {
                museumActivity.addFragmentNew(
                    CollectionFragment(),
                    bundleOf(CollectionFragment.COLLECTION_ID_KEY to collection.key),
                    containerId = R.id.flRealMainContainer
                )
            }
        }

        binding.cvAllCollectionTabAll.apply {
            setAdapter(this@AllCollectionFragment.adapter)
            setLayoutManager(COLLECTION_MODE.GRID_VERTICAL)
            setMaxItemHorizontal(2)
        }
    }
}
