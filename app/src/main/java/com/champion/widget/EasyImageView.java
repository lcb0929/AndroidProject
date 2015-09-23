package com.champion.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.champion.common.ImageLoader;

import java.lang.reflect.Field;

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

        //获取ImageView的宽高
        ImageSize size = GetImageViewSize(this);
        int width = size.width;
        int height = size.height;
        //正方形的边长
        int r = 0;
        //取最短边做边长
        if(width > height) {
            r = height;
        } else {
            r = width;
        }

        //构建一个bitmap,将宽高设置为ImageView的宽高
        Bitmap backgroundBmp = Bitmap.createBitmap(r,
                r, Bitmap.Config.ARGB_8888);
        //new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBmp);
        Paint paint = new Paint();
        //设置边缘光滑，去掉锯齿
        paint.setAntiAlias(true);
        //宽高相等，即正方形
        RectF rect = new RectF(0,0,r,r);
        //通过制定的rect画一个圆,起始角度为0度.结束角度为360度.
        canvas.drawArc(rect, 0, 360, false, paint);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        //canvas.drawBitmap(bitmap,100,100,paint);
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

    /**
     * 获取ImageView的宽高
     * @param imageView ImageView对象
     * @return          返回一个ImageSize对象
     */
    private ImageSize GetImageViewSize(ImageView imageView) {

        ImageSize imageSize = new ImageSize();
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();

        /**
         * 获取ImageView的宽度
         */
        int width =  imageView.getWidth();
        if(width <= 0){
            //获取imageview在layout中声明的宽度,因为有可能为wrap_content等,所以会小于零
            width = lp.width;
        }
        if(width <= 0){
            //检查imageview的宽度最大值,也有可能会小于零
            width = GetImageViewFieldValue(ImageView.class,imageView,"mMaxWidth");
        }
        if(width <= 0){
            //最差的情况,获取屏幕宽度
            width = displayMetrics.widthPixels;
        }

        /**
         * 获取ImageView的高度
         */
        int height =  imageView.getHeight();
        if(height <= 0){
            height = lp.height;
        }
        if(height <= 0){
            height = GetImageViewFieldValue(ImageView.class, imageView, "mMaxHeight");
        }
        if(width <= 0){
            height = displayMetrics.heightPixels;
        }

        imageSize.width = width;
        imageSize.height = height;

        return imageSize;
    }


    /**
     * 内部类:ImageView的宽高
     */
    private class ImageSize{
        int width;
        int height;
    }

    /**
     * 通过反射获取对象的某个属性值
     * @param object
     * @param fieldName
     * @return
     */
    private static int GetImageViewFieldValue(Class widget,Object object,String fieldName){
        int value  = 0;

        try {
            Field field = widget.getDeclaredField(fieldName);
            field.setAccessible(true);

            int fieldValue = field.getInt(object);

            if(fieldValue > 0 && fieldValue < Integer.MAX_VALUE){
                value = fieldValue;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }


}
