package com.example.mobdev21_night_at_the_museum.presentation.streetview.list

import androidx.fragment.app.viewModels
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.databinding.StreetViewListFragmentBinding
import com.example.mobdev21_night_at_the_museum.domain.model.StreetView
import com.example.mobdev21_night_at_the_museum.presentation.RealMainActivity
import com.example.mobdev21_night_at_the_museum.presentation.streetview.item.StreetViewFragment
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class AllStreetViewFragment : MuseumFragment<StreetViewListFragmentBinding>(R.layout.street_view_list_fragment) {
    private val adapter by lazy { AllStreetViewAdapter() }
    private val viewModel by viewModels<AllStreetViewModel>()

    override fun onInitView() {
        super.onInitView()
        (museumActivity as? RealMainActivity)?.apply {
            setBackIcon()
            expandAppBar()
            enableFragmentContainerScrollingBehavior()
            setWhiteActionBar()
        }

        adapter.listener = object : AllStreetViewAdapter.IListener {
            override fun onStreetViewClick(streetView: StreetView) {
                museumActivity.addFragmentNew(
                    StreetViewFragment().apply { this.location = streetView.location },
                    containerId = R.id.flRealMainContainer
                )
            }
        }

        binding.cvAllStreetView.apply {
            setAdapter(this@AllStreetViewFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }

        getData()
    }

    private fun getData() {
        viewModel.getAllStreetView(onSuccess = {
            binding.cvAllStreetView.submitList(viewModel.countAndStreetViews)
        }, onFailure = {
            toast("Failed to get street view list: $it")
        })
    }
}
