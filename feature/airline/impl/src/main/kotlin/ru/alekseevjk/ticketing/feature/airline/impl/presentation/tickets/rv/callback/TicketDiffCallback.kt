package ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.rv.callback

import androidx.recyclerview.widget.DiffUtil
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.entity.TicketWithTime

class TicketDiffCallback : DiffUtil.ItemCallback<TicketWithTime>() {
    override fun areItemsTheSame(oldItem: TicketWithTime, newItem: TicketWithTime): Boolean {
        return oldItem.ticket.id == newItem.ticket.id
    }

    override fun areContentsTheSame(oldItem: TicketWithTime, newItem: TicketWithTime): Boolean {
        return oldItem == newItem
    }
}