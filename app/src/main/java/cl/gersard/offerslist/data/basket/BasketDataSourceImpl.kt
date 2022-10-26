package cl.gersard.offerslist.data.basket

import cl.gersard.offerslist.data.model.BasketData
import cl.gersard.offerslist.data.model.BasketItem
import cl.gersard.offerslist.data.model.Offer
import javax.inject.Inject

/**
 * Data Source of the basket information stored in memory
 */
class BasketDataSourceImpl @Inject constructor() : BasketDataSource {

    private val basketData = BasketData(mutableListOf())

    override fun getBasketData(): BasketData {
        return basketData
    }

    override fun addOfferToBasket(offer: Offer): BasketData {
        val index = basketData.items.indexOfFirst { it.item.id == offer.id }
        if (index != -1) {
            // The offer exists
            val basketItem = basketData.items[index]
            basketItem.qty += 1
            basketData.items[index] = basketItem
        } else {
            // The offer doesn't exist
            basketData.items.add(BasketItem(offer, 1))
        }
        return getBasketData()
    }

    override fun reduceOfferQuantity(offer: Offer): BasketData {
        basketData.items.forEachIndexed { index, basketItem ->
            if (basketItem.item.id == offer.id) {
                basketItem.qty -= 1
                basketData.items[index] = basketItem
                return@forEachIndexed
            }
        }
        return getBasketData()
    }

    override fun removeOfferFromBasket(offer: Offer): BasketData {
        basketData.items.removeIf { it.item.id == offer.id }
        return getBasketData()
    }

    override fun clearBasketData() {
        basketData.items.clear()
    }
}