package ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.alekseevjk.ticketing.design.R
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.ItemOfferBinding
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Offer
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.callback.OfferDiffCallback
import javax.inject.Inject

class OffersAdapter @Inject constructor() : ListAdapter<Offer, ViewHolder>(OfferDiffCallback()) {
    inner class OfferViewHolder(val binding: ItemOfferBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOfferBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is OfferViewHolder -> {
                val offer = getItem(position)
                with(holder.binding) {
                    this.titleTV.text = offer.title
                    this.townTV.text = offer.town
                    val pattern = holder.itemView.context.getString(R.string.offer_price)
                    val formattedString = String.format(pattern, offer.price.value)
                    this.priceTV.text = formattedString
                }
            }
        }
    }
}