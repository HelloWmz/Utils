package com.wmz.utils.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @ProjectName AndroidUtils
 * @CreateDate: 2020/8/28
 * @Author: wmz
 * @Description:
 */
public class CommomBena implements MultiItemEntity {
   public String name;
   public  int type;
    @Override
    public int getItemType() {
        return type;
    }

    public CommomBena(String name, int type) {
        this.name = name;
        this.type = type;
    }
}
