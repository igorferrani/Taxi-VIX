package br.com.taxivix

import android.app.Application
import br.com.taxivix.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CustomApplication)
            modules(appModules)
        }
    }
}