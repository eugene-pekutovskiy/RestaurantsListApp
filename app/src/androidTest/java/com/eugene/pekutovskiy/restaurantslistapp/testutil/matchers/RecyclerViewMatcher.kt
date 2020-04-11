package com.eugene.pekutovskiy.restaurantslistapp.testutil.matchers

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

object RecyclerViewMatcher {
    fun hasItemsCount(expectedAmount: Int): TypeSafeMatcher<View> {
        return object : TypeSafeMatcher<View>() {
            var actualAmount = 0
            override fun describeTo(description: Description) {
                description.appendText("expected amount is $expectedAmount, but actual is $actualAmount")
            }

            override fun matchesSafely(item: View): Boolean {
                actualAmount = (item as RecyclerView).adapter?.itemCount ?: 0
                return expectedAmount == actualAmount
            }
        }
    }

    fun atPositionOnView(
        recyclerViewId: Int,
        position: Int,
        targetViewId: Int = -1
    ): TypeSafeMatcher<View> {
        return object : TypeSafeMatcher<View>() {
            var resources: Resources? = null
            var childView: View? = null

            override fun describeTo(description: Description) {
                var idDescription = recyclerViewId.toString()
                if (this.resources != null) {
                    idDescription = try {
                        this.resources!!.getResourceName(recyclerViewId)
                    } catch (var4: Resources.NotFoundException) {
                        "$recyclerViewId (resource name not found)"
                    }
                }

                description.appendText("RecyclerView with id: $idDescription at position: $position")
            }

            override fun matchesSafely(view: View): Boolean {

                this.resources = view.resources

                if (childView == null) {
                    val recyclerView = view.rootView.findViewById<RecyclerView>(recyclerViewId)
                    if (recyclerView?.id == recyclerViewId) {
                        val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                        childView = viewHolder?.itemView
                    } else {
                        return false
                    }
                }

                return if (targetViewId == -1) {
                    view === childView
                } else {
                    val targetView = childView?.findViewById<View>(targetViewId)
                    view == targetView

                }
            }
        }
    }
}