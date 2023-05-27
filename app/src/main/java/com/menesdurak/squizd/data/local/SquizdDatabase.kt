package com.menesdurak.squizd.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.menesdurak.squizd.data.local.entity.Category
import com.menesdurak.squizd.data.local.entity.Word

@Database(entities = [Word::class, Category::class], version = 1)
abstract class SquizdDatabase: RoomDatabase() {

    abstract fun getWordDao(): WordDao
    abstract fun getCategoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: SquizdDatabase? = null

        fun getDatabase(context: Context): SquizdDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SquizdDatabase::class.java,
                    "words_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }

}