package com.example.a02viewpagertest;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Hsia";
    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.ll_point)
    LinearLayout llPoint;
    private List<ImageView> imageViewList;
    private int beforePager = 0;
    private String[] imageDescriptionArrays;
    //viewpager伪无限循环
    private int newPosition;
    //当Activity界面不见时不自动切换
    private boolean switchPager = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initVPData();
        //设置viewpager数据
        MyPagerAdapter pagerAdapter = new MyPagerAdapter();
        vp.setAdapter(pagerAdapter);
        vp.setOnPageChangeListener(new MyOnPageChangeListener());
        final int current = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        vp.setCurrentItem(current);
        //自动切换页面
        autoSwitchPager();
    }

    /**
     * 3、用一个定时器，定时没3秒切换页面
     */
    private void autoSwitchPager() {
        //用一个定时器
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(switchPager){
                        vp.setCurrentItem(vp.getCurrentItem()+1);
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask,3000,3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        switchPager = true;
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        switchPager = false;
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        switchPager = false;
        super.onDestroy();
    }

    /**
     * 1、初始化Viewpager数据
     */
    private void initVPData() {
        imageDescriptionArrays = new String[] {
                "巩俐不低俗，我就不能低俗",
                "朴树又回来啦！再唱经典老歌",
                "揭秘北京电影如何升级",
                "乐视网TV版大派送",
                "热血屌丝的反杀"
        };

        int[] imageResIDs = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
        //用于存放图片数据的集合
        imageViewList = new ArrayList<>();
        for (int i = 0; i < imageResIDs.length; i++) {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setBackgroundResource(imageResIDs[i]);
            imageViewList.add(iv);
            //初始化点
            View view = new View(getApplicationContext());
            view.setBackgroundResource(R.drawable.dot_select);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            if (i > 0){
                params.leftMargin = 20;
            }
            view.setEnabled(false);
            view.setLayoutParams(params);
            llPoint.addView(view);
        }
        //设置第一个点和文字描述
        tvDescription.setText(imageDescriptionArrays[beforePager]);
        llPoint.getChildAt(beforePager).setEnabled(true);

    }

    /**
     * 2、定义viewpager数据适配器
     */
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            newPosition = position%imageViewList.size();
            ImageView imageView = imageViewList.get(newPosition);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            Log.d(TAG, "onPageScrolled: ");
        }

        @Override
        public void onPageSelected(int position) {
            int newPosition = position % imageViewList.size();
            Log.d(TAG, "onPageSelected: ");
            llPoint.getChildAt(beforePager).setEnabled(false);
            //把后一个页面赋值给前一个页面
            beforePager = newPosition;
            llPoint.getChildAt(newPosition).setEnabled(true);
            tvDescription.setText(imageDescriptionArrays[newPosition]);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
