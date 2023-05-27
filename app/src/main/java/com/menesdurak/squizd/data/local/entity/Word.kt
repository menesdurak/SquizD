package com.menesdurak.squizd.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_table")
data class Word (
    val wordName: String,
    val wordMeaning: String,
    val categoryOwnerId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var wordId: Long = 0
}