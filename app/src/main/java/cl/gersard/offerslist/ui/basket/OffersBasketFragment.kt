package cl.gersard.offerslist.ui.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cl.gersard.offerslist.R
import cl.gersard.offerslist.data.model.BasketData
import cl.gersard.offerslist.data.model.BasketItem
import cl.gersard.offerslist.databinding.FragmentOffersBasketBinding
import cl.gersard.offerslist.ui.OffersViewModel
import cl.gersard.offerslist.util.observe
import cl.gersard.offerslist.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment in charge to show the basket data
 */
@AndroidEntryPoint
class OffersBasketFragment : Fragment(R.layout.fragment_offers_basket), BasketAdapter.BasketClickListener {

    private val viewModel = activityViewModels<OffersViewModel>()
    private val binding by viewBinding(FragmentOffersBasketBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpObservers()
    }

    private fun setUpUI() {
        with(binding) {
            basketRv.setHasFixedSize(true)
            basketRv.layoutManager = LinearLayoutManager(requireContext())
            basketRv.adapter = BasketAdapter(this@OffersBasketFragment)

            basketBtnReturn.setOnClickListener { requireActivity().onBackPressed() }
        }
    }

    private fun setUpObservers() {
        observe(viewModel.value.basketData, ::handleBasketData)
    }

    private fun handleBasketData(basketData: BasketData) {
        (binding.basketRv.adapter as BasketAdapter).updateBasketItemsList(basketData.items)
    }

    override fun onAddOffer(basketItem: BasketItem) {
        viewModel.value.addOfferToBasket(basketItem.item)
    }

    override fun onReduceOffer(basketItem: BasketItem) {
        viewModel.value.reduceOfferFromBasket(basketItem)
    }


}