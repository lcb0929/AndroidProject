package com.champion.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by ChampionLam on 2015/9/17.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener {


    private MODEL mCurrModel = MODEL.NORMAL;    //当前模式
    private View mHeaderView;   //当前头布局
    private View mFooterView;   //当前脚布局

    /**
     * 模式枚举
     */
    public enum MODEL{
        NORMAL,
        LOADINGMORE,
        REFRESH,
        BOTH
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);



    }


    /**
     * 设置模式
     * @param model
     * MODEL.NORMAL         -> 默认模式,不采用下拉刷新和加载更多的功能
     * MODEL.LOADINGMORE    -> 下拉刷新默认,采用下拉刷新功能
     * MODEL.REFRESH        -> 加载更多模式,采用加载更多功能
     * MODEL.BOTH           -> 全开模式,采用加载更多和下拉刷新功能
     */
    public void setModel(MODEL model){
        mCurrModel = model;
    }

    /**
     * 初始化头布局完成下拉刷新
     */
    private void initHeaderView(){
        //TODO 初始化头布局
    }

    /**
     * 初始化脚布局完成加载更多
     */
    private void initFooterView(){
        //TODO 初始化脚布局
    }

    /**
     * 删除头布局
     */
    public void removeHeaderView(){
        if(null != mHeaderView)
            this.removeHeaderView(mHeaderView);
    }

    /**
     * 删除脚布局
     */
    public void removeFooterView(){
        if(null != mFooterView)
            this.removeFooterView(mFooterView);
    }



    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }



}
