package com.whty.zjrcpos.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.whty.zjrcpos.R;


/**
 * Created by zhihao.wen on 2016/4/19.
 */
public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * 设置显示右侧返回按钮
     */
    public void setBackView() {
        View backView = findViewById(R.id.back);
        if (backView == null) {
            return;
        }
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 设置显示标题
     *
     * @param txt
     */
    public void setTitle(String txt) {
        TextView title = (TextView) findViewById(R.id.tv_title_center);
        if (title == null) {
            return;
        }
        title.setVisibility(View.VISIBLE);
        title.setText(txt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
