package deco.koindemo

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class KoinDemoApplication : Application(){
    override fun onCreate(){
        super.onCreate()

        startKoin {
            // Android context
            androidContext(this@KoinDemoApplication)
            // modules
            modules(applicationModule)

        }
    }
}