package com.hjwaj.myapplication.diffutil;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjwaj.myapplication.R;
import com.hjwaj.myapplication.VH;

import java.util.List;
import java.util.Map;

public class DiffActivity extends Activity {

    private Adapter mAdapter;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_with_simple_recycler_view);
        RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        mAdapter = new Adapter();
        rv.setAdapter(mAdapter);
    }

    private class Adapter extends RecyclerView.Adapter<VH> {
        private Shop[] mShops;

        public Adapter() {
            mShops = mOldShops;
        }

        public void updateShops(Shop[] shops) {
            mShops = shops;
        }

        public Shop[] getShops() {
            return mShops;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView tv = new TextView(parent.getContext());
            tv.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
            tv.setTextSize(20);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            tv.setPadding(30, 0, 0, 0);
            tv.setTextColor(0xff111111);
            tv.setBackgroundColor(0xffffffff);
            return new VH(tv);
        }

        @Override
        public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
            if (payloads.isEmpty()) {
                onBindViewHolder(holder, position);
                return;
            }
            Map<String, Object> payload = (Map<String, Object>) payloads.get(0);
            if (payload.containsKey(PayloadCallback.SHOP_NAME_CHANGED)) {
                ((TextView) holder.itemView).setText((CharSequence) payload.get(PayloadCallback.SHOP_NAME_CHANGED));
            }
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            if (position == 0) {
                ((TextView) holder.itemView).setText("Refresh List");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        traditionalRefreshShops();
                        refreshShops();
                    }
                });
            } else {
                ((TextView) holder.itemView).setText(mShops[position - 1].name);
            }
            holder.itemView.setBackgroundColor((position & 1) == 0 ? 0xffffffff : 0xffeeeeee);
        }

        @Override
        public int getItemCount() {
            return mShops == null ? 1 : mShops.length + 1;
        }
    }

    private void traditionalRefreshShops() {
        mAdapter.updateShops(mAdapter.getShops() == mOldShops ? mNewShops : mOldShops);
        mAdapter.notifyDataSetChanged();
    }

    private void refreshShops() {
        Shop[] oldShops = mAdapter.getShops();
        Shop[] newShops = mAdapter.getShops() == mOldShops ? mNewShops : mOldShops;
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new Callback(oldShops, newShops), true);
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new PayloadCallback(oldShops, newShops), true);
        diffResult.dispatchUpdatesTo(mAdapter);
        mAdapter.updateShops(newShops);
    }

    private Shop[] mOldShops = new Shop[] {
            new Shop(0, "TPlus"),
            new Shop(1, "Meat Plus"),
            new Shop(2, "Home Garden"),
            new Shop(3, "沙县小吃"),
            new Shop(4, "杨国福麻辣烫"),
            new Shop(5, "兰州拉面"),
            new Shop(6, "祥和面馆"),
            new Shop(7, "永祥烧腊茶餐厅"),
            new Shop(8, "黄焖鸡米饭"),
            new Shop(9, "麻辣香锅"),
            new Shop(10, "晓燕生煎")
    };

    private Shop[] mNewShops = new Shop[] {
            new Shop(0, "TPlus"),
            new Shop(2, "Home Garden"),
            new Shop(1, "Meat Plus"),
            new Shop(11, "另一家沙县小吃"),
//            new Shop(3, "另一家沙县小吃"),
            new Shop(12, "另一家麻辣烫"),
//            new Shop(4, "另一家麻辣烫"),
            new Shop(13, "新开的小吃店"),
            new Shop(5, "兰州拉面"),
            new Shop(6, "祥和面馆"),
            new Shop(7, "永祥烧腊茶餐厅"),
            new Shop(8, "黄焖鸡米饭")
    };
}
