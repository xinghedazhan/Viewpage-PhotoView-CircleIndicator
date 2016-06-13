package com.mooc.viewpage_photoview_circleindicator;

import android.graphics.Bitmap;

/**
 * Created by 24706 on 2016/5/12.
 * 监听图片加载过程
 */
public interface LoadingImageListener {
     void onLoadStart();

     void onLoadFailure();

     void onLoadSuccess(Bitmap bitmap);
}
