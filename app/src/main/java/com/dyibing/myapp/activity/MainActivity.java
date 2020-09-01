package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.FragmentBean;
import com.dyibing.myapp.bean.FragmentResult;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.fragment.DiceDialogFragment;
import com.dyibing.myapp.fragment.FragmentDialogFragment;
import com.dyibing.myapp.mvp.presenter.FragmentPresenter;
import com.dyibing.myapp.mvp.presenter.UserInfoPresenter;
import com.dyibing.myapp.mvp.view.FragmentView;
import com.dyibing.myapp.mvp.view.UserInfoView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.Utils;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.dyibing.myapp.view.CircleImageView;

import androidx.appcompat.app.AppCompatActivity;
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

    private UserInfoPresenter userInfoPresenter;
    private FragmentPresenter fragmentPresenter;


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
        fragmentPresenter = new FragmentPresenter(this,this);
    }

    private void initPosition(){

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
        diceDialogFragment.show(getFragmentManager(), "showAnswerDialogFragment");
        new Handler().postDelayed(() -> {
            diceDialogFragment.dismiss();
            fragmentPresenter.getFragment();
        },1000);
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
        new Handler().postDelayed(() -> fragmentDialogFragment.dismiss(),3000);
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
