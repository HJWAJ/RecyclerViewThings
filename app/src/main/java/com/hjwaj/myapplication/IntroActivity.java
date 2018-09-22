package com.hjwaj.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class IntroActivity extends Activity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_intro);
        findViewById(R.id.btn_prefetch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("recyclerviewthings://prefetch")));
            }
        });
        findViewById(R.id.btn_prefetch_nested).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("recyclerviewthings://prefetch_nested")));
            }
        });
        findViewById(R.id.btn_page_snap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("recyclerviewthings://pagesnap")));
            }
        });
        findViewById(R.id.btn_linear_snap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("recyclerviewthings://linearsnap")));
            }
        });
        findViewById(R.id.btn_diff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("recyclerviewthings://diff")));
            }
        });
        findViewById(R.id.btn_partial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("recyclerviewthings://partial")));
            }
        });
        findViewById(R.id.btn_nested_stagger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("recyclerviewthings://nestedstagger")));
            }
        });
    }
}
