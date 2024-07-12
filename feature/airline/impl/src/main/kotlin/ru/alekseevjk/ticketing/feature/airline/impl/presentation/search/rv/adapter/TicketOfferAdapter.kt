package ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.math.MathUtils
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.alekseevjk.ticketing.design.R
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.ItemShowBinding
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.ItemTicketOfferBinding
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.rv.callback.TicketOfferDiffCallback
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.entity.TicketOfferWithColor
import javax.inject.Inject


class TicketOfferAdapter @Inject constructor() :
    ListAdapter<TicketOfferWithColor, ViewHolder>(TicketOfferDiffCallback()) {
    inner class TicketOfferViewHolder(val binding: ItemTicketOfferBinding) :
        ViewHolder(binding.root)

    inner class ShowViewHolder(val binding: ItemShowBinding) :
        ViewHolder(binding.root)

    private var isShowAll = false

    override fun getItemViewType(position: Int): Int {
        val lastIndex = MathUtils.clamp(itemCount - 1, 0, 2)
        return if (position == lastIndex && !isShowAll) {
            VIEW_TYPE_SHOW
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = ItemTicketOfferBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                TicketOfferViewHolder(binding)
            }

            VIEW_TYPE_SHOW -> {
                val binding = ItemShowBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ShowViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is TicketOfferViewHolder -> {
                val item = getItem(position)
                holder.binding.titleTV.text = item.ticketOffer.title
                holder.binding.timeListTV.text = item.ticketOffer.timeRange.joinToString(" ")
                val pattern = holder.itemView.context.getString(R.string.offer_price)
                val formattedString = String.format(pattern, item.ticketOffer.price.value)
                holder.binding.priceTV.text = formattedString
                holder.binding.colorCV.setCardBackgroundColor(item.color)
            }

            is ShowViewHolder -> {
                holder.binding.root.setOnClickListener {
                    isShowAll = true
                    notifyDataSetChanged()
                }
            }
        }
    }

    companion object {
        const val VIEW_TYPE_ITEM = 1
        const val VIEW_TYPE_SHOW = 2
    }
}