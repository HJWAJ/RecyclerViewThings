package com.hjwaj.myapplication.prefetch;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PreLoadLinearLayoutManager extends LinearLayoutManager {

    private OrientationHelper mOrientationHelper;

    /**
     * As {@link LinearLayoutManager#collectAdjacentPrefetchPositions} will prefetch one view for us,
     * we only need to prefetch additional ones.
     */
    private int mAdditionalAdjacentPrefetchItemCount = 0;

    public PreLoadLinearLayoutManager(Context context) {
        super(context);
        init();
    }

    public PreLoadLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        init();
    }

    public PreLoadLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mOrientationHelper = OrientationHelper.createOrientationHelper(this, getOrientation());
    }

    public void setAdjacentPrefetchItemCount(int adjacentPrefetchItemCount) {
        if (adjacentPrefetchItemCount < 1) {
            throw new IllegalArgumentException("adjacentPrefetchItemCount must not smaller than 1!");
        }
        mAdditionalAdjacentPrefetchItemCount = adjacentPrefetchItemCount - 1;
    }

    @Override
    public void collectAdjacentPrefetchPositions(int dx, int dy, RecyclerView.State state,
                                                 LayoutPrefetchRegistry layoutPrefetchRegistry) {
        super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry);

        // We can not access mLayoutState, so we have to get info by ourselves.
        // See LinearLayoutManager#updateLayoutState
        int delta = (getOrientation() == HORIZONTAL) ? dx : dy;
        Log.d("RecyclerView Prefetch", "delta: " + delta);
        if (getChildCount() == 0 || delta == 0) {
            // can't support this scroll, so don't bother prefetching
            return;
        }
        final int layoutDirection = delta > 0 ? 1 : -1;
        final View child = getChildClosest(layoutDirection);
        final int currentPosition = getPosition(child) + layoutDirection;
        int scrollingOffset;
        // Our aim is to pre-load, so we just handle layoutDirection=1 situation.
        // If we handle layoutDirection=-1 situation, each scroll with slightly toggle directions will cause huge bindings.
        if (layoutDirection == 1) {
            scrollingOffset = mOrientationHelper.getDecoratedEnd(child)
                    - mOrientationHelper.getEndAfterPadding();
            for (int i = currentPosition + 1; i < currentPosition + mAdditionalAdjacentPrefetchItemCount + 1; i++) {
                if (i >= 0 && i < state.getItemCount()) {
                    layoutPrefetchRegistry.addPosition(i, Math.max(0, scrollingOffset));
                }
            }
//        } else {
//            scrollingOffset = -mOrientationHelper.getDecoratedStart(child)
//                    + mOrientationHelper.getStartAfterPadding();
//            for (int i = currentPosition - 1; i > currentPosition - mAdditionalAdjacentPrefetchItemCount - 1; i--) {
//                if (i >= 0 && i < state.getItemCount()) {
//                    layoutPrefetchRegistry.addPosition(i, Math.max(0, scrollingOffset));
//                }
//            }
        }
    }

    private View getChildClosest(int layoutDirection) {
        return getChildAt(layoutDirection == -1 ? 0 : getChildCount() - 1);
    }
}
