package com.cartrackers.app.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.cartrackers.app.utils.shared_pref
import org.koin.dsl.module

val storageModule = module(override = true) {
    single { providesSharedPreferences(get()) }
    single { providesSharedPrefStored(context = get(), storageName = get(), value = get()) }
    single { providesSharedUserCount(context = get(), storageName = get(), value = get()) }
    single { providesSharedPrefGetStorage(context = get(), storageName = get()) }
    single { providesSharedPrefGetCount(context = get(), storageName = get()) }
}

fun providesSharedPreferences(application: Application): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
}

fun providesSharedPrefStored(context: Context, storageName: String, value: Boolean) {
    val pref = context.getSharedPreferences(shared_pref,
        AppCompatActivity.MODE_PRIVATE
    )
    val editor = pref?.edit()
    editor?.putBoolean(storageName, value)
    editor?.apply()
}

fun providesSharedUserCount(context: Context, storageName: String, value: Int) {
    val pref = context.getSharedPreferences(shared_pref,
        AppCompatActivity.MODE_PRIVATE
    )
    val editor = pref?.edit()
    editor?.putInt(storageName, value)
    editor?.apply()
}

fun providesSharedPrefGetStorage(context: Context, storageName: String): Boolean? {
    val pref = context.getSharedPreferences(shared_pref,
        AppCompatActivity.MODE_PRIVATE
    )
    return pref?.getBoolean(storageName, false)
}

fun providesSharedPrefGetCount(context: Context, storageName: String): Int? {
    val pref = context.getSharedPreferences(shared_pref,
        AppCompatActivity.MODE_PRIVATE
    )
    return pref?.getInt(storageName, 0)
}