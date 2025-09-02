package io.github.annachen368.simple_compose_multiplatform

import android.app.Application
import io.github.annachen368.simple_compose_multiplatform.di.initKoin

class CatApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}