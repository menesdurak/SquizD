package com.menesdurak.squizd.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithWords (
    @Embedded
    val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryOwnerId"
    )
    val words: List<Word>
)