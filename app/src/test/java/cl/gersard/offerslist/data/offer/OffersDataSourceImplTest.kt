package cl.gersard.offerslist.data.offer

import cl.gersard.offerslist.data.ApiService
import cl.gersard.offerslist.data.model.Offer
import cl.gersard.offerslist.data.model.OffersListResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class OffersDataSourceImplTest {

    lateinit var offersDataSourceImpl: OffersDataSourceImpl
    lateinit var offersListResponseWithData: OffersListResponse
    lateinit var offersListResponseWithEmptyData: OffersListResponse

    @Mock
    lateinit var apiService: ApiService

    @Before
    fun setUp() {
        offersListResponseWithData = OffersListResponse(listOf(Offer("id","Product","imageURL",250)))
        offersListResponseWithEmptyData = OffersListResponse(emptyList())
        offersDataSourceImpl = OffersDataSourceImpl(apiService)
    }

    @Test
    fun `when apiService returns a valid response then the subList must be returned successfully`() {
        runBlocking {
            Mockito.`when`(apiService.getOffersList()).thenReturn(offersListResponseWithEmptyData)

            val response = offersDataSourceImpl.getOffers()

            assert(offersListResponseWithEmptyData.items == response)
        }
    }

    @Test
    fun `when apiService returns a valid response and the subList contains more than 1 item then the subList must be returned successfully`() {
        runBlocking {
            Mockito.`when`(apiService.getOffersList()).thenReturn(offersListResponseWithData)

            val response = offersDataSourceImpl.getOffers()

            assert(offersListResponseWithData.items == response)
            assert(response?.size == 1)
        }
    }

    @Test
    fun `when apiService returns a null response then null must be returned`() {
        runBlocking {
            Mockito.`when`(apiService.getOffersList()).thenReturn(null)

            val response = offersDataSourceImpl.getOffers()

            assert(response == null)
        }
    }

}