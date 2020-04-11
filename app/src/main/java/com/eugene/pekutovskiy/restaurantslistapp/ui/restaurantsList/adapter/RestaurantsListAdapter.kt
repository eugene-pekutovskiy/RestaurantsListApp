package com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.adapter

import android.graphics.drawable.LevelListDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eugene.pekutovskiy.restaurantslistapp.R
import com.eugene.pekutovskiy.restaurantslistapp.ui.restaurantsList.uimodel.RestaurantUiModel
import com.eugene.pekutovskiy.restaurantslistapp.ui.util.visibleIf
import kotlinx.android.synthetic.main.cell_restaurants_item.view.*
import javax.inject.Inject

class RestaurantsListAdapter @Inject constructor(
    diffCallback: RestaurantsListDiffCallback
) : ListAdapter<RestaurantUiModel, RestaurantsListAdapter.ViewHolder>(diffCallback) {

    var onClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder.create(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        val set = payloads.firstOrNull() as? Set<*>?
        set?.forEach {
            when (it) {
                PayloadUpdates.FAVOURITE_STATUS -> {
                    val uiModel = getItem(position)
                    holder.setupTopLabel(uiModel.topLabelText, uiModel.topLabelBackgroundColor)
                    holder.updateLoadingStatus(isLoading = false)
                    holder.updateFavouriteButton(getItem(position).isFavourite)
                }
                else -> super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    public override fun getItem(position: Int): RestaurantUiModel = super.getItem(position)


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            uiModel: RestaurantUiModel,
            callback: ((Int) -> Unit)?
        ) {
            itemView.apply {
                restaurantName.text = uiModel.name
                averageRating.text = uiModel.rating
                openingsState.text = uiModel.openingsStateString
                openingsState.setTextColor(uiModel.openingsStateTextColor)
                deliveryCost.text = uiModel.deliveryCosts
            }
            setupTopLabel(uiModel.topLabelText, uiModel.topLabelBackgroundColor)
            updateLoadingStatus(isLoading = false)
            updateFavouriteButton(uiModel.isFavourite)

            itemView.addToFavouritesButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    callback?.let {
                        updateLoadingStatus(true)
                        it.invoke(position)
                    }
                }
            }
        }

        fun setupTopLabel(
            topLabelText: String,
            @ColorInt backgroundColor: Int
        ) {

            if (topLabelText.isEmpty()) {
                itemView.topLabel.visibility = View.GONE
                return
            }

            itemView.topLabel.apply {
                text = topLabelText
                setBackgroundColor(backgroundColor)
                visibility = View.VISIBLE
            }
        }

        fun updateLoadingStatus(isLoading: Boolean) {
            itemView.progress.visibleIf(isLoading, invisibilityFlag = View.INVISIBLE)
            itemView.addToFavouritesButton.visibleIf(!isLoading, invisibilityFlag = View.INVISIBLE)
        }

        fun updateFavouriteButton(isFavourite: Boolean) {
            (itemView.addToFavouritesButton.drawable as LevelListDrawable).level =
                if (isFavourite) 1 else 0
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.cell_restaurants_item, parent, false
                )
                return ViewHolder(view)
            }
        }
    }
}