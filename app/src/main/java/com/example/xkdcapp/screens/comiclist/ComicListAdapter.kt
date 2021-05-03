package com.example.xkdcapp.screens.comiclist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.xkdcapp.R
import com.example.xkdcapp.databinding.ComicListItemBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Provider


@FragmentScoped
class ComicListAdapter @Inject constructor(
    private val inflater: Provider<LayoutInflater>
) : RecyclerView.Adapter<ComicListAdapter.ComicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view = DataBindingUtil.inflate<ComicListItemBinding>(
            inflater.get(),
            R.layout.comic_list_item,
            parent,
            false
        )
        return ComicViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
      //TODO: bind
    }

    override fun getItemCount(): Int = 0

    inner class ComicViewHolder(val view: ComicListItemBinding) : RecyclerView.ViewHolder(view.root)
}

