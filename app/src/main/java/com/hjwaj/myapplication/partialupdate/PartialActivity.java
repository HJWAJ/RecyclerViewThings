package com.hjwaj.myapplication.partialupdate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjwaj.myapplication.R;
import com.hjwaj.myapplication.VH;

public class PartialActivity extends Activity {

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
            private String oldStr = "AZYXBCDWEF";
//            private String oldStr = "AZYXBCDWEFAZYXBCDWEFAZYXBCDWEF";
            private String newStr = "VBCQRSFAEDTU";

            private String str = oldStr;

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView tv = new TextView(parent.getContext());
                tv.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
                tv.setTextSize(20);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(0xff111111);
                tv.setBackgroundColor(0xffffffff);
                return new VH(tv);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                int viewType = getItemViewType(position);
                switch (viewType) {
                    case 0:
                        Log.d("rv", "" + str.charAt(position - 1));
                        ((TextView) holder.itemView).setText("" + str.charAt(position - 1));
                        holder.itemView.setBackgroundColor((position & 1) == 0 ? 0xffffffff : 0xffeeeeee);
                        break;
                    case 1:
                        ((TextView) holder.itemView).setText("Change");
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 先remove和move，再更新数据，再insert，是ok的
                                // move不会走onBindViewHolder
                                notifyItemRangeRemoved(2, 3);
                                notifyItemRangeRemoved(5, 1);
                                notifyItemMoved(2, 1);
                                notifyItemMoved(3, 2);
                                notifyItemMoved(6, 3);
                                notifyItemMoved(6, 5);
                                str = newStr;
                                notifyItemInserted(1);
                                notifyItemRangeInserted(4, 3);
                                notifyItemRangeInserted(11, 2);
                            }
                        });
                        break;
                }
            }

            @Override
            public int getItemCount() {
                return str.length() + 1;
            }

            @Override
            public int getItemViewType(int position) {
                if (position == 0) return 1;
                return 0;
            }
        });
    }
}
