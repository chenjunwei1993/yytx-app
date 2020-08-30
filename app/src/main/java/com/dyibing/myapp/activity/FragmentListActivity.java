package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.adapter.CashGiftAdapter;
import com.dyibing.myapp.adapter.FragmentAdapter;
import com.dyibing.myapp.bean.FragmentBean;
import com.dyibing.myapp.bean.FragmentResult;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.mvp.presenter.FragmentPresenter;
import com.dyibing.myapp.mvp.presenter.UserInfoPresenter;
import com.dyibing.myapp.mvp.view.FragmentView;
import com.dyibing.myapp.mvp.view.UserInfoView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.utils.Utils;
import com.dyibing.myapp.view.CircleImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentListActivity extends AppCompatActivity implements UserInfoView, FragmentView {
    @BindView(R.id.circle_avatar)
    CircleImageView circleAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_forest_coin_count)
    TextView tvForestCoinCount;
    @BindView(R.id.rv_fragment)
    RecyclerView rvFragment;
    @BindView(R.id.rv_gift)
    RecyclerView rvGift;
    @BindView(R.id.ll_gift)
    LinearLayout llGift;
    @BindView(R.id.ll_fragment)
    LinearLayout llFragment;

    private FragmentAdapter fragmentAdapter;
    private CashGiftAdapter cashGiftAdapter;
    private UserInfoPresenter userInfoPresenter;
    private FragmentPresenter fragmentPresenter;
    private int pageNo = 1;
    private int totalPageNo = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_list);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        userInfoPresenter = new UserInfoPresenter(this, this);
        fragmentPresenter = new FragmentPresenter(this, this);

        fragmentAdapter = new FragmentAdapter(this);
        rvFragment.setAdapter(fragmentAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        rvFragment.setLayoutManager(manager);

        cashGiftAdapter = new CashGiftAdapter(this);
        rvGift.setAdapter(cashGiftAdapter);
        LinearLayoutManager giftManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvGift.setLayoutManager(giftManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userInfoPresenter.getUserInfo();
        fragmentPresenter.getFragmentList(String.valueOf(pageNo));
    }

    @OnClick({R.id.circle_avatar, R.id.iv_back, R.id.iv_pre, R.id.iv_next})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.circle_avatar:
                startActivity(new Intent(this, UserCenterActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_pre:
                pageNo--;
                if (pageNo < 1) {
                    pageNo = 1;
                    SingleToast.showMsg("到头了哦！");
                } else {
                    fragmentPresenter.getFragmentList(String.valueOf(pageNo));
                }
                break;
            case R.id.iv_next:
                pageNo++;
                if (pageNo > totalPageNo) {
                    pageNo = totalPageNo;
                    SingleToast.showMsg("到底了哦！");
                } else {
                    fragmentPresenter.getFragmentList(String.valueOf(pageNo));
                }
                break;
        }
    }

    @Override
    public void onUserInfo(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return;
        }
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

    @Override
    public void getFragment(FragmentBean fragmentBean) {

    }

    @Override
    public void getFragmentList(FragmentResult fragmentResult) {
        if (null != fragmentResult.getCashGiftList() && fragmentResult.getCashGiftList().size() > 0) {
            llGift.setVisibility(View.VISIBLE);
            cashGiftAdapter.setData(fragmentResult.getCashGiftList());
        } else {
            llGift.setVisibility(View.GONE);
        }
        if (null != fragmentResult.getFragmentList() && fragmentResult.getFragmentList().size() > 0) {
            llFragment.setVisibility(View.VISIBLE);
            fragmentAdapter.setData(fragmentResult.getFragmentList());
        } else {
            llFragment.setVisibility(View.GONE);
        }
        totalPageNo = fragmentResult.getJigsawCount();
    }

    @Override
    public void fragmentSale(HttpResult httpResult) {

    }
}

