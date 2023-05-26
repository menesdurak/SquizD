package com.menesdurak.squizd.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_table")
data class Word (
    @PrimaryKey(autoGenerate = true)
    val wordId: Int,
    val wordName: String,
    val wordMeaning: String,
    val categoryOwnerId: Int
)