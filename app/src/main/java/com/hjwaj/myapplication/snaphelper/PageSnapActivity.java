package com.hjwaj.myapplication.snaphelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hjwaj.myapplication.R;
import com.hjwaj.myapplication.VH;

public class PageSnapActivity extends Activity {

    private static final int[] COLORS = new int[]{0xff87ceeb, 0xffffb6c1, 0xff8fbc8f, 0xfff5deb3, 0xfff08080};

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_with_simple_recycler_view);
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(new RecyclerView.Adapter() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = new View(PageSnapActivity.this);
                view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return new VH(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                if (position == 0) {
                    lp.width = 540;
                } else {
                    lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                holder.itemView.setLayoutParams(lp);
                holder.itemView.setBackgroundColor(COLORS[position]);
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        });
        new PagerSnapHelper().attachToRecyclerView(rv);
    }
}
