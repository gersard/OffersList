package cl.gersard.offerslist.data

import cl.gersard.offerslist.data.model.OffersListResponse
import retrofit2.http.GET

interface ApiService {

    @GET(ApiConstants.GET_OFFERS_LIST)
    suspend fun getOffersList() : OffersListResponse

}