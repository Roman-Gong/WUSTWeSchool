package com.gongyunhaoyyy.wustweschool.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gongyunhaoyyy.wustweschool.R;
import com.gongyunhaoyyy.wustweschool.util.PermissionUtils;
import com.wang.avi.AVLoadingIndicatorView;
import java.text.SimpleDateFormat;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    private Activity mthis;
    protected int mScreenWidth;
    protected int mScreenHeight;
    public static final String TAG = "WustWXY";
    /**
     * 权限回调Handler
     */
    private PermissionHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mthis=this;
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        setContentView();
        initViews();
        initListeners();
        initData();
    }

    public BaseActivity getActivity() {
        return this;
    }

    public Intent getIntent(Class clazz) {
        return new Intent(mthis, clazz);
    }

    public void startIntent(Class clazz) {
        startActivity(getIntent(clazz));
    }

    /**
     * 请求权限
     *
     * @param permissions 权限列表
     * @param handler     回调
     */
    protected void requestPermission(String[] permissions, PermissionHandler handler) {
        if (PermissionUtils.hasSelfPermissions(this, permissions)) {
            handler.onGranted();
        } else {
            mHandler = handler;
            ActivityCompat.requestPermissions(this, permissions, 001);
        }
    }

    protected void hideStatusBar() {
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;//隐藏状态栏, 定义全屏参数
        Window window = getWindow(); //获得当前窗体对象
        window.setFlags(flag, flag);//设置当前窗体为全屏显示
    }

    /**
     * 权限回调接口
     */
    public abstract class PermissionHandler {
        /**
         * 权限通过
         */
        public abstract void onGranted();

        /**
         * 权限拒绝
         */
        public void onDenied() {
        }

        /**
         * 不再询问
         *
         * @return 如果要覆盖原有提示则返回true
         */
        public boolean onNeverAsk() {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        //继承了BaseActivity的类, 如果要使用返回关闭Activity的功能
        //需要在继承的Activity的onClick(View v)里写上super.onClick(v);
        switch (v.getId()) {
            case R.id.cl_ibtn_back:
                //R.id.back为标题左上角的返回控件
                onBackPressed();
                break;
            default:
                break;
        }
    }

    public String getDateForXq(){
        int year2=2017;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sDateFormat2 = new SimpleDateFormat("MM");
        int mm= Integer.parseInt( sDateFormat2.format(new java.util.Date()) );
        String year = sDateFormat.format(new java.util.Date());
        int year1= Integer.parseInt( year );
        if (mm>2&&mm<9){
            year1--;
            year2=year1+1;
            mm=2;
        }else if (mm<=2){
            year1--;
            year2=year1+1;
            mm=1;
        }else if (mm>=9){
            year2=year1++;
        }
        String date1=String.valueOf( year1 );
        String date2=String.valueOf( year2 );
        String mm2=String.valueOf( mm );
        String date3=date1+"-"+date2+"-"+mm2;
        return date3;
    }

    public abstract void setContentView();

    public abstract void initViews();

    public abstract void initListeners();

    public abstract void initData();

}
