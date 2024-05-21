package com.mhmtn.satellites.di

import com.mhmtn.satellites.repo.SatellitesRepo
import com.mhmtn.satellites.service.SatelliteAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesSatellitesRepo(api:SatelliteAPI) = SatellitesRepo(api)

    @Provides
    @Singleton
    fun providesSatellitesAPI() = SatelliteAPI()

}