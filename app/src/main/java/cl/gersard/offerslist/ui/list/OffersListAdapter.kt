package cl.gersard.offerslist.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.gersard.offerslist.R
import cl.gersard.offerslist.data.model.Offer
import cl.gersard.offerslist.databinding.OfferItemBinding
import cl.gersard.offerslist.util.loadImage

class OffersListAdapter(val clickListener: OfferClickListener) : RecyclerView.Adapter<OffersListAdapter.OfferViewHolder>() {

    private var offersList: List<Offer> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        return OfferViewHolder(OfferItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bindOffer(offersList[position])
    }

    override fun getItemCount(): Int = offersList.size

    fun updateOffersList(offerList: List<Offer>) {
        this.offersList = offerList
        notifyItemRangeInserted(0, offerList.size - 1)
    }

    interface OfferClickListener {
        fun onOfferClickListener(offer: Offer)
    }

    inner class OfferViewHolder(private val binding: OfferItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.offerFab.setOnClickListener(this)
        }

        fun bindOffer(offer: Offer) {
            with(binding) {
                offerTitle.text = offer.title
                offerPrice.text = root.context.getString(R.string.price_format, offer.price)
                offerImage.loadImage(offer.image)
            }
        }

        override fun onClick(p0: View?) {
            clickListener.onOfferClickListener(offersList[adapterPosition])
        }

    }


}