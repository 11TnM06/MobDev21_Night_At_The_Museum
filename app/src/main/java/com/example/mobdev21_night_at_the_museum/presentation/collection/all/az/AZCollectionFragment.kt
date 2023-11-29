package com.example.mobdev21_night_at_the_museum.presentation.collection.all.az

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.databinding.AzCollectionFragmentBinding
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.presentation.collection.CollectionFragment
import com.example.mobdev21_night_at_the_museum.presentation.collection.all.CollectionsAdapter
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class AZCollectionFragment : MuseumFragment<AzCollectionFragmentBinding>(R.layout.az_collection_fragment) {
    private val viewModel by viewModels<AzCollectionViewModel>()
    private val azAdapter by lazy { AZCollectionAdapter() }
    private val collectionAdapter by lazy { CollectionsAdapter() }

    override fun onInitView() {
        super.onInitView()
        initRecyclerViewLetter()
        initRecyclerViewCollection()
        viewModel.getCollectionStartWithLetter(
            viewModel.listLetterDisplay.first().letter,
            onSuccessAction = {
                binding.cvAzCollection.submitList(it)
            },
            onFailureAction = {
                toast(it)
            }
        )
    }

    private fun initRecyclerViewLetter() {
        azAdapter.listener = object : AZCollectionAdapter.IListener {
            override fun onLetterClick(letterDisplay: AZCollectionAdapter.LetterDisplay) {
                viewModel.getCollectionStartWithLetter(
                    letterDisplay.letter,
                    onSuccessAction = {
                        binding.cvAzCollection.submitList(it)
                    },
                    onFailureAction = {
                        toast(it)
                    }
                )
            }
        }

        binding.cvAzCollectionKey.apply {
            setAdapter(this@AZCollectionFragment.azAdapter)
            setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            submitList(viewModel.listLetterDisplay)
        }
    }

    private fun initRecyclerViewCollection() {
        collectionAdapter.listener = object : CollectionsAdapter.IListener {
            override fun onCollectionClick(collection: MCollection) {
                museumActivity.addFragmentNew(
                    CollectionFragment(),
                    bundleOf(CollectionFragment.COLLECTION_ID_KEY to collection.key),
                    containerId = R.id.flRealMainContainer
                )
            }
        }

        binding.cvAzCollection.apply {
            setAdapter(this@AZCollectionFragment.collectionAdapter)
            setLayoutManager(COLLECTION_MODE.GRID_VERTICAL)
            setMaxItemHorizontal(2)
        }
    }
}
