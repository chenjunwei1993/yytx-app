package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.FragmentBean;
import com.dyibing.myapp.bean.FragmentResult;
import com.dyibing.myapp.bean.PositionBean;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.fragment.DiceDialogFragment;
import com.dyibing.myapp.fragment.ForestDialogFragment;
import com.dyibing.myapp.fragment.FragmentDialogFragment;
import com.dyibing.myapp.mvp.presenter.FragmentPresenter;
import com.dyibing.myapp.mvp.presenter.UserInfoPresenter;
import com.dyibing.myapp.mvp.view.FragmentView;
import com.dyibing.myapp.mvp.view.UserInfoView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.Utils;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.dyibing.myapp.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Descripttion: 主页
 * @Author: chenjunwei
 * @Date: 2020/8/26
 */
public class MainActivity extends AppCompatActivity implements UserInfoView, FragmentView {

    @BindView(R.id.circle_avatar)
    CircleImageView circleAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_forest_coin_count)
    TextView tvForestCoinCount;
    @BindView(R.id.iv_yy)
    ImageView ivYy;
    @BindView(R.id.iv_main)
    ImageView ivMain;

    private UserInfoPresenter userInfoPresenter;
    private FragmentPresenter fragmentPresenter;
    private List<PositionBean> positionBeanList = new ArrayList<>();
    private int totalStepCount;
    private int firstPicStep = 14;
    private int secondPicStep = 13;
    private int thirdPicStep = 13;
    private int totalPicStep = 27;
    private int[] mainBg = {R.drawable.bg_main1, R.drawable.bg_main2, R.drawable.bg_main3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        AudioUtils.getInstance().init(this); //初始化语音对象
        userInfoPresenter = new UserInfoPresenter(this, this);
        fragmentPresenter = new FragmentPresenter(this, this);
        totalPicStep = firstPicStep + secondPicStep + thirdPicStep;
        initFirstPicPosition();
        initSecondPicPosition();
        initThirdPicPosition();
    }

    /**
     * 初始化第一张图位置
     */
    private void initFirstPicPosition() {
        positionBeanList.add(getPositionBean(0.4555f, 0.9f));
        positionBeanList.add(getPositionBean(0.5555f, 0.8527f));
        positionBeanList.add(getPositionBean(0.6555f, 0.7944f));
        positionBeanList.add(getPositionBean(0.5638f, 0.7472f, true));
        positionBeanList.add(getPositionBean(0.5972f, 0.6666f));
        positionBeanList.add(getPositionBean(0.6861f, 0.6111f));
        positionBeanList.add(getPositionBean(0.75f, 0.5472f, true));
        positionBeanList.add(getPositionBean(0.8055f, 0.4722f));
        positionBeanList.add(getPositionBean(0.7361f, 0.4111f));
        positionBeanList.add(getPositionBean(0.8055f, 0.3472f));
        positionBeanList.add(getPositionBean(0.7638f, 0.2722f));
        positionBeanList.add(getPositionBean(0.8277f, 0.2f));
        positionBeanList.add(getPositionBean(0.7333f, 0.1527f));
        positionBeanList.add(getPositionBean(0.7694f, 0.0694f));
    }

    private void initSecondPicPosition() {
        positionBeanList.add(getPositionBean(0.6611f, 0.9388f));
        positionBeanList.add(getPositionBean(0.5694f, 0.8819f));
        positionBeanList.add(getPositionBean(0.6666f, 0.8333f));
        positionBeanList.add(getPositionBean(0.5694f, 0.7777f));
        positionBeanList.add(getPositionBean(0.5208f, 0.7013f));
        positionBeanList.add(getPositionBean(0.5236f, 0.6111f));
        positionBeanList.add(getPositionBean(0.5444f, 0.525f));
        positionBeanList.add(getPositionBean(0.5694f, 0.4444f));
        positionBeanList.add(getPositionBean(0.6111f, 0.3611f));
        positionBeanList.add(getPositionBean(0.6875f, 0.2958f));
        positionBeanList.add(getPositionBean(0.7402f, 0.2194f));
        positionBeanList.add(getPositionBean(0.7833f, 0.1416f));
        positionBeanList.add(getPositionBean(0.8055f, 0.0472f));
    }

    private void initThirdPicPosition() {
        positionBeanList.add(getPositionBean(0.8263f, 0.9347f));
        positionBeanList.add(getPositionBean(0.7333f, 0.8888f));
        positionBeanList.add(getPositionBean(0.8263f, 0.8361f));
        positionBeanList.add(getPositionBean(0.7305f, 0.7861f));
        positionBeanList.add(getPositionBean(0.6944f, 0.7083f));
        positionBeanList.add(getPositionBean(0.5972f, 0.6555f));
        positionBeanList.add(getPositionBean(0.6944f, 0.6013f));
        positionBeanList.add(getPositionBean(0.5972f, 0.55f));
        positionBeanList.add(getPositionBean(0.5f, 0.5f));
        positionBeanList.add(getPositionBean(0.4444f, 0.4305f));
        positionBeanList.add(getPositionBean(0.3861f, 0.3569f));
        positionBeanList.add(getPositionBean(0.3305f, 0.2861f));
        positionBeanList.add(getPositionBean(0.2777f, 0.2111f));
    }

    private PositionBean getPositionBean(float radioX, float radioY) {
        return getPositionBean(radioX, radioY, false);
    }

    private PositionBean getPositionBean(float radioX, float radioY, boolean isFragment) {
        PositionBean positionBean = new PositionBean();
        positionBean.setPositionX(getPositionX(radioX));
        positionBean.setPositionY(getPositionY(radioY));
        positionBean.setFragment(isFragment);
        return positionBean;
    }

    private int getPositionX(float radioX) {
        return (int) (ScreenUtils.getScreenWidth() * radioX) - SizeUtils.dp2px(10);
    }

    private int getPositionY(float radioY) {
        return (int) (ScreenUtils.getScreenHeight() * radioY) - SizeUtils.dp2px(20);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Utils.fullScreen(getWindow());
    }

    @Override
    protected void onResume() {
        super.onResume();
        userInfoPresenter.getUserInfo();
    }

    @OnClick({R.id.circle_avatar, R.id.iv_gold, R.id.iv_fragment, R.id.iv_dice})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.circle_avatar:
                startActivity(new Intent(this, UserCenterActivity.class));
                break;
            case R.id.iv_gold:
                startActivity(new Intent(MainActivity.this, StartAnswerActivity.class));
                break;
            case R.id.iv_fragment:
                startActivity(new Intent(MainActivity.this, FragmentListActivity.class));
                break;
            case R.id.iv_dice:
                showDiceDialogFragment();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userInfoPresenter.onDestory();
    }

    @Override
    public void onUserInfo(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return;
        }
        DataCenter.getInstance().setUserGrade(userInfoBean.getUserGrade());
        Utils.setText(String.valueOf(userInfoBean.getForestCoinCount()), tvForestCoinCount);
        if (!TextUtils.isEmpty(userInfoBean.getAvatarUrl())) {
            Glide.with(this).load(userInfoBean.getAvatarUrl()).into(circleAvatar);
        }
        if (!TextUtils.isEmpty(userInfoBean.getNickName())) {
            tvName.setText(userInfoBean.getNickName());
        } else {
            tvName.setText(userInfoBean.getUserId());
        }
    }

    private DiceDialogFragment diceDialogFragment;

    /**
     * 随机骰子
     */
    public void showDiceDialogFragment() {
        if (null != diceDialogFragment) {
            diceDialogFragment.dismiss();
        }
        diceDialogFragment = new DiceDialogFragment();
        diceDialogFragment.setOnDiceStepListener(stepCount -> {
            totalStepCount += stepCount;
            int step = totalStepCount % totalPicStep;
            //循环切换地图
            if (step < firstPicStep) {
                ivMain.setImageResource(mainBg[0]);
            } else if (step >= firstPicStep && step < firstPicStep + secondPicStep) {
                ivMain.setImageResource(mainBg[1]);
            } else if (step >= firstPicStep + secondPicStep && step < firstPicStep + secondPicStep + thirdPicStep) {
                ivMain.setImageResource(mainBg[2]);
            }
            setYYPosition(positionBeanList.get(totalStepCount % positionBeanList.size()));
        });
        diceDialogFragment.show(getFragmentManager(), "showAnswerDialogFragment");
        new Handler().postDelayed(() -> {
            diceDialogFragment.dismiss();
        }, 500);
    }

    /**
     * 设置YY位置 是碎片位置请求碎片
     *
     * @param positionBean
     */
    private void setYYPosition(PositionBean positionBean) {
        AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams) ivYy.getLayoutParams();
        lp.x = positionBean.getPositionX();
        lp.y = positionBean.getPositionY();
        ivYy.setLayoutParams(lp);
        if (positionBean.isFragment()) {
            fragmentPresenter.getFragment();
        }
    }

    private FragmentDialogFragment fragmentDialogFragment;

    /**
     * 获取碎片
     */
    public void showFragmentDialogFragment(String fragmentUrl) {
        if (null != fragmentDialogFragment) {
            fragmentDialogFragment.dismiss();
        }
        fragmentDialogFragment = new FragmentDialogFragment();
        fragmentDialogFragment.setFragmentUrl(fragmentUrl);
        fragmentDialogFragment.show(getFragmentManager(), "showFragmentDialogFragment");
        new Handler().postDelayed(() -> fragmentDialogFragment.dismiss(), 2000);
    }

    private ForestDialogFragment forestDialogFragment;

    /**
     * 森林币不足提示信息
     */
    public void showForestDialogFragment() {
        if (null != forestDialogFragment) {
            forestDialogFragment.dismiss();
        }
        forestDialogFragment = new ForestDialogFragment();
        forestDialogFragment.show(getFragmentManager(), "showForestDialogFragment");
    }

    @Override
    public void getFragment(FragmentBean fragmentBean) {
        showFragmentDialogFragment(fragmentBean.getFragmentUrl());
    }

    @Override
    public void getFragmentList(FragmentResult fragmentResult) {

    }

    @Override
    public void fragmentSale(HttpResult httpResult) {

    }
}
