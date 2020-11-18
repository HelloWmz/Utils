package com.wmz.utils.utils;


/**
 * 用于实现RecyclerView侧滑和移动item的帮助接口
 */
public interface IItemTouchHelperAdapter {
    /**
     * 当item被移动时调用
     *
     * @param fromPosition item的起点
     * @param toPosition   item的终点
     */
    void onItemMove(int fromPosition, int toPosition);
}
