package com.example.mobdev21_night_at_the_museum.presentation.home

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.databinding.HomeFragmentBinding
import com.example.mobdev21_night_at_the_museum.domain.model.StreetView
import com.example.mobdev21_night_at_the_museum.presentation.collection.CollectionFragment
import com.example.mobdev21_night_at_the_museum.presentation.collection.all.CollectionsFragment
import com.example.mobdev21_night_at_the_museum.presentation.item.ItemFragment
import com.example.mobdev21_night_at_the_museum.presentation.streetview.item.StreetViewFragment
import com.example.mobdev21_night_at_the_museum.presentation.streetview.list.AllStreetViewFragment
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class HomeFragment : MuseumFragment<HomeFragmentBinding>(R.layout.home_fragment) {
    private val adapter by lazy { HomeAdapter() }
    private val viewModel by viewModels<HomeViewModel>()

    override fun onInitView() {
        super.onInitView()
        initRecyclerView()
        viewModel.getHomeData (
            onSuccessAction = {
                binding.cvHome.submitList(viewModel.list)
            },
            onFailureAction = {
                toast("Fail: $it")
            }
        )
    }

    private fun initRecyclerView() {
        adapter.listener = object : HomeAdapter.IListener {
            override fun onStreetViewClick(streetView: StreetView) {
                museumActivity.addFragmentNew(
                    StreetViewFragment().apply { this.location = streetView.location },
                    containerId = R.id.flRealMainContainer
                )
            }

            override fun onViewAllStreetViewClick() {
                realMainActivity.addFragmentNew(
                    AllStreetViewFragment(),
                    containerId = R.id.flRealMainContainer,
                    isEnableFragmentContainerScrollingBehavior = true
                )
            }

            override fun onViewAllCollections() {
                museumActivity.addFragmentNew(
                    CollectionsFragment(),
                    containerId = R.id.flRealMainContainer
                )
            }

            override fun onCollectionClick(collectionId: String?) {
                museumActivity.addFragmentNew(
                    CollectionFragment(),
                    bundleOf(CollectionFragment.COLLECTION_ID_KEY to collectionId),
                    containerId = R.id.flRealMainContainer
                )
            }

            override fun onItemClick(itemId: String?) {
                museumActivity.addFragmentNew(
                    ItemFragment(),
                    bundleOf(ItemFragment.ITEM_ID_KEY to itemId),
                    containerId = R.id.flRealMainContainer
                )
            }
        }

        binding.cvHome.apply {
            setAdapter(this@HomeFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }
    }
}
