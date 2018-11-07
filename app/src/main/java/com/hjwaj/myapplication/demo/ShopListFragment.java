package com.hjwaj.myapplication.demo;

import android.support.v7.widget.RecyclerView;

public class ShopListFragment extends ListFragment {
    @Override
    protected String getName() {
        return "ShopListFragment";
    }

    @Override
    public int color() {
        return 0xffffffff;
    }

    @Override
    public int color1() {
        return 0xffeeeeee;
    }
}
