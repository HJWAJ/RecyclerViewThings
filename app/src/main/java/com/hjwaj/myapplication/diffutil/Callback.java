package com.hjwaj.myapplication.diffutil;

import android.support.v7.util.DiffUtil;

public class Callback extends DiffUtil.Callback {

    protected final Shop[] mOldShops;
    protected final Shop[] mNewShops;


    public Callback(Shop[] oldShops, Shop[] newShops) {
        mOldShops = oldShops;
        mNewShops = newShops;
    }

    @Override
    public int getOldListSize() {
        return mOldShops.length + 1;
    }

    @Override
    public int getNewListSize() {
        return mNewShops.length + 1;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        if (oldItemPosition == 0 && newItemPosition == 0) {
            return true;
        }
        if (oldItemPosition == 0 || newItemPosition == 0) {
            return false;
        }
        return mOldShops[oldItemPosition - 1].id == mNewShops[newItemPosition - 1].id;

//        return mOldShops[oldItemPosition - 1].id == mNewShops[newItemPosition - 1].id
//                && (oldItemPosition & 1) == (newItemPosition & 1);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (oldItemPosition == 0 && newItemPosition == 0) {
            return true;
        }
        if (oldItemPosition == 0 || newItemPosition == 0) {
            return false;
        }
        return mOldShops[oldItemPosition - 1].id == mNewShops[newItemPosition - 1].id
                && mOldShops[oldItemPosition - 1].name.equals(mNewShops[newItemPosition - 1].name);
//        return mOldShops[oldItemPosition - 1].id == mNewShops[newItemPosition - 1].id
//                && mOldShops[oldItemPosition - 1].name.equals(mNewShops[newItemPosition - 1].name)
//                && (oldItemPosition & 1) == (newItemPosition & 1);
    }
}
