package com.example.mobdev21_night_at_the_museum.presentation.search

import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.gone
import com.example.mobdev21_night_at_the_museum.common.extension.show
import com.example.mobdev21_night_at_the_museum.databinding.SearchActivityBinding
import com.example.mobdev21_night_at_the_museum.presentation.RealMainActivity
import com.example.mobdev21_night_at_the_museum.presentation.collection.CollectionFragment
import com.example.mobdev21_night_at_the_museum.presentation.item.ItemFragment
import com.example.mobdev21_night_at_the_museum.presentation.story.StoryFragment
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class SearchFragment : MuseumFragment<SearchActivityBinding>(R.layout.search_activity) {
    private val viewModel by viewModels<SearchViewModel>()
    private val searchKeyAdapter by lazy { SearchKeyAdapter() }
    private val searchAdapter by lazy { SearchAdapter() }
    private var oldEnable = false

    override fun onInitView() {
        super.onInitView()
        initOnClick()
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        if (museumActivity is RealMainActivity) {
            realMainActivity.setBackIcon()
            oldEnable = realMainActivity.enableScrollHideActionBar(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (oldEnable && museumActivity is RealMainActivity) {
            realMainActivity.enableScrollHideActionBar(true)
        }
    }

    private fun initOnClick() {
        if (museumActivity is RealMainActivity) {
            realMainActivity.getEditText().doAfterTextChanged {
                if (it.toString().isNotEmpty()) {
                    viewModel.getSuggestText(it.toString()) {
                        binding.cvSearchKey.show()
                        searchKeyAdapter.submitList(viewModel.suggestTexts)
                    }
                } else {
                    binding.cvSearchKey.gone()
                }
            }
        }
    }

    private fun initRecyclerView() {
        searchAdapter.listener = object : SearchAdapter.IListener {
            override fun onItemClick(itemId: String?) {
                addFragmentNew(ItemFragment(),
                    bundleOf(ItemFragment.ITEM_ID_KEY to itemId),
                    containerId = R.id.flRealMainContainer
                )
            }

            override fun onCollectionClick(collectionId: String?) {
                addFragmentNew(
                    CollectionFragment(),
                    bundleOf(CollectionFragment.COLLECTION_ID_KEY to collectionId),
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
        }

        binding.cvSearch.apply {
            setAdapter(this@SearchFragment.searchAdapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }

        searchKeyAdapter.listener = object : SearchKeyAdapter.IListener {
            override fun onSearchKeyClick(text: String) {
                museumActivity.hideKeyboard()
                binding.cvSearchKey.gone()
                viewModel.getSearchData(text) {
                    binding.cvSearch.submitList(viewModel.searchData)
                }
            }
        }

        binding.cvSearchKey.apply {
            setAdapter(this@SearchFragment.searchKeyAdapter)
            setLayoutManager(COLLECTION_MODE.VERTICAL)
        }
    }
}
