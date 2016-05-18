package com.example.a01android;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @butterknife.Bind(R.id.btn_translate)
    Button btnTranslate;
    @butterknife.Bind(R.id.btn_scale)
    Button btnScale;
    @butterknife.Bind(R.id.btn_alpha)
    Button btnAlpha;
    @butterknife.Bind(R.id.btn_rotate)
    Button btnRotate;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "你点不到我", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @butterknife.OnClick({R.id.btn_translate, R.id.btn_scale, R.id.btn_alpha, R.id.btn_rotate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_translate:
                //创建属性动画
                /**
                 * target 执行的目标
                 * propertyName 属性名字  The name of the property being animated.
                 * float... values 可变参数
                 */
                ObjectAnimator tx = ObjectAnimator.ofFloat(iv, "translationX", 10, 50,20,100);
                tx.setDuration(2000);
                tx.start();
                break;
            case R.id.btn_scale:
                ObjectAnimator sy = ObjectAnimator.ofFloat(iv, "scaleY", 0.1f, 2, 1, 2);
                sy.setDuration(2000);
                sy.start();
                break;
            case R.id.btn_alpha:
                ObjectAnimator aa = ObjectAnimator.ofFloat(iv, "alpha", 0, 0.5f, 0, 1);
                aa.setDuration(2000);
                aa.start();
                break;
            case R.id.btn_rotate:
                ObjectAnimator ry = ObjectAnimator.ofFloat(iv, "rotation", 0, 180, 90, 360);
        //		ObjectAnimator oa = ObjectAnimator.ofFloat(iv, "rotationY", 0, 180, 90, 360);
                ry.setDuration(2000);
                ry.start();
                break;
        }
    }
}
