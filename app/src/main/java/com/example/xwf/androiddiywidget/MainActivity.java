package com.example.xwf.androiddiywidget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.rl_level3)
    RelativeLayout rlLevel3;
    @Bind(R.id.btn_menu)
    ImageButton btnMenu;
    @Bind(R.id.rl_level2)
    RelativeLayout rlLevel2;
    @Bind(R.id.btn_home)
    ImageButton btnHome;
    @Bind(R.id.rl_level1)
    RelativeLayout rlLevel1;
    private boolean leven3isOpen = true;    //最外面的圈圈是否被打开
    private boolean leven2isOpen = true;
    private long offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_menu, R.id.btn_home})
    public void onClick(View view) {
        if (AnimationUtils.runingAnimation!=0) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_menu:

                if (leven3isOpen) {
                    //点击menu键，切换最外面圈圈
                    AnimationUtils.rotateOutAnimation(rlLevel3,offset);
                    leven3isOpen = false;
                }else {
                    AnimationUtils.rotateInAnimation(rlLevel3,offset);
                    leven3isOpen = true;
                }

                break;
            case R.id.btn_home:
                if (leven3isOpen){
                    //点击menu键，切换最外面圈圈
                    AnimationUtils.rotateOutAnimation(rlLevel3,offset);
                    leven3isOpen = false;
                    offset+=300;//加点延迟
                }
                if (leven2isOpen) {
                        //点击menu键，切换最外面圈圈
                        AnimationUtils.rotateOutAnimation(rlLevel2,offset);
                        leven2isOpen = false;
                    }else {
                        AnimationUtils.rotateInAnimation(rlLevel2,offset);
                        leven2isOpen = true;
                    }
                break;
        }
    }
}
