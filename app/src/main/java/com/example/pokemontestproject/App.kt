package com.example.pokemontestproject

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.example.pokemontestproject.di.AppComponent
import com.example.pokemontestproject.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}

fun Activity.getAppComponent(): AppComponent = (application as App).appComponent
fun Fragment.getAppComponent(): AppComponent = requireActivity().getAppComponent()