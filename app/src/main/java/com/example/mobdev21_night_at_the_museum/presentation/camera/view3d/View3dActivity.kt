package com.example.mobdev21_night_at_the_museum.presentation.camera.view3d

import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumActivity
import com.example.mobdev21_night_at_the_museum.common.extension.gone
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.extension.show
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.databinding.View3dActivityBinding
import com.example.mobdev21_night_at_the_museum.presentation.camera.view3d.control.View3dControllerFragment
import com.example.mobdev21_night_at_the_museum.presentation.download.DownloadActivity


class View3dActivity : MuseumActivity<View3dActivityBinding>(R.layout.view_3d_activity) {
    companion object {
        const val ITEM_ID_KEY = "ITEM_ID_KEY"
    }

    private val viewModel by viewModels<View3dViewModel>()
    private var arFragment: ArFragment? = null
    private val view3dControllerFragment by lazy { View3dControllerFragment() }

    override fun getContainerId() = R.id.flView3DFragmentContainer

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        viewModel.selectItemId = intent.getStringExtra(ITEM_ID_KEY)
    }

    override fun onInitView() {
        super.onInitView()
        initArFragment()
        initOnClick()
        addFragment(view3dControllerFragment)
    }

    private fun initArFragment() {
        arFragment = supportFragmentManager.findFragmentById(R.id.fView3dArFragment) as? ArFragment
        arFragment?.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent? ->
            binding.tvView3dControllerTutorial.gone()
            if (viewModel.modelRenderable != null) {
                val anchorNode = AnchorNode(hitResult.createAnchor())
                anchorNode.setParent(arFragment!!.arSceneView.scene)
                val transformableNode = TransformableNode(arFragment!!.transformationSystem)
                transformableNode.setParent(anchorNode)
                transformableNode.renderable = viewModel.modelRenderable
                transformableNode.select()
            } else {
                toast("Vui lòng chọn 1 mô hình")
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        view3dControllerFragment.getListItem()
    }

    private fun initOnClick() {
        binding.ivView3dControllerBack.setOnSafeClick {
            finish()
        }
        binding.ivView3dControllerMore.setOnSafeClick {
            val newBinding = binding.layoutView3dOption
            newBinding.apply {
                root.show()
                root.setOnSafeClick {
                    root.gone()
                }
                tvView3dOptionDownload.setOnSafeClick {
                    navigateTo(DownloadActivity::class.java)
                }
                tvView3dOptionModelInfo.setOnSafeClick {
                    if (viewModel.selectItemId != null) {
                        navigateTo(RealMainActivity2::class.java, bundleOf(RealMainActivity2.ITEM_ID_KEY to viewModel.selectItemId))
                    } else {
                        toast("Vui lòng chọn 1 mô hình")
                    }
                }
            }
        }
        binding.ivView3dControllerReload.setOnSafeClick {
            if (dontHaveChild()) {
                toast("Không có mô hình nào để xóa")
            } else {
                val dialog = ConfirmDeleteDialog2().apply {
                    onConfirmAction = {
                        reload()
                        dismiss()
                    }
                }
                dialog.show(supportFragmentManager, dialog::class.java.simpleName)
            }
        }
    }

    private fun reload() {
        if (arFragment == null) {
            toast("Error: arFragment is null")
            return
        }
        val children = arFragment!!.arSceneView.scene.children
        for (node in children) {
            if (node is AnchorNode) {
                if (node.anchor != null) {
                    node.anchor!!.detach()
                }
            }
        }
    }

    private fun dontHaveChild(): Boolean {
        if (arFragment == null) {
            toast("Error: arFragment is null")
            return false
        }

        var count = 0
        val children = arFragment!!.arSceneView.scene.children
        for (node in children) {
            if (node is AnchorNode) {
                if (node.anchor != null) {
                    count++
                }
            }
        }
        return count == 0
    }
}
