package com.example.mobdev21_night_at_the_museum.presentation.collection

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.common.extension.toastUndeveloped
import com.example.mobdev21_night_at_the_museum.databinding.CollectionFragmentBinding
import com.example.mobdev21_night_at_the_museum.domain.model.Story
import com.example.mobdev21_night_at_the_museum.presentation.RealMainActivity
import com.example.mobdev21_night_at_the_museum.presentation.favorite.collection.FavoriteCollectionsFragment
import com.example.mobdev21_night_at_the_museum.presentation.item.ItemFragment
import com.example.mobdev21_night_at_the_museum.presentation.story.StoryFragment
import com.example.mobdev21_night_at_the_museum.presentation.storylist.StoryListFragment
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class CollectionFragment : MuseumFragment<CollectionFragmentBinding>(R.layout.collection_fragment) {
    companion object {
        const val COLLECTION_ID_KEY = "COLLECTION_ID_KEY"
    }

    private val viewModel by viewModels<CollectionViewModel>()
    private val adapter: CollectionAdapter by lazy { CollectionAdapter() }

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        viewModel.collectionId = arguments?.getString(COLLECTION_ID_KEY)
    }

    override fun onInitView() {
        super.onInitView()
        (museumActivity as RealMainActivity).apply {
            setBackIcon()
        }
        initOnClick()
        initRecyclerView()
        getData()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (museumActivity.supportFragmentManager.fragments.lastOrNull() is FavoriteCollectionsFragment) {
            (museumActivity as? RealMainActivity)?.enableFragmentContainerScrollingBehavior()
        }

        (museumActivity as RealMainActivity).apply {
            setWhiteActionBar()
        }
    }

    private fun initOnClick() {

    }

    private fun initRecyclerView() {
        adapter.listener = object : CollectionAdapter.IListener {
            override fun onItemClick(itemId: String?) {
                addFragmentNew(
                    ItemFragment(),
                    bundleOf(ItemFragment.ITEM_ID_KEY to itemId),
                    containerId = R.id.flRealMainContainer
                )
            }

            override fun onStoryClick(storyId: String?) {
                addFragmentNew(
                    StoryFragment(),
                    bundleOf(StoryFragment.STORY_ID_KEY to storyId),
                    containerId = R.id.flRealMainContainer
                )
            }

            override fun onFollowClick() {
                viewModel.follow(
                    onSuccessAction = {
                        binding.cvCollection.submitList(viewModel.list)
                        (museumActivity as RealMainActivity).reloadFavorite()
                    },
                    onFailureAction = { toast("Fail: $it") }
                )
            }

            override fun onUnFollowClick() {
                viewModel.unFollow(
                    onSuccessAction = {
                        binding.cvCollection.submitList(viewModel.list)
                        (museumActivity as RealMainActivity).reloadFavorite()
                    },
                    onFailureAction = { toast("Fail: $it") }
                )
            }

            override fun onShareClick() {
                toastUndeveloped()
            }

            override fun onViewAllStories(stories: List<Story>?) {
                addFragmentNew(
                    StoryListFragment(),
                    bundleOf(StoryListFragment.STORIES_KEY to stories),
                    containerId = R.id.flRealMainContainer
                )
            }
        }
        binding.cvCollection.apply {
            setAdapter(this@CollectionFragment.adapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }
    }

    private fun getData() {
        viewModel.getCollectionData(
            onSuccessAction = {
                binding.cvCollection.submitList(viewModel.list)
            },
            onFailureAction = {
                toast("Fail: $it")
            }
        )
    }
}
