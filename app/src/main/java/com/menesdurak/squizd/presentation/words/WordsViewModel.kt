package com.menesdurak.squizd.presentation.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.menesdurak.squizd.common.Resource
import com.menesdurak.squizd.data.local.entity.Word
import com.menesdurak.squizd.domain.use_case.words.AddWordUseCase
import com.menesdurak.squizd.domain.use_case.words.DeleteAllWordsFromCategoryUseCase
import com.menesdurak.squizd.domain.use_case.words.GetAllWordsFromCategoryUseCase
import com.menesdurak.squizd.domain.use_case.words.UpdateWordWithIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(
    private val getAllWordsFromCategoryUseCase: GetAllWordsFromCategoryUseCase,
    private val addWordUseCase: AddWordUseCase,
    private val deleteAllWordsFromCategoryUseCase: DeleteAllWordsFromCategoryUseCase,
    private val updateWordWithIdUseCase: UpdateWordWithIdUseCase
) : ViewModel() {

    private val _wordsList = MutableLiveData<Resource<List<Word>>>(Resource.Loading)
    val wordsList: LiveData<Resource<List<Word>>> = _wordsList

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

    fun updateWordWithId(wordId: Long, wordName: String, wordMeaning: String) {
        viewModelScope.launch {
            updateWordWithIdUseCase(wordId, wordName, wordMeaning)
        }
    }

    fun deleteAllWordsFromCategory(categoryId: Int) {
        viewModelScope.launch {
            deleteAllWordsFromCategoryUseCase(categoryId)
        }
    }
}