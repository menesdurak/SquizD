package com.menesdurak.squizd.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.menesdurak.squizd.data.local.entity.Word

@Dao
interface WordDao {

    @Query("SELECT * FROM words_table ORDER BY wordId ASC")
    suspend fun getAllWords(): List<Word>

    @Query("SELECT * FROM words_table WHERE categoryOwnerId = :categoryId ORDER BY wordId ASC")
    suspend fun getAllWordsFromCategory(categoryId: Int): List<Word>

    @Query("SELECT * FROM words_table WHERE wordId = :wordId")
    suspend fun getWordWithId(wordId: Int) : Word

    @Insert
    suspend fun addWord(word: Word)

    @Update
    suspend fun updateWord(word: Word)

    @Query("UPDATE words_table SET wordName = :wordName, wordMeaning = :wordMeaning WHERE wordId = :wordId")
    suspend fun updateWordWithId(wordId: Int, wordName: String, wordMeaning: String)

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("DELETE FROM words_table")
    suspend fun deleteAllWords()

    @Query("DELETE FROM words_table WHERE categoryOwnerId = :categoryId")
    suspend fun deleteAllWordsFromCategory(categoryId: Int)
}