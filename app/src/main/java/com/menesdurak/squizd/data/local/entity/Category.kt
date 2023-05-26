package com.menesdurak.squizd.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class Category (
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int,
    val categoryName: String
)