package com.wmz.utils.utils;

/**
 * Created by pang on 2017/4/4.
 *  自定义 popupwindow 左侧弹窗
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.wmz.utils.R;

import androidx.annotation.LayoutRes;

public class LeftPopupWindows extends PopupWindow {
    private final View mView;
    private View mMenuView; // PopupWindow 菜单布局
    private Context context; // 上下文参数

    public LeftPopupWindows(Activity context, View v,@LayoutRes int resource) {
        super(context);
        this.context = context;
        this.mView=v;
        Init(resource);
    }

    private void Init(@LayoutRes int resource) {
        // TODO Auto-generated method stub
        // PopupWindow 导入
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(resource, null);



        // 导入布局
        this.setContentView(mMenuView);
        // 设置动画效果
        this.setAnimationStyle(R.style.my_dialog_anim_right);
        //防止虚拟键挡住
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        int[] location = new int[2];
        mView.getLocationOnScreen(location);
        //设置弹出窗体的 宽，高
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.MATCH_PARENT-mView.getHeight());
        // 设置可触
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
//        // 单击弹出窗以外处 关闭弹出窗
//        mMenuView.setOnTouchListener(new OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // TODO Auto-generated method stub
//                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                        setWindowAlpa(false);
//                    }
//                }
//                return true;
//            }
//        });


    }
}
