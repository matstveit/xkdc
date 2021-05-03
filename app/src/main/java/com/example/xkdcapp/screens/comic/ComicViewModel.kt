package com.example.xkdcapp.screens.comic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.xkdcapp.comics.FetchComicUseCase
import com.example.xkdcapp.screens.common.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ComicViewModel @Inject constructor(
    private val fetchComicUseCase: FetchComicUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _comicResult: MutableLiveData<FetchComicUseCase.Result> =
        savedStateHandle.getLiveData("comic")

    val comicResult: LiveData<FetchComicUseCase.Result> = _comicResult

    fun fetchComic(imageId: String) {
        viewModelScope.launch {
            _comicResult.value  = fetchComicUseCase.fetchComic(imageId)
        }
    }
}