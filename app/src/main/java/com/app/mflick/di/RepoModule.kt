package com.app.mflick.di

import com.app.mflick.data.repo.FlickrRepository
import com.app.mflick.data.repo.FlickrRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepoModule {

    @Binds
    fun bindsFlickrRepository(repository: FlickrRepositoryImpl): FlickrRepository
}