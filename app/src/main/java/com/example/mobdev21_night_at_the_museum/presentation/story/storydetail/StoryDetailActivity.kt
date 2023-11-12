package com.example.mobdev21_night_at_the_museum.presentation.story.storydetail

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.domain.model.StoryDetailModel
import com.example.mobdev21_night_at_the_museum.presentation.story.Constants
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList


class StoryDetailActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var heart : ImageView
    private lateinit var hiddenImage : ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var backButton : ImageView
    private lateinit var fStories : ArrayList<String>
    private lateinit var userDocRef : DocumentReference
    private var isLiked = false
    private var storyId = "17KxqSvqhxrGGqbqGm9A"

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        fetchDataFromFirestore()
        initData()
        initActionHandler()
    }

    private fun initView() {
        setContentView(R.layout.activity_story_detail)
        viewPager2 = findViewById(R.id.story_detail_view_pager)
        heart = findViewById(R.id.heart)
        hiddenImage = findViewById(R.id.hiddenImage)
        tabLayout = findViewById(R.id.story_detail_indicator)
        backButton = findViewById(R.id.backButton)

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initData() {
        val isDev : Boolean = intent.getIntExtra("storyDetails", 0) == 0
        val data : ArrayList<StoryDetailModel> = if (isDev) {
            Constants.getStoryData()
        } else {
            intent.extras?.getParcelableArrayList("storyDetails", StoryDetailModel::class.java) as ArrayList<StoryDetailModel>
        }
//        val storyDetails = intent.getSerializableExtra("storyDetails") as ArrayList<StoryDetailModel>
        val storyDetailAdapter = StoryDetailAdapter(data)

        viewPager2.adapter = storyDetailAdapter
        TabLayoutMediator(tabLayout, viewPager2) { _, _ ->
        }.attach()


    }

    private fun initActionHandler() {

        viewPager2.setOnClickListener(object : DoubleClickListener() {

            override fun onDoubleClick(v: View?) {
                heart.setImageResource(R.drawable.ic_like_filled)
                Toast.makeText(this@StoryDetailActivity, "Your answer is correct!" , Toast.LENGTH_SHORT ).show()
                isLiked = true
            }
        })

        heart.setOnClickListener {
            isLiked = if (isLiked) {
                heart.setImageResource(R.drawable.ic_like)
                removeFStory()
                false

            } else {
                heart.setImageResource(R.drawable.ic_like_filled)
                addFStory()
                true
            }
        }

        backButton.setOnClickListener{
            super.onBackPressed()
        }
    }

    private fun addFStory() {
        if (!fStories.contains(storyId)) {
            fStories.add(storyId)

            // Update the document with the modified array
            userDocRef.update("fstories", fStories)
                .addOnSuccessListener {
                    println("Item added to the array successfully")
                    Toast.makeText(
                        this, "Add Fav Story Success",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnFailureListener { exception ->
                    println("Error adding item to the array: $exception")
                }
        } else {
            println("Item already exists in the array")
        }
    }

    private fun removeFStory() {
        fStories.remove(storyId)

// Update the document with the modified array
        userDocRef.update("fstories", fStories)
            .addOnSuccessListener {
                println("Item removed from the array successfully")
                Toast.makeText(
                    this, "Remove Fav Story Success",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener { exception ->
                println("Error removing item from the array: $exception")
            }
    }
    private fun fetchDataFromFirestore() {
        if (!intent.getStringExtra("storyId").toString().isNullOrBlank()) {
            storyId = intent.getStringExtra("storyId").toString()
        }
        FirebaseApp.initializeApp(this)
        val db = FirebaseFirestore.getInstance()
        userDocRef = db.collection("users").document("fZx3P0RpgvFIKz6kSFsk")
        userDocRef.get().addOnSuccessListener { document ->
            if ( document != null) {
                fStories = (document.get("fstories") as? ArrayList<String>)!!
                //change like image if story id already listed in the list
                if (fStories.contains(storyId)) {
                    isLiked = true
                    heart.setImageResource(R.drawable.ic_like_filled)
                }
//                for (item in fStories) {
//                    // Do something with each item in the array
//                    Toast.makeText(
//                        this, item as String?,
//                        Toast.LENGTH_LONG
//                    ).show()
//                    println(item)
//                }
        }

    }}



    abstract class DoubleClickListener: View.OnClickListener {
        private var lastClickTime : Long = 0

        companion object {
            private const val DOUBLE_CLICK_TIME_DELTA = 300
        }

        override fun onClick(v: View?) {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                onDoubleClick(v)
            }
        }

        abstract fun onDoubleClick(v : View?)
    }
}