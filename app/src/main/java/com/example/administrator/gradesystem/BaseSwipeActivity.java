package com.example.administrator.gradesystem;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import com.githang.statusbar.StatusBarCompat;
import com.example.administrator.gradesystem.R;

public class BaseSwipeActivity extends SwipeBackActivity {


    private TextView tv_title;
    private GradeApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initContentView(R.layout.activity_base_swipe);
        app=GradeApplication.getInstance();
        tv_title=(TextView)findViewById(R.id.tv_shareback_title);
       Toolbar toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        //设置自定义toolbar，不使用自带actionbar
        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ease_mm_title_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseSwipeActivity.this.finish();
            }
        });
       // app.AddFriendStatusChangedListener(this);
    }
    protected  void setMyTitle(String title){
        tv_title.setText(title);
    }
    private String getMyTitle(){return tv_title.getText().toString();}
    private LinearLayout parentLinearLayout;//把父类activity和子类activity的view都add到这里

    /**
     * 初始化contentview
     */
    private void initContentView(int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(parentLinearLayout);
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);

    }

    @Override
    public void setContentView(int layoutResID) {

        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bluetips));
    }

    @Override
    public void setContentView(View view) {

        parentLinearLayout.addView(view);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bluetips));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {

        parentLinearLayout.addView(view, params);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bluetips));

    }


//    @Override
//    public void ContactStatusChanged(String sourceUserID, UserStatus status) {
//
//        if(!app.getisConnected()){
//            handler.sendEmptyMessage(0x10076);
//        }
//        else {
//            handler.sendEmptyMessage(0x10078);
//        }
//    }
}
