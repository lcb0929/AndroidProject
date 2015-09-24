package com.champion.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 成彬 on 2015/9/24.
 */
public class ViewHolder {

    private static Context mContext;
    private static int mPosition;


    private  View mConvertView;
    private  SparseArray<View> mViewList;

    private ViewHolder(Context context, ViewGroup parent, int position, int layoutRes) {
        this.mPosition = position;
        this.mContext = context;
        mViewList = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
        mConvertView.setTag(this);
    }

    /**
     * 获取ViewHolder对象
     *
     * @param context     上下文
     * @param convertView convertView
     * @param parent      ViewGroup
     * @param position    当前位置
     * @param layoutRes   布局ID
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int position, int layoutRes) {
        if (null == convertView) {
            return new ViewHolder(context, parent, position, layoutRes);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    /**
     * 通过ID获取控件
     *
     * @param resID 控件ID
     * @param <T>   控件类型
     * @return 返回控件
     */
    public <T extends View> T GetView(int resID) {
        View view = mViewList.get(resID);
        if (null == view) {
            view = mConvertView.findViewById(resID);
            mViewList.put(resID, view);
        }
        return (T) view;
    }

    /**
     * 为ViewHolder中的TextView设置text值
     *
     * @param viewID TextView的ID
     * @param text   text值
     * @return 返回ViewHolder, 链式编程
     */
    public ViewHolder setTextToTextView(int viewID, String text) {
        TextView tv = GetView(viewID);
        tv.setText(text);
        return this;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getPosition() {
        return mPosition;
    }

}
