package com.champion.androidproject;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.champion.widget.EasyImageView;

public class EasyImageViewActivity extends AppCompatActivity {

    private static String url = "http://f.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd56888d6d354da81cb39db3d39.jpg";
    private Button btnLocalNormal;
    private Button btnLocalCircular;
    private Button btnNetNormal;
    private Button btnNetCircular;
    private EasyImageView eiv;
    private TextView tvModel;
    private TextView tvUrl;

    private void assignViews() {
        btnLocalNormal = (Button) findViewById(R.id.btnLocalNormal);
        btnLocalCircular = (Button) findViewById(R.id.btnLocalCircular);
        btnNetNormal = (Button) findViewById(R.id.btnNetNormal);
        btnNetCircular = (Button) findViewById(R.id.btnNetCircular);
        eiv = (EasyImageView) findViewById(R.id.eiv);
        tvModel = (TextView) findViewById(R.id.tv_model);
        tvUrl = (TextView) findViewById(R.id.tv_url);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_image_view);
        assignViews();

        btnLocalCircular.setOnClickListener(new MyOnClickListener());
        btnLocalNormal.setOnClickListener(new MyOnClickListener());
        btnNetCircular.setOnClickListener(new MyOnClickListener());
        btnNetNormal.setOnClickListener(new MyOnClickListener());

        eiv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.jt), EasyImageView.MODEL.NORMAL);
        tvModel.setText("当前模式：" + btnLocalNormal.getText().toString());
        tvModel.setVisibility(View.VISIBLE);
        tvUrl.setVisibility(View.GONE);
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnLocalCircular:
                    eiv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.jt), EasyImageView.MODEL.CIRCULAR);
                    tvModel.setText("当前模式：" + btnLocalCircular.getText().toString());
                    tvModel.setVisibility(View.VISIBLE);
                    tvUrl.setVisibility(View.GONE);
                    break;
                case R.id.btnLocalNormal:
                    eiv.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.jt), EasyImageView.MODEL.NORMAL);
                    tvModel.setText("当前模式：" + btnLocalNormal.getText().toString());
                    tvModel.setVisibility(View.VISIBLE);
                    tvUrl.setVisibility(View.GONE);
                    break;
                case R.id.btnNetCircular:
                    eiv.setImageUrl(url, EasyImageView.MODEL.CIRCULAR);
                    tvModel.setText("当前模式：" + btnNetCircular.getText().toString());
                    tvUrl.setText("当前URL："+url);
                    tvModel.setVisibility(View.VISIBLE);
                    tvUrl.setVisibility(View.VISIBLE);
                    break;
                case R.id.btnNetNormal:
                    eiv.setImageUrl(url, EasyImageView.MODEL.NORMAL);
                    tvModel.setText("当前模式：" + btnNetNormal.getText().toString());
                    tvUrl.setText("当前URL："+url);
                    tvModel.setVisibility(View.VISIBLE);
                    tvUrl.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
