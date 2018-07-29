package com.hjwaj.myapplication.diffutil;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PayloadCallback extends Callback {

    public static final String SHOP_NAME_CHANGED = "shopNameChanged";

    public PayloadCallback(Shop[] oldShops, Shop[] newShops) {
        super(oldShops, newShops);
    }

    @Nullable
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        if (oldItemPosition == 0 || newItemPosition == 0) {
            return null;
        }
        Map<String, Object> changeList = new HashMap<>();
        changeList.put(SHOP_NAME_CHANGED, mNewShops[newItemPosition - 1].name);
        return changeList;
    }
}
