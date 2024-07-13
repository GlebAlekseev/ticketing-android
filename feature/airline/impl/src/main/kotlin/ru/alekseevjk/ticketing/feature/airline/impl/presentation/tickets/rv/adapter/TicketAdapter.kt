package ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.rv.adapter

import android.content.Context
import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        val item = getItem(position)
        bindTicketData(holder.itemView.context, holder as TicketViewHolder, item)
    }

    private fun bindTicketData(context: Context, holder: TicketViewHolder, item: TicketWithTime) {
        val resources = context.resources

        bindPrice(holder, resources, item)
        bindAirportCodes(holder, item)
        bindTime(holder, item)
        bindDescription(holder, resources, item)
        bindLabel(holder, item)
    }

    private fun bindPrice(holder: TicketViewHolder, resources: Resources, item: TicketWithTime) {
        val pattern = resources.getString(R.string.offer_price)
        val formattedString = String.format(pattern, item.ticket.price.value)
        holder.binding.priceTV.text = formattedString
    }

    private fun bindAirportCodes(holder: TicketViewHolder, item: TicketWithTime) {
        holder.binding.departureCodeTV.text = item.ticket.departure.airport
        holder.binding.arrivalCodeTV.text = item.ticket.arrival.airport
    }

    private fun bindTime(holder: TicketViewHolder, item: TicketWithTime) {
        val timePattern = DateTimeFormatter.ofPattern("hh:mm", Locale("ru"))
        holder.binding.departureTimeTV.text = item.ticket.departure.date.format(timePattern)
        holder.binding.arrivalTimeTV.text = item.ticket.arrival.date.format(timePattern)
    }

    private fun bindDescription(holder: TicketViewHolder, resources: Resources, item: TicketWithTime) {
        val transferText = if (!item.ticket.hasTransfer) resources.getString(R.string.no_transfers) else ""
        val description = resources.getString(R.string.ticket_description,
            String.format("%.1f", item.travellingTime), transferText)
        val spannable = SpannableString(description)
        val start = spannable.indexOf('/')
        if (start != -1){
            val end = start + 1
            spannable.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.grey_6
                    )
                ),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        holder.binding.descriptionTV.text = spannable
    }

    private fun bindLabel(holder: TicketViewHolder, item: TicketWithTime) {
        if (item.ticket.badge == null) {
            holder.binding.labelCV.visibility = View.GONE
        } else {
            holder.binding.labelCV.visibility = View.VISIBLE
            holder.binding.labelTV.text = item.ticket.badge
        }
    }
}
