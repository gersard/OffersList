package cl.gersard.offerslist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gersard.offerslist.core.Event
import cl.gersard.offerslist.data.basket.BasketDataSource
import cl.gersard.offerslist.data.offer.OffersDataSource
import cl.gersard.offerslist.data.model.BasketData
import cl.gersard.offerslist.data.model.BasketItem
import cl.gersard.offerslist.data.model.Offer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
data class OffersViewModel @Inject constructor(
    private val offersDataSource: OffersDataSource,
    private val basketDataSource: BasketDataSource,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    private var _offersList = MutableLiveData<Event<List<Offer>>>()
    val offersList: LiveData<Event<List<Offer>>>
        get() = _offersList

    private var _basketData = MutableLiveData<BasketData>()
    val basketData: LiveData<BasketData>
        get() = _basketData

    //region Offers
    fun fetchOffers() = viewModelScope.launch {
        _offersList.value = Event.Loading(true)

        // make the service call
        val offersList = withContext(coroutineContext) { offersDataSource.getOffers() }

        _offersList.value = Event.Loading(false)

        _offersList.value = if (offersList != null) {
            Event.Success(offersList)
        } else {
            Event.Failure()
        }
    }
    //endregion

    //region Basket
    fun addOfferToBasket(offer: Offer) {
        _basketData.value = basketDataSource.addOfferToBasket(offer)
    }

    fun reduceOfferFromBasket(basketItem: BasketItem) {
        _basketData.value = if (basketItem.qty == 1) {
            basketDataSource.removeOfferFromBasket(basketItem.item)
        } else {
            basketDataSource.reduceOfferQuantity(basketItem.item)
        }
    }
    //endregion


}
