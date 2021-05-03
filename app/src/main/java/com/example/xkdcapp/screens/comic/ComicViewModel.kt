package com.example.xkdcapp.screens.comic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.xkdcapp.comics.FetchComicDetailUseCase
import com.example.xkdcapp.comics.FetchComicUseCase
import com.example.xkdcapp.screens.common.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ComicViewModel @Inject constructor(
    private val fetchComicUseCase: FetchComicUseCase,
    private val fetchComicDetailUseCase: FetchComicDetailUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _comicResult: MutableLiveData<FetchComicUseCase.Result> =
        savedStateHandle.getLiveData("comic")
    private val _comicDetailResult: MutableLiveData<FetchComicDetailUseCase.Result> =
        savedStateHandle.getLiveData("comicDetail")

    val comicResult: LiveData<FetchComicUseCase.Result> = _comicResult
    val comicDetailResult: LiveData<FetchComicDetailUseCase.Result> = _comicDetailResult

    fun fetchComic(imageId: String) {
        viewModelScope.launch {
            _comicResult.value  = fetchComicUseCase.fetchComic(imageId)
        }
    }

    fun fetchComicDetail(comicId: String, comicName: String) {
        viewModelScope.launch {
            _comicDetailResult.value = fetchComicDetailUseCase.fetchComicDetail(comicId, comicName)
        }
    }
}