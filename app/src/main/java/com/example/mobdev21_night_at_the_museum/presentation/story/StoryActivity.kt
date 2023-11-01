package com.example.mobdev21_night_at_the_museum.presentation.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.databinding.ActivityStoryBinding

class StoryActivity : AppCompatActivity() {
    var binding : ActivityStoryBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        binding= ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val storiesList = Constants.getStoryData()
        val storyAdapter = StoryAdaper(storiesList)
        binding?.storyList?.rvItemsList?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding?.storyList?.rvItemsList?.adapter = storyAdapter
    }
}