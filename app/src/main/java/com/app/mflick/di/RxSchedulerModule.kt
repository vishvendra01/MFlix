package com.app.mflick.di

import com.app.mflick.utils.RxScheduler
import com.app.mflick.utils.RxScheudlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RxSchedulerModule {

    @Binds
    fun bindRxScheduler(rxSchedulerImpl: RxScheudlerImpl): RxScheduler
}