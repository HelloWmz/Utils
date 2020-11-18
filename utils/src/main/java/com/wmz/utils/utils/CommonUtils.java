package com.wmz.utils.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import androidx.annotation.ColorRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Admin on 2018/5/11.
 * 蓝牙
 * https://juejin.im/post/5cac76e35188251afb4a897e
 */

public class CommonUtils {

    private static final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    /**
     * 跳转activity
     *
     * @param context
     * @param classs
     */
    public static void startActivity(Context context, Class<?> classs) {
        Intent intent = new Intent();
        intent.setClass(context, classs);
        context.startActivity(intent);
    }

    /**
     * dp2px
     *
     * @param context
     * @param valueInDp
     * @return
     */
    public static float convertDPToPixel(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);

    }


    /**
     * 通用 设置 RecylerView 使用 LinearLayoutManager 布局管理者 不需要分割线
     *
     * @param mActivity
     * @param rv
     */
    public static void setBaseRecylerView(RecyclerView.LayoutManager layoutManager, Context mActivity, RecyclerView rv) {
        if (layoutManager == null) {
            rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        } else {
            rv.setLayoutManager(layoutManager);
        }
    }

    /**
     * 计算渐变后的颜色
     *
     * @param startColor 开始颜色
     * @param endColor   结束颜色
     * @param rate       渐变率（0,1）
     * @return 渐变后的颜色，当rate=0时，返回startColor，当rate=1时返回endColor
     */
    public static int computeGradientColor(int startColor, int endColor, float rate) {
        if (rate < 0) {
            rate = 0;
        }
        if (rate > 1) {
            rate = 1;
        }

        int alpha = Color.alpha(endColor) - Color.alpha(startColor);
        int red = Color.red(endColor) - Color.red(startColor);
        int green = Color.green(endColor) - Color.green(startColor);
        int blue = Color.blue(endColor) - Color.blue(startColor);

        return Color.argb(
                Math.round(Color.alpha(startColor) + alpha * rate),
                Math.round(Color.red(startColor) + red * rate),
                Math.round(Color.green(startColor) + green * rate),
                Math.round(Color.blue(startColor) + blue * rate));
    }

    /**
     * 完整的判断中文汉字和符号
     *
     * @param strName
     * @return
     */

    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 判断微信是否可用
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断qq是否可用
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断微博是否可用
     */
    public static boolean isWeiboAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.sina.weibo")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen.xml");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 计算指定的 View 在屏幕中的坐标。
     */
    public static RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }

    /**
     * 判断触摸点是否在控件内
     */
    public static boolean isInViewRange(View view, MotionEvent event) {

        // MotionEvent event;
        // event.getX(); 获取相对于控件自身左上角的 x 坐标值
        // event.getY(); 获取相对于控件自身左上角的 y 坐标值
        float x = event.getRawX(); // 获取相对于屏幕左上角的 x 坐标值
        float y = event.getRawY(); // 获取相对于屏幕左上角的 y 坐标值

        // View view;
        RectF rect = calcViewScreenLocation(view);
        return rect.contains(x, y);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取网络视频的第一帧图片
     *
     * @param videoUrl
     * @return
     */
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    /**
     * 将String转为集合
     *
     * @param data
     * @return
     */

    public static List<String> string12List(String data) {
        List<String> dataList = new ArrayList<>();
        if (data != null) {
            String[] split = data.split(",");
            for (int i = 0; i < split.length; i++) {
                if (!TextUtils.isEmpty(split[i])) {
                    dataList.add(split[i]);
                }
            }
        }
        return dataList;
    }

    /**
     * 将集合转为list
     *
     * @param dataList
     * @return
     */
    public static String list2String(List<String> dataList) {
        if (dataList.size() <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        if (dataList != null) {
            for (int i = 0; i < dataList.size(); i++) {
                if (i != 0) {
                    builder.append(",");
                }
                builder.append(dataList.get(i));
            }
        }
        return builder.toString();
    }

    /**
     * 获取资源下的颜色
     *
     * @param context
     * @param id
     * @return
     */
    public static int getColor(Context context, @ColorRes int id) {
        return ContextCompat.getColor(context, id);

    }

    /**
     * 获取资源下的Drawable
     *
     * @param context
     * @param id
     * @return
     */
    public static Drawable getDrawable(Context context, @ColorRes int id) {
        return ContextCompat.getDrawable(context, id);

    }

    /**
     * 检查权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean checkPermission(Context context, String[] permissions) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        for (String permission : permissions) {
            int per = packageManager.checkPermission(permission, packageName);
            if (PackageManager.PERMISSION_DENIED == per) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取本地视频第一帧图片
     *
     * @param path
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static int createVideoDuration(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        mmr.release();
        return Integer.parseInt(duration);
    }

    /**
     * 设置textView部分颜色
     *
     * @param text
     * @param view
     * @param index
     */
    public static void setTextMidColor(Context context, String text, TextView view, int index, int color) {
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(color));
        spannableString.setSpan(colorSpan, index, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        view.setText(spannableString);
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取打开高德地图应用uri
     * style
     * 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5
     * 不走高速且避免收费；6 不走高速且躲避拥堵；
     * 7 躲避收费和拥堵；8 不走高速躲避收费和拥堵)
     */
    public static String getGdMapUri(String appName, String dlat, String dlon, String dname) {
        String newUri = "androidamap://navi?sourceApplication=%1$s&poiname=%2$s&lat=%3$s&lon=%4$s&dev=1&style=2";
        return String.format(newUri, appName, dname, dlat, dlon);
    }

    /**
     * 判断是否多次点击
     */
    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
            lastClickTime = 0;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    /**
     * 打开软键盘
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 检查手机号
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        Pattern phonenumber = Pattern
                .compile("^[1][3-9][0-9]{9}$");
        return phonenumber.matcher(phone.trim()).matches();
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return null == str || str.length() <= 0;
    }

    /**
     * 将字符串换成Bitmap类型
     *
     * @param string
     * @return
     */
    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 请求网络图片
     *
     * @param url
     * @return
     */

    public static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 解决4.0以后获取手机url path路径的问题
     *
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * 设置textViewDrawable
     *
     * @param context
     * @param isCollected         是否选择
     * @param view
     * @param CollectedColor
     * @param NOCollectedColor
     * @param CollectedDrawable
     * @param NOCollectedDrawable
     */
    public void setTextViewDrawable(Context context, boolean isCollected, TextView view, int CollectedColor, int NOCollectedColor,
                                    int CollectedDrawable, int NOCollectedDrawable) {

        view.setText(isCollected ? "已收藏" : "收藏");
        Drawable img_off = getDrawable(context, isCollected ? CollectedDrawable : NOCollectedDrawable);
        view.setTextColor(getColor(context, isCollected ? CollectedColor : NOCollectedColor));
        view.setCompoundDrawablesWithIntrinsicBounds(null, img_off, null, null); // 设置左图标
        view.setCompoundDrawablePadding(20);

    }

    /**
     * Bundle bundle = getIntent().getExtras();
     * 带参数跳转界面
     */
    protected void startActivity(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * md5加密
     *
     * @param text
     * @return
     */
    public static String md5(String text) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(text.getBytes());
            byte bytes[] = digest.digest();

            // Create Hex String
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xFF & b);
                while (hex.length() < 2) {
                    hex = "0" + hex;
                }
                stringBuilder.append(hex);
            }

            return stringBuilder.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    /**
     * 判断是否有读写权利
     *
     * @param activity
     * @return
     */
    public static boolean verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 讲bitmap 保存在相册中
     *
     * @param bmp 获取的bitmap数据
     */
    public static void saveBmp2Gallery(Activity context, Bitmap bmp) {
        if (verifyStoragePermissions(context)) {
            String picName = String.valueOf(System.currentTimeMillis());
            String fileName = null;
            //系统相册目录
            String galleryPath = Environment.getExternalStorageDirectory()
                    + File.separator + Environment.DIRECTORY_DCIM
                    + File.separator + "Camera" + File.separator;


            // 声明文件对象
            File file = null;
            // 声明输出流
            FileOutputStream outStream = null;

            try {
                // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
                file = new File(galleryPath, picName + ".jpg");

                // 获得文件相对路径
                fileName = file.toString();
                // 获得输出流，如果文件中有内容，追加内容
                outStream = new FileOutputStream(fileName);
                if (null != outStream) {
                    bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
                }

            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                try {
                    if (outStream != null) {
                        outStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    bmp, fileName, null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
        }
    }

    /**
     * 生成唯一标识符UUID可变
     */
    public static String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成唯一标识符UUID不可变
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getMyUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        tmPhone = tm.getLine1Number();
        androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        Log.d("debug", "uuid=" + uniqueId + ",tmPhone=" + tmPhone);
        return uniqueId;
    }

    /**
     * 获取Bitmap的大小
     *
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }


    /**
     * @param object
     * @param text   "0.00"  保留两位
     * @return
     */
    public static String getDecimalFormat(Object object, String text) {
        DecimalFormat df = new DecimalFormat(text);
        return df.format(object);
    }

    /** 四舍五入
     * @param num
     * @param newScale 2  保留两位
     * @return
     */
    public static String getBigDecimal(Double num, int newScale) {
        BigDecimal bd = new BigDecimal(num);
        bd.setScale(newScale, BigDecimal.ROUND_HALF_UP);
        return String.valueOf(bd);
    }


    /*public static void startBaiDuMapApp(Context context,LatLng stLatLng, LatLng enLatLng) {
        try {
            String start_latlng = stLatLng.latitude + "," + stLatLng.longitude;
            String end_latlng = enLatLng.latitude + "," + enLatLng.longitude;
            Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:"+start_latlng+"|name:"+"起点"+"&destination=latlng:"+end_latlng+"|name:"+"终点"+"&mode=riding&src=这里随便写#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "地址解析错误", Toast.LENGTH_SHORT).show();
        }
    }

    public static void startGaoDeApp(Context context,LatLng stLatLng,LatLng enLatLng) {
        String uriString = null;
        //LatLng stLatLng_gd = bd_decrypt(stLatLng);
        StringBuilder builder = new StringBuilder("amapuri://route/plan?sourceApplication=maxuslife");
        builder.append("&sname=").append("起点")
                .append("&slat=").append(stLatLng.latitude)
                .append("&slon=").append(stLatLng.longitude)
                .append("&dlat=").append(enLatLng.latitude)
                .append("&dlon=").append(enLatLng.longitude)
                .append("&dname=").append("终点")
                .append("&dev=0")
                .append("&t=0");
        uriString = builder.toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.autonavi.minimap");
        intent.setData(Uri.parse(uriString));
        context.startActivity(intent);
    }*/
    /**
     * 判断intent 是否存在  true  存在
     */
   public static boolean isIntentAvailable(Context context, Intent intent){
       PackageManager packageManager = context.getPackageManager();
       List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
       return resolveInfos.size() > 0;

   }

    /**
     * 启动一个inten的时候 判断是否存在
     * 判断intent 是否存在  true  存在
     * @param context
     * @param intent
     * @return
     */
    public static boolean isIntentAvailable(Intent intent, Context context){
        PackageManager packageManager = context.getPackageManager();
        return intent.resolveActivity(packageManager) != null;

    }

    /**
     * 判断intent 是否可用 没有去应用市场去搜索
     * @param intent
     * @param context
     * https://blog.csdn.net/growing_tree/article/details/39099621
     */
    public static void isIntentAvailableOrQuery(Intent intent, Context context){
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        //如果有
       if (intent.resolveActivity(packageManager) != null){
           context.startActivity(intent);
       }else{
           Uri marketUri = Uri.parse("market://serach?q=pname:"+packageName);
           Intent markeIntent = new Intent(Intent.ACTION_VIEW).setData(marketUri);
           if (markeIntent.resolveActivity(packageManager) != null){
               context.startActivity(markeIntent);
           }else {
               Toast.makeText(context,"没有可用的应用", Toast.LENGTH_LONG).show();
           }
       }
    }
}
