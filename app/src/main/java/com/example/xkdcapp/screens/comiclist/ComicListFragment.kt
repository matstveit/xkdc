package com.example.xkdcapp.screens.comiclist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.xkdcapp.R
import com.example.xkdcapp.screens.common.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ComicListFragment : BaseFragment() {

    private val viewModel: ComicListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comic_list, container, false)
    }
}