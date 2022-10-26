package cl.gersard.offerslist.data.offer

import cl.gersard.offerslist.data.model.Offer

interface OffersDataSource {

    suspend fun getOffers(): List<Offer>?

}