package com.menesdurak.squizd.di

import android.app.Application
import com.menesdurak.squizd.data.local.CategoryDao
import com.menesdurak.squizd.data.local.SquizdDatabase
import com.menesdurak.squizd.data.local.WordDao
import com.menesdurak.squizd.data.repository.LocalRepositoryImpl
import com.menesdurak.squizd.domain.repository.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun getSquizdDatabase(context: Application): SquizdDatabase {
        return SquizdDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun getWordDao(squizdDatabase: SquizdDatabase): WordDao {
        return squizdDatabase.getWordDao()
    }

    @Provides
    @Singleton
    fun getCategoryDao(squizdDatabase: SquizdDatabase): CategoryDao {
        return squizdDatabase.getCategoryDao()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(wordDao: WordDao, categoryDao: CategoryDao): LocalRepository {
        return LocalRepositoryImpl(wordDao, categoryDao)
    }
}