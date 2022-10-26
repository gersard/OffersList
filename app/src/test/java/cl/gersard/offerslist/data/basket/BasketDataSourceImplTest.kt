package cl.gersard.offerslist.data.basket

import cl.gersard.offerslist.data.model.Offer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class BasketDataSourceImplTest {

    private lateinit var basketDataSourceImpl: BasketDataSourceImpl

    lateinit var fakeOffer: Offer

    @Before
    fun setUp() {
        basketDataSourceImpl = BasketDataSourceImpl()
        basketDataSourceImpl.clearBasketData()

        fakeOffer = Offer("id", "Product", "imageURL", 500)
    }

    @Test
    fun `when getBasketData is called, then the basket data must be returned`() {
        with(basketDataSourceImpl.getBasketData()) {
            assert(items.size == 0)
        }
    }

    @Test
    fun `when addOfferToBasket is called, then the offer must be added into the list`() {
        val basketData = basketDataSourceImpl.addOfferToBasket(fakeOffer)

        assert(basketData.items.size == 1)
        assert(basketData.items[0].qty == 1)
        assert(basketData.items[0].item == fakeOffer)
    }

    @Test
    fun `when reduceOfferQuantity is called, then the offer quantity must be reduced by 1`() {
        // Added 3 times so quantity = 3
        basketDataSourceImpl.addOfferToBasket(fakeOffer)
        basketDataSourceImpl.addOfferToBasket(fakeOffer)
        basketDataSourceImpl.addOfferToBasket(fakeOffer)

        // Reduced quantity by 1
        basketDataSourceImpl.reduceOfferQuantity(fakeOffer)

        with(basketDataSourceImpl.getBasketData()) {
            assert(items.size == 1)
            assert(items[0].qty == 2)
            assert(items[0].item == fakeOffer)
        }
    }

    @Test
    fun `when removeOfferFromBasket is called, then the offer must be removed from the list`() {
        // Added 3 times so quantity = 3
        basketDataSourceImpl.addOfferToBasket(fakeOffer)
        basketDataSourceImpl.addOfferToBasket(fakeOffer)
        basketDataSourceImpl.addOfferToBasket(fakeOffer)

        // Removed offer from the list
        basketDataSourceImpl.removeOfferFromBasket(fakeOffer)

        with(basketDataSourceImpl.getBasketData()) {

            assert(items.size == 0)

            val item = items.find { it.item.id == fakeOffer.id }
            assert(item == null)
        }
    }

    @Test
    fun `when clearBasketData is called, then the basket data must return an empty list`() {
        basketDataSourceImpl.clearBasketData()

        with(basketDataSourceImpl.getBasketData()) {
            assert(items.size == 0)
        }
    }
}