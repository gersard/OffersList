package cl.gersard.offerslist.data.model

data class BasketData(val items: MutableList<BasketItem>)

data class BasketItem(val item: Offer, var qty: Int)
