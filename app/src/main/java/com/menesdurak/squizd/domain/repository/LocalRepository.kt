package com.menesdurak.squizd.domain.repository

import com.menesdurak.squizd.data.local.entity.Category
import com.menesdurak.squizd.data.local.entity.CategoryWithWords
import com.menesdurak.squizd.data.local.entity.Word

interface LocalRepository {

    suspend fun getAllWordsFromCategory(categoryId: Int): List<Word>

    suspend fun getWordWithId(wordId: Long) : Word

    suspend fun addWord(word: Word)

    suspend fun updateWordWithId(wordId: Long, wordName: String, wordMeaning: String)

    suspend fun deleteAllWordsFromCategory(categoryId: Int)

    suspend fun addCategory(category: Category)

    suspend fun updateCategoryWithId(categoryName: String, categoryId: Int)

    suspend fun deleteWordWithId(wordId: Long)

    suspend fun deleteCategory(category: Category)

    suspend fun getAllCategories() : List<Category>
}