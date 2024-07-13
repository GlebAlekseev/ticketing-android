package ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.alekseevjk.ticketing.design.R
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.ItemOfferBinding
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.entity.OfferWithImage
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.callback.OfferDiffCallback
import javax.inject.Inject

class OffersAdapter @Inject constructor() :
    ListAdapter<OfferWithImage, ViewHolder>(OfferDiffCallback()) {
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
                val offerWithImage = getItem(position)
                with(holder.binding) {
                    val offer = offerWithImage.offer
                    this.titleTV.text = offer.title
                    this.townTV.text = offer.town
                    val pattern = holder.itemView.context.getString(R.string.offer_price)
                    val formattedString = String.format(pattern, offer.price.value)
                    this.priceTV.text = formattedString
                    this.bannerIV.setImageResource(offerWithImage.imageResource)
                }
            }
        }
    }
}