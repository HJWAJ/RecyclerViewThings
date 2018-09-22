package com.hjwaj.myapplication.nestedstagger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjwaj.myapplication.R;
import com.hjwaj.myapplication.VH;

import java.util.ArrayList;
import java.util.Random;

public class NestedStaggerActivity extends Activity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_with_simple_recycler_view);
        RecyclerView rv = findViewById(R.id.rv);
        rv.setItemAnimator(null);
        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setReverseLayout(true);
//        llm.setStackFromEnd(true);
        rv.setLayoutManager(llm);
        rv.setAdapter(new RecyclerView.Adapter() {

            private ArrayList<Integer> height = new ArrayList<>();

            private RecyclerView.Adapter mAdapter = new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    TextView tv = new TextView(parent.getContext());
                    tv.setTextSize(20);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextColor(0xff111111);
                    tv.setBackgroundColor(0xffffffff);
                    return new VH(tv);
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height.get(position)));
                    ((TextView) holder.itemView).setText((height.get(position) / 3) + "");
                }

                @Override
                public int getItemCount() {
                    return height.size();
                }
            };

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    TextView tv = new TextView(parent.getContext());
                    tv.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
                    tv.setTextSize(20);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextColor(0xff111111);
                    tv.setBackgroundColor(0xffffffff);
                    return new VH(tv);
                } else {
                    RecyclerView nested = new RecyclerView(parent.getContext());
                    nested.setFocusable(false);
                    nested.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1920 - 240));
                    nested.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    nested.setItemAnimator(null);
                    nested.addItemDecoration(new RecyclerView.ItemDecoration() {
                        @Override
                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                            outRect.top = 30;
                            outRect.left = 30;
                            outRect.right = 30;
                            outRect.bottom = 30;
                        }
                    });
                    nested.setAdapter(mAdapter);
                    append();
                    return new VH(nested);
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                switch (position) {
                    case 0:
                        ((TextView) holder.itemView).setText("Header");
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                height.clear();
                                append();
                            }
                        });
                        break;
                    case 2:
                        ((TextView) holder.itemView).setText("Footer");
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                append();
                            }
                        });
                        break;
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }

            @Override
            public int getItemViewType(int position) {
                if (position == 1) return 1;
                return 0;
            }

            Random random = new Random();

            private void append() {
                for (int i = 0; i < 10; i++) {
                    height.add((100 + random.nextInt(300)) * 3);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
