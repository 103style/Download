package com.hnpolice.xiaoke.downloadfile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hnpolice.xiaoke.downloadfile.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.single, R.id.multith})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.single:
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                break;
            case R.id.multith:
                startActivity(new Intent(StartActivity.this, MainActivity2.class));
                break;
        }
    }


}
