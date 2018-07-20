package com.hjwaj.myapplication.prefetch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjwaj.myapplication.R;

public class InitialPrefetchActivity extends Activity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_with_simple_recycler_view);
        RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        /* This adapter makes all item simple TextView's, except item 10 is a nested RecyclerView
         * with LinearLayoutManager and item 15 is a nested RecyclerView with StaggeredGridLayoutManager
         */
        rv.setAdapter(new RecyclerView.Adapter() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType) {
                    case 1:
                    case 2:
                        RecyclerView recyclerView = new RecyclerView(InitialPrefetchActivity.this);
                        recyclerView.setLayoutParams(new RecyclerView.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        if (viewType == 1) {
                            /* We can see while binding item 10 by prefetch, nested item 0 & 1 are also bound,
                             * as default initial prefetch item count is 2.
                             */
                            LinearLayoutManager llm = new LinearLayoutManager(InitialPrefetchActivity.this);
                            recyclerView.setLayoutManager(llm);
                            // After set this, we can see all 3 nested items will bind when item 10 is bound.
//                            llm.setInitialPrefetchItemCount(5);
                            recyclerView.setAdapter(new NestedLinearAdapter());
                        }
                        if (viewType == 2) {
                            /* StaggeredGridLayoutManager doesn't implement collectInitialPrefetchPositions,
                             * so we can't see nested item binding when prefetching item 15.
                             */
//                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(
//                                    2, StaggeredGridLayoutManager.VERTICAL));

                            /* We implemented a StaggeredGridLayoutManager with collectInitialPrefetchPositions
                             * implemented, so we can see nested item binding now.
                             */
                            recyclerView.setLayoutManager(new InitialPrefetchStaggeredGridLayoutManager(
                                    2, StaggeredGridLayoutManager.VERTICAL));
                            recyclerView.setAdapter(new NestedStaggeredAdapter());
                        }
                        return new VH(recyclerView);
                    case 0:
                    default:
                        return createTextVH();
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                Log.d("RecyclerView", "bind " + position);
                int viewType = getItemViewType(position);
                switch (viewType) {
                    case 0:
                        ((TextView) holder.itemView).setText("" + position);
                        break;
                    case 1:
                        ((NestedLinearAdapter) ((RecyclerView) holder.itemView).getAdapter()).setCount(3);
                        break;
                    case 2:
                        ((NestedStaggeredAdapter) ((RecyclerView) holder.itemView).getAdapter()).setCount(3);
                        break;
                }
            }

            @Override
            public int getItemCount() {
                return 20;
            }

            @Override
            public int getItemViewType(int position) {
                if (position == 10) { // rv with llm
                    return 1;
                }
                if (position == 15) { // rv with sglm
                    return 2;
                }
                return 0;
            }
        });
    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(View itemView) {
            super(itemView);
        }
    }

    private class NestedLinearAdapter extends RecyclerView.Adapter {
        private int count;

        public void setCount(int count) {
            this.count = count;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return createTextVH();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d("RecyclerView", "bind nested linear" + position);
            ((TextView) holder.itemView).setText("Nested: " + position);
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }

    private class NestedStaggeredAdapter extends RecyclerView.Adapter {
        private int count;

        public void setCount(int count) {
            this.count = count;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return createTextVH();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d("RecyclerView", "bind nested staggered" + position);
            ((TextView) holder.itemView).setText("Nested: " + position);
            if (position == 0) {
                holder.itemView.getLayoutParams().height = 720;
            }
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }

    private VH createTextVH() {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 360));
        tv.setTextSize(20);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(0xff111111);
        tv.setBackgroundColor(0xffffffff);
        return new VH(tv);
    }
}
