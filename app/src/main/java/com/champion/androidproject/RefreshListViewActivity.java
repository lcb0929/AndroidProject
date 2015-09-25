package com.champion.androidproject;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.champion.utils.SmartAdapter;
import com.champion.utils.ViewHolder;
import com.champion.widget.RefreshListView;

import java.util.ArrayList;
import java.util.List;

public class RefreshListViewActivity extends AppCompatActivity {

    private RefreshListView rv;
    private List<String> listData;
    private MyAdapter myAdapter;

    private void assignViews() {
        rv = (RefreshListView) findViewById(R.id.rv);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list_view);
        assignViews();

        initData();
        initEvent();
        rv.setModel(RefreshListView.MODEL.NORMAL);
        myAdapter = new MyAdapter(this,listData, R.layout.refreshlist_item);

        rv.setAdapter(myAdapter);

    }

    /**
     * 设置刷新回调事件
     */
    private void initEvent() {
        rv.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void OnRefreshing() {
                new MyAsyncTask().execute();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        listData = new ArrayList<String>();
        for (int i = 1; i <= 30; i++) {
            listData.add(String.format("这是默认生成的第%d条数据",i));
        }
    }

    /**
     * 适配器
     */
    class MyAdapter extends SmartAdapter<String>{

        public MyAdapter(Context context, List<String> listData, int layoutRes) {
            super(context, listData, layoutRes);
        }

        @Override
        protected void setView(ViewHolder viewHolder, String item) {
            viewHolder.setTextToTextView(R.id.tv_item,item);
        }
    }

    /**
     * 异步任务
     */
    class MyAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            listData.add(0, "这是刷新的数据");
            try {
                //模拟刷新进度效果,线程停止两秒
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            myAdapter.notifyDataSetChanged();
            rv.hideHeaderView();    //刷新成功,隐藏头布局
        }
    }

}
