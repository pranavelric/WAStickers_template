package com.template.samplestickerapp.main.di

import com.template.samplestickerapp.main.Adapter.MyDataAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class MyAdapterModule {

    @Singleton
    @Provides
    fun provideMyDataAdapter(): MyDataAdapter {
        return MyDataAdapter()
    }


}