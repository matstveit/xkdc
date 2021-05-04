package com.example.xkdcapp.screens.comic

import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.xkdcapp.R
import com.example.xkdcapp.comics.FetchComicDetailUseCase
import com.example.xkdcapp.comics.FetchComicUseCase
import com.example.xkdcapp.screens.comic.ComicFragmentDirections.Companion.actionErrorDialogFragment
import com.example.xkdcapp.screens.common.fragments.BaseFragment
import com.example.xkdcapp.screens.common.imageloader.ImageLoader
import com.example.xkdcapp.utils.FileUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_comic.*
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class ComicFragment : BaseFragment() {

    @Inject lateinit var imageLoader: ImageLoader
    @Inject lateinit var fileUtils: FileUtils
    private val viewModel: ComicViewModel by viewModels()
    private val args by navArgs<ComicFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comic, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image.setOnClickListener {
            shareImage()
        }
        swipeRefresh.setOnRefreshListener {
            fetchComic(args.comicId)
        }
        fetchComic(args.comicId)
    }

    private fun setupObserver() {
        viewModel.comicResult.observe(this, { result ->
            if (result is FetchComicUseCase.Result.Success) {
                title.text = result.data.title
                message.text = result.data.message
                image.contentDescription = result.data.description
                imageLoader.loadImage(result.data.imageUrl, image)

                fetchComicDetail(args.comicId, args.comicName)
            } else {
                hideProgressIndication()
                findNavController().navigate(actionErrorDialogFragment())
            }
        })

        viewModel.comicDetailResult.observe(this, { result ->
            if (result is FetchComicDetailUseCase.Result.Success) {
                result.data?.let {
                    explanationTitle.visibility = View.VISIBLE
                    explanation.text = it
                } ?: Timber.e("comicDetailResult is empty")
            } else {
                findNavController().navigate(actionErrorDialogFragment())
            }
            hideProgressIndication()
        })
    }

    private fun fetchComicDetail(comicId: String?, comicName: String?) {
        if (comicId != null && comicName != null) {
            viewModel.fetchComicDetail(comicId, comicName)
        }
    }

    private fun fetchComic(comicId: String?) {
        comicId?.let {
            viewModel.fetchComic(it)
            showProgressIndication()
        }
    }

    private fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    private fun hideProgressIndication() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    private fun shareImage() {
        image.invalidate()
        image.drawable?.let {
            val uri = fileUtils.storeBitmapAndGetUri(getBitmapFromImageView())
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
            }
            val chooser = Intent.createChooser(intent, getString(R.string.share))

            val resInfoList: List<ResolveInfo>? =
                context?.packageManager?.queryIntentActivities(
                    chooser,
                    PackageManager.MATCH_DEFAULT_ONLY
                )

            resInfoList?.forEach {
                context?.grantUriPermission(
                    it.activityInfo.packageName,
                    uri,
                    FLAG_GRANT_WRITE_URI_PERMISSION or FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            startActivity(chooser)
        } ?: Timber.e("Image Drawable is null")
    }

    private fun getBitmapFromImageView(): Bitmap {
        val bitmap = image.drawable.toBitmap(1280, 720, Bitmap.Config.ARGB_8888)
        return fileUtils.getCompressedJpegAsBitmap(bitmap, 50)
    }
}