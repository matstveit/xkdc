package com.example.xkdcapp.screens.comiclist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.xkdcapp.R
import com.example.xkdcapp.comics.Comic
import com.example.xkdcapp.databinding.ComicListItemBinding
import com.example.xkdcapp.screens.comiclist.ComicListFragmentDirections.Companion.actionComicFragment
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Provider

@FragmentScoped
class ComicListAdapter @Inject constructor(
    private val inflater: Provider<LayoutInflater>
) : RecyclerView.Adapter<ComicListAdapter.ComicViewHolder>() {

    private var comicDetailList: List<Comic> = ArrayList(0)

    fun bindData(comic: List<Comic>?) {
        comic?.let {
            comicDetailList = ArrayList(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view = inflate<ComicListItemBinding>(
            inflater.get(),
            R.layout.comic_list_item,
            parent,
            false
        )
        return ComicViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        with(holder) {
            val id = comicDetailList[position].id
            val name = comicDetailList[position].name
            view.id.text = id
            view.name.text = name
            view.date.text = comicDetailList[position].date
            view.container.setOnClickListener {
                it.findNavController().navigate(actionComicFragment(id, name))
            }
        }
    }

    override fun getItemCount(): Int = comicDetailList.size

    inner class ComicViewHolder(val view: ComicListItemBinding) : RecyclerView.ViewHolder(view.root)
}

