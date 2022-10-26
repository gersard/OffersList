package cl.gersard.offerslist.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cl.gersard.offerslist.R
import cl.gersard.offerslist.core.Event
import cl.gersard.offerslist.data.model.Offer
import cl.gersard.offerslist.databinding.FragmentOffersListBinding
import cl.gersard.offerslist.ui.OffersViewModel
import cl.gersard.offerslist.util.observe
import cl.gersard.offerslist.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment in charge to show the list of offers
 */
@AndroidEntryPoint
class OffersListFragment : Fragment(R.layout.fragment_offers_list), OffersListAdapter.OfferClickListener {

    private val viewModel = activityViewModels<OffersViewModel>()
    private val binding by viewBinding(FragmentOffersListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpObservers()
        viewModel.value.fetchOffers()
    }

    private fun setUpUI() {
        with(binding) {
            // Offers list
            offersRv.setHasFixedSize(true)
            offersRv.layoutManager = LinearLayoutManager(requireContext())
            offersRv.adapter = OffersListAdapter(this@OffersListFragment)
        }
    }

    private fun setUpObservers() {
        observe(viewModel.value.offersList, ::handleOffersList)
    }

    private fun handleOffersList(event: Event<List<Offer>>) {
        when (event) {
            is Event.Failure -> Toast.makeText(requireContext(), "An error has ocurred", Toast.LENGTH_SHORT).show()
            is Event.Loading -> binding.pb.visibility = if (event.isLoading) View.VISIBLE else View.GONE
            is Event.Success -> (binding.offersRv.adapter as OffersListAdapter).updateOffersList(event.items)
        }
    }

    override fun onOfferClickListener(offer: Offer) {
        viewModel.value.addOfferToBasket(offer)
        Toast.makeText(requireContext(), "${offer.title} added successfully", Toast.LENGTH_SHORT).show()
    }
}