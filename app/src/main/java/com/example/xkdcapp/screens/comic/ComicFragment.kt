package com.example.xkdcapp.screens.comic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.xkdcapp.R
import com.example.xkdcapp.comics.FetchComicDetailUseCase
import com.example.xkdcapp.comics.FetchComicUseCase
import com.example.xkdcapp.screens.comic.ComicFragmentDirections.Companion.actionErrorDialogFragment
import com.example.xkdcapp.screens.common.fragments.BaseFragment
import com.example.xkdcapp.screens.common.imageloader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_comic.*
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class ComicFragment : BaseFragment() {

    @Inject lateinit var imageLoader: ImageLoader
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
}