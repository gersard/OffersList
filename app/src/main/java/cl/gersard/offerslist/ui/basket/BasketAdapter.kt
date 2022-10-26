package cl.gersard.offerslist.ui.basket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.gersard.offerslist.R
import cl.gersard.offerslist.data.model.BasketItem
import cl.gersard.offerslist.databinding.BasketItemBinding
import cl.gersard.offerslist.util.loadImage

class BasketAdapter(val clickListener: BasketClickListener) : RecyclerView.Adapter<BasketAdapter.BasketItemViewHolder>() {

    private var basketItems = mutableListOf<BasketItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketItemViewHolder {
        return BasketItemViewHolder(BasketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BasketItemViewHolder, position: Int) {
        holder.bindBasketItem(basketItems[position])
    }

    override fun getItemCount(): Int = basketItems.size

    fun updateBasketItemsList(basketItems: MutableList<BasketItem>) {
        this.basketItems = basketItems
        notifyDataSetChanged()
    }

    interface BasketClickListener {
        fun onAddOffer(basketItem: BasketItem)
        fun onReduceOffer(basketItem: BasketItem)
    }

    inner class BasketItemViewHolder(private val binding: BasketItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.basketFabAdd.setOnClickListener(this)
            binding.basketFabReduce.setOnClickListener(this)
        }

        fun bindBasketItem(basketItem: BasketItem) {
            with(binding) {
                offerTitle.text = basketItem.item.title
                offerPrice.text = root.context.getString(R.string.price_format, basketItem.item.price * basketItem.qty)
                offerImage.loadImage(basketItem.item.image)
            }
        }

        override fun onClick(view: View?) {
            when(view?.id) {
                binding.basketFabAdd.id -> clickListener.onAddOffer(basketItems[adapterPosition])
                binding.basketFabReduce.id -> clickListener.onReduceOffer(basketItems[adapterPosition])
            }
        }

    }


}