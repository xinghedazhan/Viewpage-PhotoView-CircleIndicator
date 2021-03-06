package com.mooc.viewpage_photoview_circleindicator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by xinghe on 16/6/13.
 * Glide图片加载的工具类
 */
public class GlideImageLoader {
    //设置加载时自定义的图片
    private  int DEFAULT_IMAGE = R.mipmap.ic_launcher;
    //设置加载错误时自定义的的图片
    private  int ERROR_IMAGE = R.mipmap.ic_launcher;

    //静态内部类单例模式    不仅能够保证线程安全，也能够保证单例对象的唯一性，同时也延迟了单例的实例化
    private GlideImageLoader(){}
    public static GlideImageLoader getInstance(){
        return GlideImageLoaderInner.mInstance;
    }
    /**
     * 静态内部类
     */
    private static class GlideImageLoaderInner{
        private static final GlideImageLoader mInstance=new GlideImageLoader();
    }
    /**
     * 加载普通网络图片
     */
    public void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(DEFAULT_IMAGE)
                .error(ERROR_IMAGE)
                .centerCrop()
                //设置填充满imageview，可能有部分被裁剪掉，还有一种方式是fitCenter，将图片完整显示
                .into(imageView);
    }
//    /**
//     * 加载圆形图片
//     */
//    public  void loadCircleImage(Context context, String url, ImageView imageView) {
//        Glide.with(context)
//                .load(url)
//                .placeholder(DEFAULT_IMAGE)
//                .error(ERROR_IMAGE)
//                .centerCrop()
//                .transform(new GlideCircleTransform(context))
//                .into(imageView);
//    }
    /**
     * 监控加载过程
     *
     */
    public void loadImage(Context context, String url, LoadingImageListener loadingImageListener) {
        MySimpleTarget mySimpleTarget=new MySimpleTarget(loadingImageListener);
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(DEFAULT_IMAGE)
                .error(ERROR_IMAGE)
                .centerCrop()//centerCrop设置填充满imageview，可能有部分被裁剪掉，还有一种方式是fitCenter，将图片完整显示
                .into(mySimpleTarget);
    }
    public class MySimpleTarget extends SimpleTarget<Bitmap> {

        private LoadingImageListener imageListener;

        public MySimpleTarget(LoadingImageListener imageListener) {
            this.imageListener = imageListener;
        }

        public MySimpleTarget(int width, int height, LoadingImageListener imageListener) {
            super(width, height);
            this.imageListener = imageListener;
        }


        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            if (null!=imageListener){
                imageListener.onLoadSuccess(resource);
            }
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
            super.onLoadFailed(e, errorDrawable);
            imageListener.onLoadFailure();
        }

        @Override
        public void onLoadStarted(Drawable placeholder) {
            super.onLoadStarted(placeholder);
            imageListener.onLoadStart();
        }
    }

}
