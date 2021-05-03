package com.example.xkdcapp.screens.comiclist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xkdcapp.R
import com.example.xkdcapp.screens.common.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_comic_list.*
import javax.inject.Inject
import javax.inject.Provider


@AndroidEntryPoint
class ComicListFragment : BaseFragment() {

    @Inject
    lateinit var comicAdapter: ComicListAdapter
    @Inject lateinit var linearLayoutManager: Provider<LinearLayoutManager>
    private val viewModel: ComicListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comic_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpRecyclerView()
        fetchComic()
    }

    private fun setupObserver(){
       //TODO: viewModel.observe
       // hideProgressIndication()
    }

    private fun fetchComic() {
       //TODO: viewModel.fetchComicList()
       // showProgressIndication()
    }

    private fun setUpListeners() {
        swipeRefresh.setOnRefreshListener { fetchComic() }
    }

    private fun setUpRecyclerView() {
        recycler.layoutManager = linearLayoutManager.get()
        recycler.adapter = comicAdapter
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