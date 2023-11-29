package com.example.mobdev21_night_at_the_museum.presentation.ar

import android.net.Uri
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.BaseBindingActivity
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.databinding.ArActivityBinding

class ArActivity : BaseBindingActivity<ArActivityBinding>(R.layout.ar_activity) {
    private val viewModel by viewModels<ArViewModel>()

    override fun onInitView() {
        super.onInitView()
        initArFragment()
        viewModel.onGet3DModelLoading = {
            binding.cpIArLoading.show()
        }

        viewModel.onGet3DModelFailure = {
            binding.cpIArLoading.hide()
            toast("Tải mô hình ${viewModel.modelName} thất bại")
        }

        viewModel.onGet3DModelSuccess = {
            binding.cpIArLoading.hide()
            toast("Đã tải về thành công mô hình: ${viewModel.modelName}")
            buildModel()
        }
        viewModel.modelName = "table.glb"
        viewModel.get3dModel()
    }

    override fun onObserverViewModel() {
        super.onObserverViewModel()
//        coroutinesLaunch(viewModel.get3dModelState) {
//            handleUiState(it, object : IViewListener {
//                override fun onLoading() {
//                    binding.cpIArLoading.show()
//                }
//
//                override fun onFailure() {
//                    binding.cpIArLoading.hide()
//                    toast("Tải mô hình ${viewModel.modelName} thất bại")
//                }
//
//                override fun onSuccess() {
//                    binding.cpIArLoading.hide()
//                    toast("Đã tải về thành công mô hình: ${viewModel.modelName}")
//                    buildModel()
//                }
//            })
//        }

    }

    private fun initArFragment() {
        val arFragment = fragmentManager?.findFragmentById(R.id.arFragment) as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent? ->
            val anchorNode = AnchorNode(hitResult.createAnchor())
            anchorNode.renderable = viewModel.renderable
            arFragment.arSceneView.scene.addChild(anchorNode)
        }
    }

    private fun buildModel() {
        if (viewModel.file == null) {
            toast("File is null")
            return
        }
        val renderableSource = RenderableSource
            .builder()
            .setSource(this, Uri.parse(viewModel.file!!.path), RenderableSource.SourceType.GLB)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()
        ModelRenderable
            .builder()
            .setSource(this, renderableSource)
            .setRegistryId(viewModel.file!!.path)
            .build()
            .thenAccept { modelRenderable: ModelRenderable? ->
                Toast.makeText(this, "Model built", Toast.LENGTH_SHORT).show()
                viewModel.renderable = modelRenderable
            }
    }
}
