package com.hjwaj.myapplication.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjwaj.myapplication.R;
import com.hjwaj.myapplication.VH;

public abstract class ListFragment extends Fragment {

    private RecyclerView.RecycledViewPool pool;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list, container, false);
        RecyclerView rv = rootView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        if (pool != null) {
            rv.setRecycledViewPool(pool);
        }
        rv.setAdapter(new RecyclerView.Adapter() {
            private int count = 1;

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView tv = new TextView(parent.getContext());
                tv.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 360));
                tv.setTextSize(20);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(0xff111111);
                return new VH(tv);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                int viewType = getItemViewType(position);
                switch (viewType) {
                    case 0:
                        ((TextView) holder.itemView).setText("" + position);
                        holder.itemView.setBackgroundColor((position & 1) == 0 ? color() : color1());
                        break;
                    case 1:
                        Log.d("RecyclerView", getName() + " loading");
                        ((TextView) holder.itemView).setText("Loading...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                count += 10;
                                notifyDataSetChanged();
                            }
                        }, 2000);
                        break;
                }
            }

            @Override
            public int getItemCount() {
                return count;
            }

            @Override
            public int getItemViewType(int position) {
                if (position == getItemCount() - 1) return 1;
                return 0;
            }
        });
        return rootView;
    }

    protected abstract String getName();

    abstract public int color();
    abstract public int color1();

    public void setRecycledViewPool(RecyclerView.RecycledViewPool pool) {
        this.pool = pool;
    }
}
