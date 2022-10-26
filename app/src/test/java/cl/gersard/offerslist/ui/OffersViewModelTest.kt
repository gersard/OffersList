package cl.gersard.offerslist.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cl.gersard.offerslist.CoroutineTestRule
import cl.gersard.offerslist.core.Event
import cl.gersard.offerslist.data.basket.BasketDataSource
import cl.gersard.offerslist.data.model.BasketData
import cl.gersard.offerslist.data.model.BasketItem
import cl.gersard.offerslist.data.model.Offer
import cl.gersard.offerslist.data.offer.OffersDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any

@RunWith(MockitoJUnitRunner::class)
internal class OffersViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var offersDataSource: OffersDataSource

    @Mock
    lateinit var basketDataSource: BasketDataSource

    private lateinit var offersViewModel: OffersViewModel
    private lateinit var fakeOffersList: List<Offer>
    private lateinit var fakeOffer: Offer
    private lateinit var fakeBasketData: BasketData
    private lateinit var fakeBasketItemQty1: BasketItem
    private lateinit var fakeBasketItemQty2: BasketItem

    @Before
    fun setUp() {
        offersViewModel = OffersViewModel(offersDataSource, basketDataSource, coroutineTestRule.dispatcher)
        fakeOffer = Offer("id", "Product", "imageURL", 500)
        fakeOffersList = listOf(fakeOffer)
        fakeBasketData = BasketData(mutableListOf())
        fakeBasketItemQty1 = BasketItem(fakeOffer, 1)
        fakeBasketItemQty2 = BasketItem(fakeOffer, 2)
    }

    @Test
    fun `when fetchOffers is called and offersDataSource returns a valid response, then _offersList must be Success`() {
        runBlocking {
            Mockito.`when`(offersDataSource.getOffers()).thenReturn(fakeOffersList)

            offersViewModel.fetchOffers()

            assert(offersViewModel.offersList.value is Event.Success)
        }
    }

    @Test
    fun `when fetchOffers is called and offersDataSource returns a valid response, then _offersList must be Success and its list must be the same the one was returned`() {
        runBlocking {
            Mockito.`when`(offersDataSource.getOffers()).thenReturn(fakeOffersList)

            offersViewModel.fetchOffers()

            assert(offersViewModel.offersList.value is Event.Success)
            assert((offersViewModel.offersList.value as Event.Success).items == fakeOffersList)
        }
    }

    @Test
    fun `when fetchOffers is called and offersDataSource returns an issue, then _offersList must be Failure`() {
        runBlocking {
            Mockito.`when`(offersDataSource.getOffers()).thenReturn(null)

            offersViewModel.fetchOffers()

            assert(offersViewModel.offersList.value is Event.Failure)
        }
    }

    @Test
    fun `when addOfferToBasket is called, then the basketData must be the same the one was returned`() {
        Mockito.`when`(basketDataSource.addOfferToBasket(any())).thenReturn(fakeBasketData)

        offersViewModel.addOfferToBasket(fakeOffer)

        assert(offersViewModel.basketData.value == fakeBasketData)
    }

    @Test
    fun `when reduceOfferFromBasket is called, and qty is 1 then removeOfferFromBasket must be called`() {

        offersViewModel.reduceOfferFromBasket(fakeBasketItemQty1)

        Mockito.verify(basketDataSource, Mockito.times(1)).removeOfferFromBasket(fakeBasketItemQty1.item)
        Mockito.verify(basketDataSource, Mockito.times(0)).reduceOfferQuantity(fakeBasketItemQty1.item)
    }

    @Test
    fun `when reduceOfferFromBasket is called, and qty is 2 or more then reduceOfferFromBasket must be called`() {

        offersViewModel.reduceOfferFromBasket(fakeBasketItemQty2)

        Mockito.verify(basketDataSource, Mockito.times(0)).removeOfferFromBasket(fakeBasketItemQty1.item)
        Mockito.verify(basketDataSource, Mockito.times(1)).reduceOfferQuantity(fakeBasketItemQty1.item)
    }

}