package ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.rv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.threeten.bp.format.DateTimeFormatter
import ru.alekseevjk.ticketing.design.R
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.ItemTicketBinding
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.entity.TicketWithTime
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.rv.callback.TicketDiffCallback
import java.util.Locale
import javax.inject.Inject


class TicketAdapter @Inject constructor() :
    ListAdapter<TicketWithTime, ViewHolder>(TicketDiffCallback()) {
    inner class TicketViewHolder(val binding: ItemTicketBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTicketBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is TicketViewHolder -> {
                val item = getItem(position)
                val pattern = holder.itemView.context.getString(R.string.offer_price)
                val formattedString = String.format(pattern, item.ticket.price.value)
                holder.binding.priceTV.text = formattedString
                holder.binding.departureCodeTV.text = item.ticket.departure.airport
                holder.binding.arrivalCodeTV.text = item.ticket.arrival.airport
                val timePattern = DateTimeFormatter.ofPattern("hh:mm", Locale("ru"))
                holder.binding.departureTimeTV.text = item.ticket.departure.date.format(timePattern)
                holder.binding.arrivalTimeTV.text = item.ticket.arrival.date.format(timePattern)

                val transferText = if (!item.ticket.hasTransfer) " / Без пересадок" else ""

                val description =
                    "${String.format("%.1f", item.travellingTime)}ч в пути" + transferText
                holder.binding.descriptionTV.text = description

                if (item.ticket.badge == null) {
                    holder.binding.labelCV.visibility = View.GONE
                } else {
                    holder.binding.labelCV.visibility = View.VISIBLE
                    holder.binding.labelTV.text = item.ticket.badge
                }
            }
        }
    }
}