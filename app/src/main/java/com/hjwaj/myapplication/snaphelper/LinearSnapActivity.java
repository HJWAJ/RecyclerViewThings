package com.hjwaj.myapplication.snaphelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjwaj.myapplication.R;
import com.hjwaj.myapplication.VH;

public class LinearSnapActivity extends Activity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_with_simple_recycler_view);
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rv.setBackgroundColor(0xffcccccc);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv.setPadding(0, 30, 0, 30);
        rv.setAdapter(new RecyclerView.Adapter() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView tv = new TextView(LinearSnapActivity.this);
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 180);
                lp.setMargins(15, 0, 15, 0);
                tv.setLayoutParams(lp);
                tv.setTextSize(15);
                tv.setTextColor(0xff111111);
                tv.setGravity(Gravity.CENTER);
                tv.setMinWidth(300);
                tv.setBackgroundColor(0xffffffff);
                return new VH(tv);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((TextView) holder.itemView).setText(position + "");
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                if (position == 0) {
                    lp.leftMargin = 30;
                    lp.rightMargin = 15;
                } else if (position == getItemCount() - 1) {
                    lp.leftMargin = 15;
                    lp.rightMargin = 30;
                } else {
                    lp.leftMargin = lp.rightMargin = 15;
                }
                holder.itemView.setLayoutParams(lp);
            }

            @Override
            public int getItemCount() {
                return 30;
            }
        });
//        new LinearSnapHelper().attachToRecyclerView(rv);
        new StartSnapHelper().attachToRecyclerView(rv);
//        new PagerSnapHelper().attachToRecyclerView(rv);
    }
}
