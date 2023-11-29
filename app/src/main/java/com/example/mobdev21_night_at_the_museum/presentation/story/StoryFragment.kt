package com.example.mobdev21_night_at_the_museum.presentation.story

import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.common.extension.toastUndeveloped
import com.example.mobdev21_night_at_the_museum.databinding.StoryFragmentBinding
import com.example.mobdev21_night_at_the_museum.domain.model.Page
import com.example.mobdev21_night_at_the_museum.domain.model.Story
import com.example.mobdev21_night_at_the_museum.domain.usecase.updateLikeStory
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class StoryFragment : MuseumFragment<StoryFragmentBinding>(R.layout.story_fragment) {
    companion object {
        const val STORY_ID_KEY = "story_id_key"
    }

    private val adapter by lazy { StoryAdapter() }
    private val lineAdapter by lazy { StoryLineAdapter() }
    private var storyId: String? = null
    private var story: Story? = null

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        storyId = arguments?.getString(STORY_ID_KEY)
    }

    override fun onResume() {
        super.onResume()
        realMainActivity.apply {
            setBackIcon()
            expandAppBar()
        }
    }

    override fun onInitView() {
        super.onInitView()

        if (storyId == null) {
            toast("Story id is null")
            return
        }

        adapter.listener = object : StoryAdapter.IListener {
            override fun onRightClick() {
                binding.vpStory.currentItem++
            }

            override fun onLeftClick() {
                binding.vpStory.currentItem--
            }
        }

        binding.vpStory.apply {
            adapter = this@StoryFragment.adapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    updateLineList()
                }
            })
        }

        binding.cvStoryLine.apply {
            setAdapter(this@StoryFragment.lineAdapter)
            setLayoutManager(COLLECTION_MODE.HORIZONTAL)
        }

        val storyRef = Firebase.firestore.collection("stories").document(storyId!!)
        storyRef.get().addOnSuccessListener {
            story = it.toObject(Story::class.java)?.apply { key = it.id }
            story?.pages?.let { pages ->
                submitData(pages)
            }
        }

        binding.ivStoryLike.setOnSafeClick {
            updateLikeStory(story?.safeIsLiked() ?: false, storyId, onSuccessAction = {
                story?.mapIsLiked()
                updateLikeStatus()
            }, onFailureAction = {
                toast("Update like status failed")
            })
        }

        binding.ivStoryShare.setOnSafeClick { toastUndeveloped() }
    }

    private var lines: List<StoryLineAdapter.StoryLineDisplay>? = null

    private fun submitData(pages: List<Page>) {
        val pageDisplays: MutableList<StoryAdapter.PageDisplay> = mutableListOf(
            StoryAdapter.PageDisplay(title = story?.title, desc = story?.description, thumbnail = story?.thumbnail)
        )

        pageDisplays.addAll(pages.map {
            StoryAdapter.PageDisplay(thumbnail = it.thumbnail, desc =  it.description)
        })

        adapter.submitList(pageDisplays)

        if (lines == null) {
            lines = pages.map {
                val screenWidth = resources.displayMetrics.widthPixels
                StoryLineAdapter.StoryLineDisplay(width = screenWidth.toFloat() / pages.size)
            }.apply { firstOrNull()?.isWhite = true }
        }

        binding.cvStoryLine.submitList(lines)
    }

    private fun updateLineList() {
        for (i in lines!!.indices) {
            lines!![i].isWhite = i <= binding.vpStory.currentItem
        }
        binding.cvStoryLine.submitList(lines)
    }

    private fun updateLikeStatus() {
        if (story?.safeIsLiked() == true) {
            binding.ivStoryLike.setImageResource(R.drawable.ic_like_filled)
        } else {
            binding.ivStoryLike.setImageResource(R.drawable.ic_like)
        }
    }
}
