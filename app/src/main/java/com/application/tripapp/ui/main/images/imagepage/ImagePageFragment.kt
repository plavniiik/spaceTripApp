package com.application.tripapp.ui.main.images.imagepage

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.application.tripapp.R
import com.application.tripapp.databinding.FragmentImageBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ImagePageFragment : Fragment() {
    private var binding: FragmentImageBinding? = null
    private val viewModel: ImagePageViewModel by viewModels()

    companion object {
        private const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(layoutInflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("keyWord")
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is ImagePageState.PictureLoaded -> {
                            binding?.loginTitle?.text = state.picture?.title.toString()
                            binding?.picture.let {
                                if (it != null) {
                                    binding?.root?.let { it1 ->
                                        Glide.with(it1.context)
                                            .load(state.picture?.link)
                                            .error(
                                                Glide.with(it1.context)
                                                    .load("https://hightech.fm/wp-content/uploads/2023/02/8888889.jpg")
                                            )
                                            .into(it)
                                    }
                                }
                            }
                            binding?.toHome?.setOnClickListener {
                                findNavController().navigate(R.id.action_imagePageFragment_to_mainFragment)
                            }
                            binding?.shared?.setOnClickListener {
                                val id = arguments?.getString("keyWord")
                                val shareIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "https://tripspacenasa.com/image/$id")
                                    type = "text/plain"
                                }
                                startActivity(Intent.createChooser(shareIntent, resources.getText(R.string.send_to)))
                            }
                            binding?.descriptionText?.text=state.picture?.description
                            binding?.dwnl?.setOnClickListener {
                                if (ContextCompat.checkSelfPermission(
                                        requireContext(),
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    ) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    requestPermissions(
                                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE
                                    )
                                } else {
                                    startDownload(state.picture?.link)
                                }
                            }
                        }

                        is ImagePageState.PictureError -> {
                            Toast.makeText(requireContext(), state.str, Toast.LENGTH_LONG)
                                .show()
                        }

                        else -> {

                        }
                    }
                }
            }
        }

        id?.let { viewModel.processAction(ImagePageAction.LoadPictures(id)) }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } else {

                }
                return
            }

        }
    }

    private fun startDownload(url: String?) {
        val fileExtension = "jpg"
        val fileName = "${System.currentTimeMillis()}.$fileExtension"

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(getString(R.string.download))
            .setDescription(getString(R.string.downloading))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val dm = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = dm.enqueue(request)

        val onComplete = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) == downloadId) {
                    val query = DownloadManager.Query().setFilterById(downloadId)
                    val cursor = dm.query(query)
                    if (cursor.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                        val uriString = cursor.getString(columnIndex)
                        val uri = Uri.parse(uriString)

                        context?.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
                    }
                }
            }
        }

        context?.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }
}
