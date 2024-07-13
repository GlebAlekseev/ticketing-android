package ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.rv.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class OfferSpaceItemDecoration(context: Context, spaceDp: Int) : ItemDecoration() {
    private val space: Int
    private val startSpace: Int
    private val endSpaceDp: Int = 0

    init {
        this.space = dpToPx(context, spaceDp)
        this.startSpace = dpToPx(context, 0)
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return Math.round(dp * density)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            return
        }

        if (position == 0) {
            outRect.left = startSpace
        } else {
            outRect.left = 0
        }

        if (position != parent.adapter!!.itemCount - 1) {
            outRect.right = space
        } else {

            val parentWidth = parent.width
            val viewWidth = view.width
            val endSpace = dpToPx(parent.context, endSpaceDp)
            outRect.right = endSpace
        }
    }
}
