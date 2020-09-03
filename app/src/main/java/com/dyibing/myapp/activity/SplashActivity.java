package com.dyibing.myapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.LogUtils;
import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.ForestCoinBean;
import com.dyibing.myapp.listener.OnMultiClickListener;
import com.dyibing.myapp.mvp.presenter.ForestCoinPresenter;
import com.dyibing.myapp.mvp.view.ForestCoinView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.Utils;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Descripttion: 启动页
 * @Author: 陈俊伟
 * @Date: 2020/8/26
 */
public class SplashActivity extends AppCompatActivity implements ForestCoinView {
    public final String TAG = getClass().getSimpleName();
    private String receiveForestCoinStatus;

    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.point1)
    ImageView point1;
    @BindView(R.id.point2)
    ImageView point2;
    @BindView(R.id.point3)
    ImageView point3;
    @BindView(R.id.point4)
    ImageView point4;

    //容器
    private List<View> mList = new ArrayList<>();
    /**
     * 权限部分
     */
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO
    };

    private ForestCoinPresenter forestCoinPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        checkPermission();
        initView();
    }

    private void init() {
        ButterKnife.bind(this);
        AudioUtils.getInstance().init(this); //初始化语音对象
        forestCoinPresenter = new ForestCoinPresenter(this, this);
        forestCoinPresenter.receiveForestCoinStatus();
    }

    private void initView() {
        point1.setVisibility(View.VISIBLE);
        point2.setVisibility(View.VISIBLE);
        point3.setVisibility(View.VISIBLE);
        //设置默认图片
        setPointImg(true, false, false, false);

        View view1 = View.inflate(this, R.layout.pager_item_one, null);
        View view2 = View.inflate(this, R.layout.pager_item_two, null);
        View view3 = View.inflate(this, R.layout.pager_item_three, null);
        Button btnLogin = view3.findViewById(R.id.btn_login);
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);
        if (TextUtils.isEmpty(DataCenter.getInstance().getToken())) {
            btnLogin.setText(R.string.login_now);
        } else {
            btnLogin.setText(R.string.go_main);
        }
        btnLogin.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (TextUtils.isEmpty(DataCenter.getInstance().getToken())) {
                    //未登录 进入授权
                    startActivity(new Intent(SplashActivity.this, AuthActivity.class));
                } else {
                    //已经登录
                    if (Utils.isReceiveForestCoin(receiveForestCoinStatus)) {
                        startActivity(new Intent(SplashActivity.this, ForestCoinActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                }
                finish();
            }
        });

        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());

        //监听ViewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //pager切换
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setPointImg(true, false, false, false);
                        AudioUtils.getInstance().speakText(getString(R.string.splash_first));
                        break;
                    case 1:
                        setPointImg(false, true, false, false);
                        AudioUtils.getInstance().speakText(getString(R.string.splash_two));
                        break;
                    case 2:
                        setPointImg(false, false, true, false);
                        AudioUtils.getInstance().speakText(getString(R.string.splash_three));
                        break;
                    case 3:
                        setPointImg(false, false, false, true);
                        AudioUtils.getInstance().speakText(getString(R.string.splash_four));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        AudioUtils.getInstance().speakText(getString(R.string.splash_first));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            checkPermission();//重新申请权限
        }
    }

    /**
     * 权限检测
     */
    @SuppressLint("CheckResult")
    private void checkPermission() {
        new RxPermissions(this).request(permissions)
                .subscribe(granted -> {
                    if (granted) {
                        LogUtils.dTag(TAG, "所有权限都已打开！可以开始操作界面");
                    } else {
                        LogUtils.dTag(TAG, "至少有一个权限被禁止");
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 1);
                    }
                });
    }

    @Override
    public void onReceiveForestCoin(HttpResult httpResult) {

    }

    @Override
    public void onReceiveForestCoinStatus(ForestCoinBean forestCoinBean) {
        receiveForestCoinStatus = forestCoinBean.getReceiveForestCoinStatus();
    }

    @Override
    public void onUseForestCoin(HttpResult httpResult) {

    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }
    }

    //设置小圆点的选中效果
    private void setPointImg(boolean isCheck1, boolean isCheck2, boolean isCheck3, boolean isCheck4) {
        if (isCheck1) {
            point1.setBackgroundResource(R.drawable.point_on);
        } else {
            point1.setBackgroundResource(R.drawable.point_off);
        }

        if (isCheck2) {
            point2.setBackgroundResource(R.drawable.point_on);
        } else {
            point2.setBackgroundResource(R.drawable.point_off);
        }

        if (isCheck3) {
            point3.setBackgroundResource(R.drawable.point_on);
        } else {
            point3.setBackgroundResource(R.drawable.point_off);
        }

        if (isCheck4) {
            point4.setBackgroundResource(R.drawable.point_on);
        } else {
            point4.setBackgroundResource(R.drawable.point_off);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioUtils.getInstance().stopSpeaking();
    }
}
