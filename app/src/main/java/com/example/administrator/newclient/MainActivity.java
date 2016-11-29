package com.example.administrator.newclient;



        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;


        import com.example.administrator.newclient.base.BaseActivity;
        import com.example.administrator.newclient.fragment.LeftMenuFragment;
        import com.example.administrator.newclient.fragment.MainFragment;
        import com.example.administrator.newclient.fragment.RightMenuFragment;
        import com.example.administrator.newclient.silidingmenu.SlidingMenu;


public class MainActivity extends BaseActivity {
    private ImageView ivMenuLeft;
    private ImageView ivMenuRight;
    private TextView tvTitle;

    public static SlidingMenu slidingMenu;
    private Fragment leftMenuFragment;
    private Fragment rightMenuFragment;

    private Fragment mainFragment;

    private long prevTime;//上一次时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initSlidingMenu();
        ivMenuLeft.setOnClickListener(listener);
        ivMenuRight.setOnClickListener(listener);
        initMainFragment();
    }

    public void initMainFragment(){
        if(mainFragment == null){
            mainFragment = new MainFragment();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rl_content, mainFragment);
        ft.commit();
    }

    //当按下back键时会调用该方法
    @Override
    public void onBackPressed() {
        if(slidingMenu != null && slidingMenu.isMenuShowing()){
            slidingMenu.showContent();
        }else{
            //按两次退出
            twiceExit();
        }
    }

    private void twiceExit(){
        long currentTime = System.currentTimeMillis();//系统当前时间
        if(currentTime - prevTime > 1500){
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            prevTime = currentTime;
        }else{
            prevTime = currentTime;
            finish();
            System.exit(0);
        }
    }

    //侧滑
    private void initSlidingMenu(){
        leftMenuFragment = new LeftMenuFragment();
        rightMenuFragment = new RightMenuFragment();


        slidingMenu = new SlidingMenu(this);
        //设置侧滑模式，左侧滑，右侧滑，左右侧滑
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置滑动的屏幕范围，该设置为全屏区域都可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //SlidingMenu划出时主页面显示的剩余宽度
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_margin);
        //使SlidingMenu附加在Activity上
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        //为侧滑菜单设置布局 设置menu的布局文件
        slidingMenu.setMenu(R.layout.layout_left_menu);
        //设置右边（二级）侧滑菜单
        slidingMenu.setSecondaryMenu(R.layout.layout_right_menu);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.ll_left_container, leftMenuFragment).commit();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.ll_right_container, rightMenuFragment).commit();
    }

    //初始化组件
    private void initView() {
        ivMenuLeft = (ImageView) findViewById(R.id.iv_left_menu_icon);
        ivMenuRight = (ImageView) findViewById(R.id.iv_right_menu_icon);
        tvTitle = (TextView) findViewById(R.id.tv_title);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.iv_left_menu_icon://当侧滑没有显示，则显示侧滑，当侧滑显示，则收起侧滑
                    if(slidingMenu != null && slidingMenu.isMenuShowing()){
                        slidingMenu.showContent();//显示内容
                    }else if(slidingMenu != null){
                        slidingMenu.showMenu();//显示SlidingMenu
                    }
                    break;
                case R.id.iv_right_menu_icon://
                    if(slidingMenu != null && slidingMenu.isMenuShowing()){
                        slidingMenu.showContent();
                    }else if(slidingMenu != null){
                        slidingMenu.showSecondaryMenu();
                    }
                    break;
            }
        }
    };
}




