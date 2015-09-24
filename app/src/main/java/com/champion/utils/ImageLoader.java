package com.champion.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 成彬 on 2015/9/21.
 */
public class ImageLoader {


    private static ImageLoader mInstance;   //默认实例
    private static LruCache<String, Bitmap> mImageCache;

    private ImageLoader() {
        //获取应用的最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        //设置缓存内存
        int cacheSize = maxMemory / 4;
        //初始化Lru缓存
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //设置每次存入的缓存大小
                return value.getByteCount();
            }
        };
    }

    /**
     * 将图片加载到缓存中
     *
     * @param urlString 缓存键
     * @param bitmap    缓存值
     */
    public static void AddImageViewToCache(String urlString, Bitmap bitmap) {
        if (null == mImageCache.get(urlString))
            mImageCache.put(urlString, bitmap);
    }

    /**
     * 从缓存中获取图片
     *
     * @param urlString 缓存键
     * @return 缓存值
     */
    public static Bitmap GetBitmapFromCache(String urlString) {
        return mImageCache.get(urlString);
    }


    /**
     * 使用单例模式获取实例对象
     *
     * @return ImageLoader对象
     */
    public static ImageLoader getInstance() {
        if (null == mInstance) {
            synchronized (ImageLoader.class) {
                if (null == mInstance) {
                    mInstance = new ImageLoader();
                }
            }
        }
        return mInstance;
    }

    /**
     * 通过Url将网络图片转换成Bitmap对象,注意使用多线程调用
     *
     * @param urlString 图片路径
     * @return Bitmap对象
     */
    public static Bitmap GetBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL mUrl = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
            conn.connect();
            is = new BufferedInputStream(conn.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            conn.disconnect();
            AddImageViewToCache(urlString, bitmap);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
