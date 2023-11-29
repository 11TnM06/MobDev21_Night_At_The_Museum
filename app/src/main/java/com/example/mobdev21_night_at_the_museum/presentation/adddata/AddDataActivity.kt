package com.example.mobdev21_night_at_the_museum.presentation.adddata

import androidx.activity.viewModels
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumActivity
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.databinding.AddDataActivityBinding
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.domain.model.ItemDetail

class AddDataActivity : MuseumActivity<AddDataActivityBinding>(R.layout.add_data_activity) {
    private val viewModel by viewModels<AddDataViewModel>()

    override fun onInitView() {
        super.onInitView()

        binding.btnAddData.setOnSafeClick {
//            viewModel.addData()
//
//            viewModel.addStreetViewList()
//
//            viewModel.addStoryList()

//            viewModel.removeSameNameSV()

            val item = Item().apply {
                name = "Big Ben"
                thumbnail = "https://lh3.googleusercontent.com/ci/AJFM8rz47R0Mz6EHAAD6BPR2KbZhvuHDw2uFG4XW0HGVN82cG_1gAguu5tT3Pf1jY9cxoC84-JCctq0=w168-c-h168-rw-v1"
                model3d = "https://culturalinstitute-3d-serving.storage.googleapis.com/98969182/DgGS2rgu8PoWqQ/1631639669853/gltf/model.glb"
                time = ""
                description = "3d model of Big Ben in London, United Kingdom. Big Ben is the nickname for the Great Bell of the striking clock at the north end of the Palace of Westminster, although the name is frequently extended to refer also to the clock and the clock tower. The official name of the tower in which Big Ben is located was originally the Clock Tower, but it was renamed Elizabeth Tower in 2012, to mark the Diamond Jubilee of Elizabeth II, Queen of the United Kingdom. (Wikipedia)"
                creatorName = ""
                collectionId = "EVV871STQ2PUvIOvYAJk"
                details = listOf(
                    ItemDetail().apply { mapFrom("Title: Big Ben") },
                    ItemDetail().apply { mapFrom("Rights: © 2021 Google") },
                )
            }

            val a = ""

            val db = Firebase.firestore
            db.collection("items")
                .add(item)
                .addOnSuccessListener { documentReference ->
                    toast("DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    toast("Error adding document: $e")
                }
        }
    }
}
