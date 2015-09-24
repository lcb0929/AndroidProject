package com.champion.androidproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.champion.utils.SmartAdapter;
import com.champion.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RefreshListViewActivity extends AppCompatActivity {

    private ListView rv;
    private List<String> listData;
    private MyAdapter myAdapter;

    private void assignViews() {
        rv = (ListView) findViewById(R.id.rv);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list_view);
        assignViews();

        initData();
        myAdapter = new MyAdapter(this,listData, R.layout.refreshlist_item);

        rv.setAdapter(myAdapter);

    }

    private void initData() {
        listData = new ArrayList<String>();
        for (int i = 1; i <= 30; i++) {
            listData.add(String.format("这是默认生成的第%d条数据",i));
        }
    }

    class MyAdapter extends SmartAdapter<String>{

        public MyAdapter(Context context, List<String> listData, int layoutRes) {
            super(context, listData, layoutRes);
        }

        @Override
        protected void setView(ViewHolder viewHolder, String item) {
            viewHolder.setTextToTextView(R.id.tv_item,item);
        }
    }

}
