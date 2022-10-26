package cl.gersard.offerslist.data.offer

import cl.gersard.offerslist.data.ApiService
import cl.gersard.offerslist.data.model.Offer
import java.lang.Exception
import javax.inject.Inject

class OffersDataSourceImpl @Inject constructor(private val apiService: ApiService) : OffersDataSource {

    override suspend fun getOffers(): List<Offer>? {
        return try {
            val response = apiService.getOffersList()
            response.items
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }
}