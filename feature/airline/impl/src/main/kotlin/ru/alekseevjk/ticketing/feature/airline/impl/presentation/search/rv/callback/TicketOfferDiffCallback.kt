package ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.rv.callback

import androidx.recyclerview.widget.DiffUtil
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.entity.TicketOfferWithColor

class TicketOfferDiffCallback : DiffUtil.ItemCallback<TicketOfferWithColor>() {
    override fun areItemsTheSame(oldItem: TicketOfferWithColor, newItem: TicketOfferWithColor): Boolean {
        return oldItem.ticketOffer.id == newItem.ticketOffer.id
    }

    override fun areContentsTheSame(oldItem: TicketOfferWithColor, newItem: TicketOfferWithColor): Boolean {
        return oldItem == newItem
    }
}