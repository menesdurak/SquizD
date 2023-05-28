package com.menesdurak.squizd.presentation.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.data.local.entity.Word
import com.menesdurak.squizd.domain.use_case.words.AddWordUseCase
import com.menesdurak.squizd.domain.use_case.words.GetAllWordsFromCategoryUseCase
import com.menesdurak.squizd.domain.use_case.words.GetAllWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(
    private val getAllWordsUseCase: GetAllWordsUseCase,
    private val getAllWordsFromCategoryUseCase: GetAllWordsFromCategoryUseCase,
    private val addWordUseCase: AddWordUseCase
) : ViewModel() {

    private val _wordsList = MutableLiveData<Resource<List<Word>>>(Resource.Loading)
    val wordsList: LiveData<Resource<List<Word>>> = _wordsList

    fun getAllWords() {
        viewModelScope.launch {
            _wordsList.value = Resource.Loading
            _wordsList.value = getAllWordsUseCase()!!
        }
    }

    fun getAllWordsFromCategory(categoryId: Int) {
        viewModelScope.launch {
            _wordsList.value = Resource.Loading
            _wordsList.value = getAllWordsFromCategoryUseCase(categoryId)!!
        }
    }

    fun addWord(word: Word) {
        viewModelScope.launch {
            addWordUseCase(word)
        }
    }
}