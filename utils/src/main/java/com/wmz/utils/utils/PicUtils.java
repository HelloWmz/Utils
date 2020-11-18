package com.wmz.utils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by wmz on 2019/3/26.
 *
 * 图片缩放
 */

public class PicUtils {

    //使用Bitmap加Matrix来缩放
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h)
    {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(1.25f, 1.25f);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    //使用BitmapFactory.Options的inSampleSize参数来缩放
    public static Drawable resizeImage2(Context context, int path,
                                        int width, int height)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不加载bitmap到内存中
        BitmapFactory.decodeResource(context.getResources(),path,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 1;

        if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0)
        {
            int sampleSize=(outWidth/width+outHeight/height)/2;
            options.inSampleSize = sampleSize;
        }
        options.inSampleSize = 5;

        options.inJustDecodeBounds = false;
        return new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(),path,options));
    }


    public static Bitmap scale(Bitmap bmp, int  scaleWidth, int  scaleHeight) {


        Matrix matrix = new Matrix();   //矩阵，用于图片比例缩放
        matrix.postScale(10,10);    //设置高宽比例（三维矩阵）

        //缩放后的BitMap
       return  Bitmap.createBitmap(bmp, 0, 0, scaleWidth, scaleHeight, matrix, true);



    }
}




