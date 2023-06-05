package com.menesdurak.squizd.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.menesdurak.squizd.data.local.entity.Word

@Dao
interface WordDao {

    @Query("SELECT * FROM words_table WHERE categoryOwnerId = :categoryId ORDER BY wordId ASC")
    suspend fun getAllWordsFromCategory(categoryId: Int): List<Word>

    @Query("SELECT * FROM words_table WHERE wordId = :wordId")
    suspend fun getWordWithId(wordId: Long) : Word

    @Insert
    suspend fun addWord(word: Word)

    @Query("UPDATE words_table SET wordName = :wordName, wordMeaning = :wordMeaning WHERE wordId = :wordId")
    suspend fun updateWordWithId(wordId: Long, wordName: String, wordMeaning: String)

    @Query("DELETE FROM words_table WHERE wordId = :wordId")
    suspend fun deleteWordWithId(wordId: Long)

    @Query("DELETE FROM words_table WHERE categoryOwnerId = :categoryId")
    suspend fun deleteAllWordsFromCategory(categoryId: Int)
}