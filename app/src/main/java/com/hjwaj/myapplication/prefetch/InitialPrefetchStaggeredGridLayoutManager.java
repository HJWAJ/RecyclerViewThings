package com.hjwaj.myapplication.prefetch;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * A StaggeredGridLayoutManager which supports initial prefetch.
 * As StaggeredGridLayoutManager's anchor position is not accessible,
 * this can only satisfy the situation that the RecyclerView can not scroll,
 * which means the first items are always preparing to bind while parent RecyclerView
 * is prefetching.
 */
public class InitialPrefetchStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

    public InitialPrefetchStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    /**
     * Number of items to prefetch when first coming on screen with new data.
     */
    private int mInitialPrefetchItemCount = 2;

    public void setInitialPrefetchItemCount(int itemCount) {
        mInitialPrefetchItemCount = itemCount;
    }

    @Override
    public void collectAdjacentPrefetchPositions(int dx, int dy, RecyclerView.State state,
                                                 LayoutPrefetchRegistry layoutPrefetchRegistry) {
        super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry);
    }

    @Override
    public void collectInitialPrefetchPositions(int adapterItemCount,
                                                LayoutPrefetchRegistry layoutPrefetchRegistry) {
        // Only prefetch first N items as we are not accessible to the anchor.
        for (int i = 0; i < mInitialPrefetchItemCount; i++) {
            if (i >= 0 && i < adapterItemCount) {
                layoutPrefetchRegistry.addPosition(i, 0);
            } else {
                break; // no more to prefetch
            }
        }
    }
}
