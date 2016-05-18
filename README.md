#Android自定义控件1
**自定义控件blog的第一篇，学习的大部分内容是通过Android原生控件的组合，来实现一些开发时比较实用且比较常用的各种自定义控件。**
##1、自定义控件之仿优酷菜单
![image](/Users/xwf/Desktop/d1.gif)
>定义三个相对布局，用来存放3级动画图片

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.xwf.androiddiywidget.MainActivity">

    <RelativeLayout
        android:id="@+id/rl_level3"
        android:layout_width="360dp"
        android:layout_height="180dp"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:background="@drawable/level3">
    </RelativeLayout>
    <RelativeLayout
        android:id="@+id/rl_level2"
        android:layout_width="200dp"
        android:layout_height="100dp"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:background="@drawable/level2">
        <ImageButton
            android:id="@+id/btn_menu"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/icon_menu"
            android:background="@android:color/transparent"
            android:layout_centerHorizontal="true"/>
    </RelativeLayout>
    <RelativeLayout
        android:id="@+id/rl_level1"
        android:layout_width="100dp"
        android:layout_height="50dp"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:background="@drawable/level1">
        <ImageButton
            android:id="@+id/btn_home"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@android:color/transparent"
            android:layout_centerInParent="true"
            android:src="@drawable/icon_home"/>
    </RelativeLayout>

</RelativeLayout>
```

>写一个旋转动画的工具类，接受参数是RelativeLayout

```
/**
 * Created by Hsia on 16/5/13.
 * E-mail: xiaweifeng@live.cn
 * //TODO:动画工具类
 */
public class AnimationUtils {
    //定义动画执行过程
    public static int runingAnimation = 0;
    /**
     * 退出动画
     * @param rl
     */
    public static void rotateOutAnimation(RelativeLayout rl,long offset){
        RotateAnimation rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setStartOffset(offset);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setAnimationListener(new MyAnimationListener());
        rl.startAnimation(rotateAnimation);
    }

    /**
     * 进入动画
     * @param rl
     */
    public static void rotateInAnimation(RelativeLayout rl,long offset){
        RotateAnimation rotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setStartOffset(offset);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setAnimationListener(new MyAnimationListener());
        rl.startAnimation(rotateAnimation);
    }

    //设置一个动画监听
    static class MyAnimationListener implements Animation.AnimationListener{

        private static final String TAG = "Hsia";

        @Override
        public void onAnimationStart(Animation animation) {
            runingAnimation++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            runingAnimation--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
```

##2、viewpager简单学习
一个Viewpager的Demo，可无限左右切换页面，没3秒自动切换页面。
![image](/Users/xwf/Desktop/d2.gif)

> 定义布局

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.a02viewpagertest.MainActivity">

    <android.support.v4.view.ViewPager
        android:id="@+id/vp"
        android:layout_width="match_parent"
        android:layout_height="210dp"></android.support.v4.view.ViewPager>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:layout_alignBottom="@+id/vp"
        android:background="#55000000">
        <TextView
            android:id="@+id/tv_description"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="哈哈哈哈哈哈哈"
            android:layout_marginTop="3dp"
            android:layout_marginBottom="3dp"
            android:gravity="center_horizontal"
            android:textSize="20sp"/>
        <LinearLayout
            android:id="@+id/ll_point"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center_horizontal"
            android:layout_marginBottom="5dp"
            android:orientation="horizontal">

        </LinearLayout>
    </LinearLayout>
</RelativeLayout>
```
>shape选择器

![image](/Users/xwf/Desktop/d3.png)

> 详细代码就3个部分（伪无限循环，自动循环定时器）

```
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
```
##自定义下拉框（用PopupWindow实现）

![image](/Users/xwf/Desktop/d4.gif)

>涉及知识点

- Popupwindow的简单使用

```
popupWindow = new PopupWindow(listView,mETContext.getWidth(),1200);
            //点击popupwindow的外面 隐藏poupwindow
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.showAsDropDown(mETContext);
```
- popupwindow内listview的点击事件处理

	在layout的节点下设置 android:descendantFocusability="blocksDescendants"这个属性
	
- listview数据移除和刷新

```
//删除集合数据，并刷新listview数据设配器
                    list.remove(position);
                    myBaseAdapter.notifyDataSetChanged();
                   //如果集合中没有数据了，关闭popupwindow
                    if (list.size() == 0) {
                        popupWindow.dismiss();
                    }
```

> 自定义下拉框代码展示

```
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Hsia";
    private ImageButton mIBtn;
    private EditText mETContext;
    private ListView listView;
    private List<String> list;
    private TextView et_text;
    private PopupWindow popupWindow;
    private MyBaseAdapter myBaseAdapter;
    private boolean isopen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mIBtn.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void init() {
        mETContext = ((EditText) findViewById(R.id.et_context));
        mIBtn = ((ImageButton) findViewById(R.id.ib_check));
    }


    /**
     * 点击imagebutton 弹出PopupWindow
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (isopen) {
            Log.d(TAG, "onClick: ");
            initListView();
            popupWindow = new PopupWindow(listView, mETContext.getWidth(), 1200);
            //点击popupwindow的外面 隐藏poupwindow
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.showAsDropDown(mETContext);
            isopen = false;
        } else {
            popupWindow.dismiss();
            isopen = true;
        }
    }

    /**
     * 初始化listview数据
     */
    private void initListView() {
        listView = new ListView(getApplicationContext());
        myBaseAdapter = new MyBaseAdapter();
        initlvData();
        listView.setAdapter(myBaseAdapter);
//        listView.setBackgroundResource(R.drawable.listview_background); //指定listv的背景
//        listView.setCacheColorHint(Color.BLACK);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String context = list.get(position);
                mETContext.setText(context);
                popupWindow.dismiss();
            }
        });
    }

    private void initlvData() {
        //把listview需要的数据放入到集合中
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("1000000" + i);
        }
    }

    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.list_item, null);
            } else {
                view = convertView;
            }
            ImageButton imageButton = (ImageButton) view.findViewById(R.id.ibtn);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除集合数据，并刷新listview数据设配器
                    list.remove(position);
                    myBaseAdapter.notifyDataSetChanged();
                    //如果集合中没有数据了，关闭popupwindow
                    if (list.size() == 0) {
                        popupWindow.dismiss();
                    }
                }
            });
            et_text = (TextView) view.findViewById(R.id.et);
            et_text.setText(list.get(position));

            return view;
        }
    }
}
```

**关于作者**
	- 个人网站：[北京互联科技](http://shop.zbj.com/14622657/)
	- Email：[xiaweifeng@live.cn](https://login.live.com)
	- 项目地址:[https://github.com/swordman20/AndroidDIYWidget](https://github.com/swordman20/AndroidDIYWidget)