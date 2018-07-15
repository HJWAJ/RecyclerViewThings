package com.hjwaj.myapplication.prefetch;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

public class InitialPrefetchStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

    public InitialPrefetchStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public void collectAdjacentPrefetchPositions(int dx, int dy, RecyclerView.State state,
                                                 LayoutPrefetchRegistry layoutPrefetchRegistry) {
        super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry);
    }

    @Override
    public void collectInitialPrefetchPositions(int adapterItemCount,
                                                LayoutPrefetchRegistry layoutPrefetchRegistry) {

    }
}
