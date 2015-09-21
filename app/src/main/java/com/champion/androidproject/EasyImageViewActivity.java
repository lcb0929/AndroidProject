package com.champion.androidproject;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.champion.widget.EasyImageView;

public class EasyImageViewActivity extends AppCompatActivity {

    private EasyImageView eiv;

    private void assignViews() {
        eiv = (EasyImageView) findViewById(R.id.eiv);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_image_view);
        assignViews();

        //eiv.setImageUrl("http://f.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd56888d6d354da81cb39db3d39.jpg", EasyImageView.MODEL.NORMAL);
        eiv.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.jt), EasyImageView.MODEL.NORMAL);
    }
}
