package com.lykj.aextreme.afinal.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.lykj.aextreme.R;
import com.lykj.aextreme.afinal.bean.LoadCache;
import com.lykj.aextreme.afinal.utils.Constant;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyUtil;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, IBaseActivity {

    private BaseApplication app;
    protected Context context = this;
    private Class<?> lastAct;// 上一级 Activity
    private String lastSkipAct;// 跳转过来的Activity
    private Toolbar toolbar;
    private TextView txtTitle;
    private FrameLayout flyMain;
    private LoadCache loadCache;
    private boolean translucentStatus;

    public void setTranslucentStatus(boolean translucentStatus) {
        this.translucentStatus = translucentStatus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window =getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.title));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        init();
        initView();
        initData();
    }
    private void init() {
        lastSkipAct = getIntent().getStringExtra(Constant.LAST_ACT);// 获取上一级Activity的Name
        try {
            app = (BaseApplication) getApplication();
        } catch (Exception e) {
            Debug.e(getClass(), "BaseApplication Exception");
            System.exit(0);
            return;
        }
        app.addActivity(this);
        int layout = initLayoutId();
        if (layout < 1) return;
        setContentView(layout);
        toolbar = getView(R.id.head_toolBar);
        boolean custumHeader = true;
        if (toolbar == null) {
            custumHeader = false;
            setContentView(R.layout.act_base);
            toolbar = getView(R.id.head_toolBar);
        }
        toolbar.setTitle("");
        int statusBarHeight = getStatusBarHeight();
        if (translucentStatus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && statusBarHeight > 0) {
            toolbar.setPadding(0, statusBarHeight, 0, 0);
        }
        setSupportActionBar(toolbar);
        txtTitle = getView(R.id.head_vTitle);
        flyMain = getView(R.id.base_flyMain);
        if (!custumHeader && flyMain != null) {
            View.inflate(context, layout, flyMain);
        }
    }

    public void setToolbarBack(int color) {
        toolbar.setBackgroundColor(getResources().getColor(color));
    }

    /**
     * 初始化数据
     */
    public void onStartData() {

    }

    public TextView getTitleText() {
        if (txtTitle != null)
            return txtTitle;
        return null;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public BaseApplication getApp() {
        return app;
    }

    /**
     * 获取上一级的Activity名
     */
    public String getLastSkipAct() {
        return lastSkipAct;
    }


//    @SuppressLint("NewApi")
//    private void setTitlebarBackground() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            rlyHead.setBackground(theme.getDrawTitleBar());
//        } else {
//            rlyHead.setBackgroundDrawable(theme.getDrawTitleBar());
//        }
//    }

    @Deprecated
    protected void showNavigation(int drawId) {
        if (drawId > 0)
            toolbar.setNavigationIcon(drawId);
        else
            toolbar.setNavigationIcon(null);
    }

    /**
     * 加载布局
     */
    private boolean initLoadView() {
        if (flyMain == null) {
            Debug.e(getClass(), "Your layout must include 'FrameLayout',the ID must be 'base_flyMain'!");
            return false;
        }
        if (loadCache != null) return true;
        View loadView = getView(R.id.base_llyLoad);
        //You need to add the layout
        if (loadView == null) {
            if (flyMain.getChildCount() > 0) {
                loadView = View.inflate(context, R.layout.in_loading, null);
                flyMain.addView(loadView, 0);
            } else
                View.inflate(context, R.layout.in_loading, flyMain);
        }
        loadCache = new LoadCache();
        loadCache.llyLoad = getView(R.id.base_llyLoad);
        loadCache.proLoading = getView(R.id.base_proLoading);
        loadCache.imgMessage = getView(R.id.base_imgMessage);
        loadCache.txtMessage = getView(R.id.base_txtMessage);
        return true;
    }

    protected void showLoading() {
        showLoading(getString(R.string.loading));
    }

    protected void showLoading(String msg) {
        if (initLoadView()) {
            int count = flyMain.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = flyMain.getChildAt(i);
                if (i > 0) v.setVisibility(View.GONE);
            }
            loadCache.showLoading(msg);
        }
    }

    protected void showNoData() {
        showNoData(getString(R.string.hint_nodata));
    }

    protected void showNoData(String msg) {
        showNoData(msg, R.drawable.img_hint_nodata);
    }

    //R.drawable.img_hint_net_fail
    protected void showNoData(String msg, int drawId) {
        if (initLoadView()) {
            int count = flyMain.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = flyMain.getChildAt(i);
                if (i > 0) v.setVisibility(View.GONE);
            }
            loadCache.showNoData(msg, drawId);
        }
    }

    /**
     * 显示内容View
     */
    protected void showCView() {
        if (initLoadView()) {
            int count = flyMain.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = flyMain.getChildAt(i);
                if (i == 0) v.setVisibility(View.GONE);
                else v.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置标题
     */
    @Override
    public void setTitle(@StringRes int titleId) {
        setTitle(getString(titleId));

    }

    /**
     * 设置标题
     */
    @Override
    public void setTitle(CharSequence title) {
        if (txtTitle != null)
            txtTitle.setText(title);
        else
            super.setTitle(title);
    }

    protected void hideHeader() {
        if (toolbar != null) toolbar.setVisibility(View.GONE);
    }

    @SuppressLint("ResourceType")
    protected void setHeaderLeft(@DrawableRes int left) {
        if (left > 0) {
            if (toolbar.findViewById(R.id.head_vLeft) == null) {
                View v = View.inflate(context, R.layout.in_head_left, toolbar);
                ImageView img = getView(v, R.id.head_vLeft);
                img.setOnClickListener(this);
                img.setImageResource(left);
            } else {
                ImageView img = getView(toolbar, R.id.head_vLeft);
                img.setImageResource(left);
            }
        }
    }

    @SuppressLint("ResourceType")
    protected void setHeaderLeftTxt(int s, @StringRes int left) {
        if (left > 0) {
            if (toolbar.findViewById(R.id.head_vLeft) == null) {
                View v = View.inflate(context, R.layout.in_head_tleft, toolbar);
                TextView txt = getView(v, R.id.head_vLeft);
                txt.setOnClickListener(this);
                txt.setText(left);
                if (txtTitle != null)
                    txt.setTextColor(txtTitle.getTextColors());
            } else {
                TextView txt = getView(toolbar, R.id.head_vLeft);
                txt.setText(left);
            }
        }
    }

    @SuppressLint("ResourceType")
    protected void setHeaderRight(@DrawableRes int right) {
        if (right > 0) {
            if (toolbar.findViewById(R.id.head_vRight) == null) {
                View v = View.inflate(context, R.layout.in_head_right, toolbar);
                ImageView img = getView(v, R.id.head_vRight);
                img.setOnClickListener(this);
                img.setImageResource(right);
            } else {
                ImageView img = getView(toolbar, R.id.head_vRight);
                img.setImageResource(right);
            }
        }
    }
    @SuppressLint("ResourceType")
    protected void setHeaderRightTxt(@StringRes int right) {
        if (right > 0) {
            if (toolbar.findViewById(R.id.head_vRight) == null) {
                View v = View.inflate(context, R.layout.in_head_tright, toolbar);
                TextView txt = getView(v, R.id.head_vRight);
                txt.setOnClickListener(this);
                txt.setText(right);
                if (txtTitle != null)
                    txt.setTextColor(txtTitle.getTextColors());
            } else {
                TextView txt = getView(toolbar, R.id.head_vRight);
                txt.setText(right);
            }
        }
    }

    /**
     * 头部
     */
    protected View getHeader() {
        return toolbar;
    }

    protected View getHeaderRight() {
        return toolbar.findViewById(R.id.head_vRight);
    }

    protected View getMainView() {
        return flyMain;
    }

    protected void setLastAct(Class<?> cls) {
        this.lastAct = cls;
    }

    /**
     * @see #startAct(Intent, Class)
     */
    protected void startAct(Class<?> cls) {
        startAct(null, cls);
    }

    /**
     * 启动Activity
     */
    protected void startAct(Intent intent, Class<?> cls) {
        if (intent == null)
            intent = new Intent();
        intent.putExtra(Constant.LAST_ACT, this.getClass().getSimpleName());
        intent.setClass(this, cls);
        startActivity(intent);
    }

    /**
     * @see #startActClear(Intent, Class)
     */
    @Deprecated
    public void startActClear(Class<?> cls) {
        startActClear(null, cls);
    }

    /**
     * 启动Activity，清空栈 并添加到栈顶，慎用
     */
    @Deprecated
    protected void startActClear(Intent intent, Class<?> cls) {
        if (app != null) app.clear();
        startAct(intent, cls);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Constant.LAST_ACT, this.getClass().getSimpleName());
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void finish() {
        if (app != null) app.remove(this);
        if (lastAct != null && TextUtils.equals(lastAct.getSimpleName(), lastSkipAct)) {
            startActClear(lastAct);
        } else super.finish();
    }

    protected String getStrings(Integer... ids) {
        if (ids.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int id : ids) {
                sb.append(getString(id));
            }
            return sb.toString();
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        if (MyUtil.isFastClick())
            return;
        if (v.getId() == R.id.head_vLeft)
            onLeftClick();
        else if (v.getId() == R.id.head_vRight)
            onRightClick();
        else
            onViewClick(v);
    }

    /**
     * 获取 控件
     *
     * @param v  布局
     * @param id 行布局中某个组件的id
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(View v, @IdRes int id) {
        return (T) v.findViewById(id);
    }

    /**
     * 获取 控件
     *
     * @param id 行布局中某个组件的id
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int id) {
        return (T) findViewById(id);
    }

    /**
     * 获取并绑定点击
     *
     * @param id id
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewAndClick(@IdRes int id) {
        T v = getView(id);
        v.setOnClickListener(this);
        return v;
    }

    /**
     * 获取并绑定点击
     *
     * @param id id
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewAndClick(View view, @IdRes int id) {
        T v = getView(view, id);
        v.setOnClickListener(this);
        return v;
    }

    protected TextView setOnClickListener(@IdRes int id) {
        findViewById(id).setOnClickListener(this);
        return null;
    }

    protected void setOnClickListener(View v, @IdRes int id) {
        v.findViewById(id).setOnClickListener(this);
    }

    /**
     * 头-左边图标点击
     */
    public void onLeftClick() {
        finish();
    }

    /**
     * 头-右边图标点击
     */
    public void onRightClick() {

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
