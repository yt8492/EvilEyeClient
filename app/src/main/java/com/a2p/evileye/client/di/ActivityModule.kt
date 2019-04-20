package com.a2p.evileye.client.di

import com.a2p.evileye.client.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity
}