package com.mooc.viewpage_photoview_circleindicator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.mooc.viewpage_photoview_circleindicator.photoview.PhotoView;
import com.mooc.viewpage_photoview_circleindicator.photoview.PhotoViewAttacher;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<String> urls = new ArrayList<>();
    private HackyViewPager viewPager;
    private MyViewPagerAdapter adapter;
    private int currentPosition;
    private CircleIndicator circleIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitData();
        initView();
        if (urls.size()==1){
            circleIndicator.setVisibility(View.GONE);
        }else{
            circleIndicator.setVisibility(View.VISIBLE);
        }
//        this.currentPosition = getIntent().getIntExtra(Common.BigPhotoActivity.CURRENT_POSITION, 0);

        currentPosition=0;

    }

    private void InitData() {
        urls.add("http://ww2.sinaimg.cn/large/610dc034jw1f454lcdekoj20dw0kumzj.jpg");
        urls.add("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo_top_ca79a146.png");
        urls.add("http://ww3.sinaimg.cn/mw690/8345c393jw1f32xv7zd4gj20go24yaqv.jpg");
        urls.add("http://ww2.sinaimg.cn/large/7a8aed7bjw1f3c7zc3y3rj20rt15odmp.jpg");
    }

    private void initView() {
        circleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator);
        viewPager = (HackyViewPager) findViewById(R.id.viewpager);
        viewPager.setBackgroundColor(Color.TRANSPARENT);
        adapter = new MyViewPagerAdapter(urls);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition);
        circleIndicator.setViewPager(viewPager);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.photo_activity_out);
    }

    class MyViewPagerAdapter extends PagerAdapter {

        private PhotoView[] photoViews;
        private ArrayList<String> urls;

        public MyViewPagerAdapter(ArrayList<String> urls) {
            this.urls = urls;
            photoViews = new PhotoView[urls.size()];
        }

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(photoViews[position]);
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            GlidePhotoView photoView = new GlidePhotoView(MainActivity.this, urls.get(position));
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
//                    finish();
                }

                @Override
                public void onOutsidePhotoTap() {
//                    finish();
                }
            });
            photoViews[position] = photoView;

            container.addView(photoView);
            return photoView;
        }


    }
}
