package com.champion.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.champion.common.ImageLoader;

/**
 * Created by 成彬 on 2015/9/21.
 */
public class EasyImageView extends ImageView {


    private MODEL mModel = MODEL.NORMAL;    //图片显示模式
    private ImageLoader mImageLoader;       //图片加载器

    public EasyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mImageLoader = ImageLoader.getInstance();
    }

    /**
     * 图片显示模式枚举
     */
    public enum MODEL{
        CIRCULAR,
        NORMAL
    }

    /**
     * 使用网络URL设置图片
     * @param urlString 网络图片路径
     * @param model     模式
     */
    public void setImageUrl(final String urlString,MODEL model){
        mModel = model;
        new getBitmapFromUrlAsync().execute(urlString);
    }

    /**
     * 根据模式设置图片
     * @param bitmap    传入的图片
     * @param model     模式
     */
    public void setImageBitmap(Bitmap bitmap , MODEL model){
        mModel = model;
        setImage(bitmap);
    }

    /**
     * 在原有的图片上画圆
     * @param bitmap    用于展示的图片
     * @return          添加了圆圈的图片
     */
    private Bitmap toRoundBitmap(Bitmap bitmap) {
        //圆形图片宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //正方形的边长
        int r = 0;
        //取最短边做边长
        if(width > height) {
            r = height;
        } else {
            r = width;
        }
        //构建一个bitmap
        Bitmap backgroundBmp = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        //new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBmp);
        Paint paint = new Paint();
        //设置边缘光滑，去掉锯齿
        paint.setAntiAlias(true);
        //宽高相等，即正方形
        RectF rect = new RectF(0, 0, r, r);
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        //且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r/2, r/2, paint);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, paint);
        //返回已经绘画好的backgroundBmp
        return backgroundBmp;
    }


    /**
     * 根据模式设置图片
     * @param bitmap    需要设置的图片
     */
    private void setImage(Bitmap bitmap){
        if(null != bitmap){
            if(mModel == MODEL.CIRCULAR){
                bitmap = toRoundBitmap(bitmap);
            }
            setImageBitmap(bitmap);
        }else{
            return;
        }
    }

    /**
     * 异步任务类
     */
    private class getBitmapFromUrlAsync extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            String urlString = params[0];
            Bitmap bitmap = ImageLoader.getInstance().GetBitmapFromCache(urlString);
            if(null == bitmap)
                bitmap = ImageLoader.getInstance().GetBitmapFromUrl(urlString);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            setImage(bitmap);
        }
    }


}
