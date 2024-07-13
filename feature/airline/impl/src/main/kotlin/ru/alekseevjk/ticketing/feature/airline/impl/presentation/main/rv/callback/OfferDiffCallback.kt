package ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.callback

import androidx.recyclerview.widget.DiffUtil
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.entity.OfferWithImage

class OfferDiffCallback : DiffUtil.ItemCallback<OfferWithImage>() {
    override fun areItemsTheSame(oldItem: OfferWithImage, newItem: OfferWithImage): Boolean {
        return oldItem.offer.id == newItem.offer.id
    }

    override fun areContentsTheSame(oldItem: OfferWithImage, newItem: OfferWithImage): Boolean {
        return oldItem == newItem
    }
}