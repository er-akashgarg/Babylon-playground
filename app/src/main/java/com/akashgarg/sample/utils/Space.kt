package com.akashgarg.sample.utils

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class Space(private var space: Int, private var spanCount: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildLayoutPosition(view) <= spanCount) {
            outRect.top = space
        }
        outRect.bottom = space
    }
}