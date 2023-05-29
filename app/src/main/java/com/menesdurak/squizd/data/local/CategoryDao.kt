package com.menesdurak.squizd.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.menesdurak.squizd.data.local.entity.Category
import com.menesdurak.squizd.data.local.entity.CategoryWithWords

@Dao
interface CategoryDao {

    @Insert
    suspend fun addCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("DELETE FROM categories_table WHERE categoryId = :categoryId")
    suspend fun deleteCategoryWithId(categoryId: Int)

    @Query("SELECT * FROM categories_table ORDER BY categoryId ASC")
    suspend fun getAllCategories() : List<Category>

    @Transaction
    @Query("SELECT * FROM categories_table")
    suspend fun getCategoryWithWords() : List<CategoryWithWords>
}