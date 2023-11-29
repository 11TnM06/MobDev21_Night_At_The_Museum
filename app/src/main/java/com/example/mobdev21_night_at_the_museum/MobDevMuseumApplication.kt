package com.example.mobdev21_night_at_the_museum

import com.example.mobdev21_night_at_the_museum.common.BaseApplication
import com.example.mobdev21_night_at_the_museum.common.extension.setApplication

class MobDevMuseumApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        setApplication(this)
    }
}
