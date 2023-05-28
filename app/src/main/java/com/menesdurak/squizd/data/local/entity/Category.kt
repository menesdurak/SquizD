package com.menesdurak.squizd.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class Category (
    val categoryName: String
) {
    @PrimaryKey(autoGenerate = true)
    var categoryId: Int = 0
}