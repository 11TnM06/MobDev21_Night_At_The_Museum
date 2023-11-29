package com.example.mobdev21_night_at_the_museum.presentation.camera.view3d.control

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.getAppString
import com.example.mobdev21_night_at_the_museum.common.extension.toast
import com.example.mobdev21_night_at_the_museum.databinding.View3dControllerFragmentBinding
import com.example.mobdev21_night_at_the_museum.presentation.camera.view3d.DOWNLOAD_STATUS
import com.example.mobdev21_night_at_the_museum.presentation.camera.view3d.RealMainActivity2
import com.example.mobdev21_night_at_the_museum.presentation.camera.view3d.View3dViewModel
import com.example.mobdev21_night_at_the_museum.presentation.dialog.LoadingDialog
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class View3dControllerFragment : MuseumFragment<View3dControllerFragmentBinding>(R.layout.view_3d_controller_fragment) {
    private val adapter by lazy { View3dControllerAdapter() }
    private val viewModel by activityViewModels<View3dViewModel>()
    private val downloadReceiver = DownloadReceiver()
    private val dialog by lazy {
        LoadingDialog().apply {
            title = getAppString(R.string.please_wait_for_render_model)
            isEnableDismiss = true
        }
    }

    override fun onInitView() {
        super.onInitView()
        initRecyclerView()
        getListItem()
    }

    override fun onDestroy() {
        requireContext().unregisterReceiver(downloadReceiver)
        viewModel.downloadMap.keys.forEach {
            viewModel.downloadManager.remove(it)
        }
        museumActivity.finish()
        super.onDestroy()
    }

    fun getListItem() {
        viewModel.getListItem(
            onSuccessAction = {
                binding.cvView3d.submitList(viewModel.listItemDisplay)
                if (viewModel.selectItemId != null) {
                    onItemClick(viewModel.selectItemId)
                }
                requireContext().registerReceiver(downloadReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
            }, onFailAction = {
                toast("Fail: $it")
            }
        )
    }

    private fun initRecyclerView() {
        adapter.listener = object : View3dControllerAdapter.IListener {
            override fun onItemClick(itemDisplay: View3dControllerAdapter.ItemDisplay) {
                onItemClick(itemDisplay.item.key)
            }

            override fun onItemLongClick(itemDisplay: View3dControllerAdapter.ItemDisplay) {
                navigateTo(RealMainActivity2::class.java, bundleOf(RealMainActivity2.ITEM_ID_KEY to itemDisplay.item.key))
            }
        }
        binding.cvView3d.apply {
            setAdapter(this@View3dControllerFragment.adapter)
            setLayoutManager(COLLECTION_MODE.HORIZONTAL)
        }
    }

    inner class DownloadReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (downloadId != -1L) {
                    val itemKey = viewModel.downloadMap[downloadId]
                    Log.d(TAG, "suck: Tải mô hình $itemKey thành công")
                    viewModel.copyFileFromDownloadToInternalStorage(itemKey!!, onSuccessAction = {
                        if (itemKey == viewModel.selectItemId) {
                            viewModel.buildModel(itemKey,
                                onStartAction = { showLoading() },
                                onSuccessAction = { hideLoading() },
                                onFailAction = { toast("Fail: $it") }
                            )
                        }
                        viewModel.updateItemDownloadState(itemKey, DOWNLOAD_STATUS.DOWNLOADED, onSuccessAction = {
                            binding.cvView3d.submitList(viewModel.listItemDisplay)
                        })
                    })
                }
            }
        }
    }

    private fun onItemClick(itemId: String?) {
        viewModel.updateSelectedItem(itemId,
            onSuccessAction = {
                binding.cvView3d.submitList(viewModel.listItemDisplay)
            }, onBuildStart = {
                showLoading()
            }, onBuildEnd = {
                hideLoading()
            }
        )
    }

    private fun showLoading() {
        dialog.show(childFragmentManager, dialog::class.java.simpleName)
    }

    private fun hideLoading() {
        dialog.dismiss()
    }
}
