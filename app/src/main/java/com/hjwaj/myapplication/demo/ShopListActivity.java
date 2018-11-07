package com.hjwaj.myapplication.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hjwaj.myapplication.R;
import com.hjwaj.myapplication.VH;

import java.util.ArrayList;

public class ShopListActivity extends FragmentActivity {

    private RecyclerView viewPagerRV;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_shoplist);

        viewPagerRV = findViewById(R.id.view_pager_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        llm.setItemPrefetchEnabled(false);
        viewPagerRV.setLayoutManager(llm);
        new PagerSnapHelper().attachToRecyclerView(viewPagerRV);
        final Adapter adapter = new Adapter();
        viewPagerRV.setAdapter(adapter);

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = viewPagerRV.getLayoutManager().findViewByPosition(
                        ((LinearLayoutManager) viewPagerRV.getLayoutManager()).findFirstCompletelyVisibleItemPosition());
                adapter.commitFragment(view, ((LinearLayoutManager) viewPagerRV.getLayoutManager()).findFirstCompletelyVisibleItemPosition(), true);
                adapter.setPages();
            }
        });
    }

    private class Adapter extends RecyclerView.Adapter {

        private RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        ArrayList<Tab> tabs1 = new ArrayList<>();
        ArrayList<Tab> tabs2 = new ArrayList<>();

        {
            tabs1.add(new Tab(0, 0));
            tabs1.add(new Tab(0, 1));
            tabs1.add(new Tab(0, 2));

            tabs2.add(new Tab(0, 0));
            tabs2.add(new Tab(1, 0));
            tabs2.add(new Tab(2, 0));
            tabs2.add(new Tab(0, 1));
            tabs2.add(new Tab(0, 2));
        }

        private class Tab {
            int tabId;
            int tabType;
            public Tab(int id, int type) {
                tabType = type;
                tabId = id;
            }
        }

        ArrayList<Tab> tabs = tabs1;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            FrameLayout fl = new FrameLayout(parent.getContext());
            fl.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return new VH(fl);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            commitFragment(holder.itemView, position, false);
        }

        private void commitFragment(View view, int position, boolean force) {
            view.setId(position + 1);
            Tab tab = tabs.get(position);
            if (force || getSupportFragmentManager().findFragmentByTag(getFragmentTag(tab, position)) == null) {
                ListFragment f;
                if (tab.tabType == 0) {
                    f = new ShopListFragment();
                    f.setRecycledViewPool(pool);
                } else if (tab.tabType == 1) {
                    f = new ContentListFragment();
                } else {
                    f = new PeopleListFragment();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(view.getId(), f, getFragmentTag(tab, position))
                        .commitAllowingStateLoss();
            }
        }

        @Override
        public int getItemCount() {
            return tabs.size();
        }

        @Override
        public int getItemViewType(int position) {
            return tabs.get(position).tabType * 100 + tabs.get(position).tabId;
        }

        private String getFragmentTag(Tab tab, int position) {
            //return tab.tabType + "_" + tab.tabId;
            return position + "";
        }

        public void setPages() {
            if (tabs == tabs1) {
                tabs = tabs2;
                notifyItemRemoved(0);
                notifyItemRemoved(2);
                notifyItemRangeInserted(0, 3);
                notifyItemInserted(4);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        viewPagerRV.scrollToPosition(3);
                    }
                });
            } else {
                tabs = tabs1;
                notifyDataSetChanged();
            }
        }
    }
}
