package cl.gersard.offerslist.core

import cl.gersard.offerslist.data.*
import cl.gersard.offerslist.data.basket.BasketDataSource
import cl.gersard.offerslist.data.basket.BasketDataSourceImpl
import cl.gersard.offerslist.data.offer.OffersDataSource
import cl.gersard.offerslist.data.offer.OffersDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(ApiConstants.URL_BASE)
        .build()

    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun providesOffersDataSource(offersDataSourceImpl: OffersDataSourceImpl) : OffersDataSource = offersDataSourceImpl

    @Provides
    fun providesBasketDataSource(basketDataSourceImpl: BasketDataSourceImpl) : BasketDataSource = basketDataSourceImpl

}