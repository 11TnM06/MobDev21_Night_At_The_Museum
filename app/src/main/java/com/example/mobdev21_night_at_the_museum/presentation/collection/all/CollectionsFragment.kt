package com.example.mobdev21_night_at_the_museum.presentation.collection.all

import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.getAppColor
import com.example.mobdev21_night_at_the_museum.common.extension.gone
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.extension.show
import com.example.mobdev21_night_at_the_museum.databinding.CollectionsFragmentBinding
import com.example.mobdev21_night_at_the_museum.presentation.MainViewPagerAdapter
import com.example.mobdev21_night_at_the_museum.presentation.RealMainActivity
import com.example.mobdev21_night_at_the_museum.presentation.collection.all.all.AllCollectionFragment
import com.example.mobdev21_night_at_the_museum.presentation.collection.all.az.AZCollectionFragment

class CollectionsFragment: MuseumFragment<CollectionsFragmentBinding>(R.layout.collections_fragment) {
    private val pagerAdapter by lazy { MainViewPagerAdapter(childFragmentManager) }
    private val fragmentList = mutableListOf<MuseumFragment<*>>()
    private val allFragment by lazy { AllCollectionFragment() }
    private val azFragment by lazy { AZCollectionFragment() }

    override fun onInitView() {
        super.onInitView()
        initActionBar()
        initViewPager()
        initOnClick()
    }

    override fun onDestroy() {
        super.onDestroy()
        (museumActivity as RealMainActivity).enableScrollHideActionBar(true)
    }

    private fun initActionBar() {
        (museumActivity as RealMainActivity).apply {
            setBackIcon()
            enableScrollHideActionBar(false)
        }
    }

    private fun initViewPager() {
        fragmentList.add(allFragment)
        fragmentList.add(azFragment)
        pagerAdapter.addListFragment(fragmentList)

        binding.vpCollections.apply {
            setPagingEnabled(false)
            adapter = pagerAdapter
            offscreenPageLimit = pagerAdapter.count
            currentItem = 0
        }
    }
    
    private fun initOnClick() {
        binding.flCollectionsTabAll.setOnSafeClick {
            setTab(COLLECTION_TAB.ALL)
            binding.vpCollections.setCurrentItem(0, false)
        }

        binding.flCollectionsTabAZ.setOnSafeClick {
            setTab(COLLECTION_TAB.AZ)
            binding.vpCollections.setCurrentItem(1, false)
        }

        binding.flCollectionsTabMap.setOnSafeClick {
            setTab(COLLECTION_TAB.MAP)
            binding.vpCollections.setCurrentItem(2, false)
        }
    }

    private fun setTab(tab: COLLECTION_TAB) {
        when (tab) {
            COLLECTION_TAB.ALL -> selectTabAll()
            COLLECTION_TAB.AZ -> selectTabAZ()
            COLLECTION_TAB.MAP -> selectTabMap()
        }
    }

    private fun selectTabAll() {
        binding.vCollectionsTabAllEnable.show()
        binding.vCollectionsTabAZEnable.gone()
        binding.vCollectionsTabMapEnable.gone()
        binding.tvCollectionsTabAll.setTextColor(getAppColor(R.color.main_black))
        binding.tvCollectionsTabAZ.setTextColor(getAppColor(R.color.gray))
        binding.tvCollectionsTabMap.setTextColor(getAppColor(R.color.gray))
    }

    private fun selectTabAZ() {
        binding.vCollectionsTabAllEnable.gone()
        binding.vCollectionsTabAZEnable.show()
        binding.vCollectionsTabMapEnable.gone()
        binding.tvCollectionsTabAll.setTextColor(getAppColor(R.color.gray))
        binding.tvCollectionsTabAZ.setTextColor(getAppColor(R.color.main_black))
        binding.tvCollectionsTabMap.setTextColor(getAppColor(R.color.gray))
    }

    private fun selectTabMap() {
        binding.vCollectionsTabAllEnable.gone()
        binding.vCollectionsTabAZEnable.gone()
        binding.vCollectionsTabMapEnable.show()
        binding.tvCollectionsTabAll.setTextColor(getAppColor(R.color.gray))
        binding.tvCollectionsTabAZ.setTextColor(getAppColor(R.color.gray))
        binding.tvCollectionsTabMap.setTextColor(getAppColor(R.color.main_black))
    }
}
