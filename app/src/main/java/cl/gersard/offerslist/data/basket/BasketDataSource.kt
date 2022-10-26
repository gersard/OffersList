package cl.gersard.offerslist.data.basket

import cl.gersard.offerslist.data.model.BasketData
import cl.gersard.offerslist.data.model.Offer

interface BasketDataSource {

    fun getBasketData(): BasketData
    fun addOfferToBasket(offer: Offer): BasketData
    fun reduceOfferQuantity(offer: Offer): BasketData
    fun removeOfferFromBasket(offer: Offer): BasketData
    fun clearBasketData()

}