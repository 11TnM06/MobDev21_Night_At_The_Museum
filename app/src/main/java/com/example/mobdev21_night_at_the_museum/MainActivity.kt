package com.example.mobdev21_night_at_the_museum

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobdev21_night_at_the_museum.databinding.ActivityMainBinding
import com.example.mobdev21_night_at_the_museum.presentation.story.Constants
import com.example.mobdev21_night_at_the_museum.presentation.story.StoryAdaper


class MainActivity : AppCompatActivity() {

    var binding : ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val storiesList = Constants.getStoryData()
        val storyAdapter = StoryAdaper(storiesList)
        binding?.rvItemsList?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding?.rvItemsList?.adapter = storyAdapter


    }

    fun ShowStories(view: View?) {
        // on below line we are opening a new activity using intent.


    }
}