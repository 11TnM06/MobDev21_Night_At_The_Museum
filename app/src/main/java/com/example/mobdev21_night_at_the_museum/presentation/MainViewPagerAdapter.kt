package com.example.mobdev21_night_at_the_museum.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private var fragmentList = mutableListOf<MuseumFragment<*>>()

    override fun getCount() = fragmentList.size

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addListFragment(fragmentList: List<MuseumFragment<*>>) {
        this.fragmentList.clear()
        this.fragmentList.addAll(fragmentList)
    }
}
