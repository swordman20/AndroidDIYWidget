package com.example.a03;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

