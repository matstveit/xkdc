package com.example.xkdcapp.screens.comiclist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.xkdcapp.comics.FetchComicListUseCase
import com.example.xkdcapp.screens.common.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicListViewModel @Inject constructor(
    private val fetchComicListUseCase: FetchComicListUseCase
) : BaseViewModel() {

    private val _comicListResult: MutableLiveData<FetchComicListUseCase.Result> = MutableLiveData()
    val comicListResult: LiveData<FetchComicListUseCase.Result> = _comicListResult

    fun fetchComicList() {
        viewModelScope.launch {
            _comicListResult.value = fetchComicListUseCase.fetchComicList()
        }
    }
}