package com.menesdurak.squizd.domain.repository

import com.menesdurak.squizd.data.local.entity.Category
import com.menesdurak.squizd.data.local.entity.CategoryWithWords
import com.menesdurak.squizd.data.local.entity.Word

interface LocalRepository {

    suspend fun getAllWords(): List<Word>

    suspend fun getAllWordsFromCategory(categoryId: Int): List<Word>

    suspend fun getWordWithId(wordId: Int) : Word

    suspend fun addWord(word: Word)

    suspend fun updateWord(word: Word)

    suspend fun updateWordWithId(wordId: Int, wordName: String, wordMeaning: String)

    suspend fun deleteWord(word: Word)

    suspend fun deleteAllWords()

    suspend fun deleteAllWordsFromCategory(categoryId: Int)

    suspend fun addCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    suspend fun deleteCategoryWithId(categoryId: Int)

    suspend fun getAllCategories() : List<Category>

    suspend fun getCategoryWithWords() : List<CategoryWithWords>
}