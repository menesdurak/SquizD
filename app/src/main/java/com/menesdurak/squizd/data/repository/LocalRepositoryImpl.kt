package com.menesdurak.squizd.data.repository

import com.menesdurak.squizd.data.local.CategoryDao
import com.menesdurak.squizd.data.local.WordDao
import com.menesdurak.squizd.data.local.entity.Category
import com.menesdurak.squizd.data.local.entity.CategoryWithWords
import com.menesdurak.squizd.data.local.entity.Word
import com.menesdurak.squizd.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val wordDao: WordDao,
    private val categoryDao: CategoryDao,
) :
    LocalRepository {

    override suspend fun getAllWordsFromCategory(categoryId: Int): List<Word> {
        return wordDao.getAllWordsFromCategory(categoryId)
    }

    override suspend fun getWordWithId(wordId: Long): Word {
        return wordDao.getWordWithId(wordId)
    }

    override suspend fun addWord(word: Word) {
        wordDao.addWord(word)
    }

    override suspend fun updateWordWithId(wordId: Long, wordName: String, wordMeaning: String) {
        wordDao.updateWordWithId(wordId, wordName, wordMeaning)
    }

    override suspend fun deleteAllWordsFromCategory(categoryId: Int) {
        wordDao.deleteAllWordsFromCategory(categoryId)
    }

    override suspend fun addCategory(category: Category) {
        categoryDao.addCategory(category)
    }

    override suspend fun updateCategoryWithId(categoryName: String, categoryId: Int) {
        categoryDao.updateCategoryWithId(categoryName, categoryId)
    }

    override suspend fun deleteWordWithId(wordId: Long) {
        wordDao.deleteWordWithId(wordId)
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    override suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAllCategories()
    }

}