package com.app.mflick.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface RxScheduler {

    fun io(): Scheduler

    fun ui(): Scheduler

    fun computation(): Scheduler
}

class RxScheudlerImpl @Inject constructor() : RxScheduler {

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

}