package com.example.mobdev21_night_at_the_museum.presentation

import android.net.Uri
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.BaseBindingFragment
import com.example.mobdev21_night_at_the_museum.databinding.MyArFragmentBinding
import java.io.File
import java.io.IOException

class MyArFragment : BaseBindingFragment<MyArFragmentBinding>(R.layout.my_ar_fragment) {
    private var renderable: ModelRenderable? = null

    override fun onInitView() {
        super.onInitView()

        FirebaseApp.initializeApp(requireContext())

        val storage = FirebaseStorage.getInstance()
        val modelRef = storage.reference.child("table.glb")

        val arFragment = fragmentManager?.findFragmentById(R.id.arFragment) as ArFragment

        binding.downloadBtn.setOnClickListener {
            try {
                val file = File.createTempFile("table", "glb")
                val task = modelRef.getFile(file)
                task.addOnSuccessListener { buildModel(file) }
                task.addOnFailureListener { e: Exception -> Toast.makeText(requireContext(), "Error: " + e.message, Toast.LENGTH_SHORT).show() }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error: " + e.message, Toast.LENGTH_SHORT).show()
            }
        }

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent? ->
            val anchorNode = AnchorNode(hitResult.createAnchor())
            anchorNode.renderable = renderable
            arFragment.arSceneView.scene.addChild(anchorNode)
        }
    }

    private fun buildModel(file: File) {
        val renderableSource = RenderableSource
            .builder()
            .setSource(requireContext(), Uri.parse(file.path), RenderableSource.SourceType.GLB)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()
        ModelRenderable
            .builder()
            .setSource(requireContext(), renderableSource)
            .setRegistryId(file.path)
            .build()
            .thenAccept { modelRenderable: ModelRenderable? ->
                Toast.makeText(requireContext(), "Model built", Toast.LENGTH_SHORT).show()
                renderable = modelRenderable
            }
    }
}
