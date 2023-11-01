package com.example.mobdev21_night_at_the_museum.presentation.story.storydetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.domain.model.StoryDetailModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class StoryDetail : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)
        viewPager2 = findViewById(R.id.story_detail_view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.story_detail_indicator)
        val storyDetails = intent.getSerializableExtra("storyDetails") as ArrayList<StoryDetailModel>
        val storyDetailAdapter = StoryDetailAdapter(storyDetails)

        viewPager2.adapter = storyDetailAdapter
        TabLayoutMediator(tabLayout, viewPager2) { _, _ ->

        }.attach()
    }
}