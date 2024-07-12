package ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.alekseevjk.ticketing.feature.airline.impl.databinding.ItemPopularDestinationBinding
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.entity.PopularDestinationWithImage

class PopularDestinationAdapter(
    private val onClick: (destinationName: String) -> Unit,
    private val list: List<PopularDestinationWithImage>
) : RecyclerView.Adapter<PopularDestinationAdapter.PopularDestinationViewHolder>() {
    class PopularDestinationViewHolder(val binding: ItemPopularDestinationBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularDestinationViewHolder {
        return PopularDestinationViewHolder(
            ItemPopularDestinationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PopularDestinationViewHolder, position: Int) {
        val item = list[position]
        holder.binding.descriptionTV.text = item.popularDestination.description
        holder.binding.destinationNameTV.text = item.popularDestination.destinationName
        holder.binding.destinationIV.setImageResource(item.imageResource)
        holder.binding.root.setOnClickListener {
            onClick.invoke(item.popularDestination.destinationName)
        }
    }
}