package ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.callback

import androidx.recyclerview.widget.DiffUtil
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Offer

class OfferDiffCallback : DiffUtil.ItemCallback<Offer>() {
    override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
        return oldItem == newItem
    }
}