package com.champion.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnGoRefreshListView;
    private Button btnGoEasyImageView;

    private void assignViews() {
        btnGoRefreshListView = (Button) findViewById(R.id.btnGoRefreshListView);
        btnGoEasyImageView = (Button) findViewById(R.id.btnGoEasyImageView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        InitEvent();
    }

    private void InitEvent() {
        btnGoEasyImageView.setOnClickListener(new MyOnClickListener());
        btnGoRefreshListView.setOnClickListener(new MyOnClickListener());
    }


    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnGoEasyImageView:
                    startActivity(new Intent(MainActivity.this,EasyImageViewActivity.class));
                    break;
                case R.id.btnGoRefreshListView:
                    startActivity(new Intent(MainActivity.this,RefreshListViewActivity.class));
                    break;
            }
        }
    }

}
