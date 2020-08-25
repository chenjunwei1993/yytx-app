package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.mvp.presenter.UserInfoPresenter;
import com.dyibing.myapp.mvp.view.TasksView;
import com.dyibing.myapp.mvp.view.UserInfoView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.utils.Utils;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.dyibing.myapp.view.CircleImageView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements TasksView, UserInfoView {

    @BindView(R.id.nice_iv0)
    CircleImageView niceIv0;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_zan)
    TextView tvZan;
    @BindView(R.id.tv_aixin)
    TextView tvAixin;
    @BindView(R.id.iv_help_yy)
    TextView ivHelpYy;
    @BindView(R.id.iv_check_ta)
    TextView ivCheckTa;
    @BindView(R.id.tv_today_task)
    TextView tvTodayTask;
    @BindView(R.id.tv_now_time)
    TextView tvNowTime;
    @BindView(R.id.clv_today_task)
    RecyclerView clvTodayTask;
    @BindView(R.id.tv_toady_task_hint)
    TextView tvToadyTaskHint;
    @BindView(R.id.tv_ys_task)
    TextView tvYsTask;
    @BindView(R.id.tv_ys_time)
    TextView tvYsTime;
    @BindView(R.id.clv_ys_task)
    RecyclerView clvYsTask;
    @BindView(R.id.tv_ys_task_hint)
    TextView tvYsTaskHint;
    @BindView(R.id.iv_go_task_list)
    ImageView ivTaskList;
    @BindView(R.id.srl_fresh)
    SwipeRefreshLayout srlFresh;

    private MainTaskAdapter mTodayTaskAdapter;
    private MainTaskAdapter mYesterdayTaskAdapter;
    private TasksPresenter tasksPresenter;
    private List<CurrentDateTaskBean.TaskBean.TaskListBean> todayList;
    private List<CurrentDateTaskBean.TaskBean.TaskListBean> yesterdayList;
    private UserInfoPresenter userInfoPresenter;
    private int tAwaitConfirmCount;
    private int yAwaitConfirmCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AudioUtils.getInstance().init(this); //初始化语音对象
        tasksPresenter = new TasksPresenter(this, this);
        mTodayTaskAdapter = new MainTaskAdapter(this);
//        mSwipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        clvTodayTask.setLayoutManager(new LinearLayoutManager(this));
        clvTodayTask.setAdapter(mTodayTaskAdapter);

        mYesterdayTaskAdapter = new MainTaskAdapter(this);
        clvYsTask.setLayoutManager(new LinearLayoutManager(this));
        clvYsTask.setAdapter(mYesterdayTaskAdapter);
        srlFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里获取数据的逻辑
                srlFresh.setRefreshing(false);
                tasksPresenter.getCurrentDateTask();
                userInfoPresenter.getUserInfo();
            }
        });
        userInfoPresenter = new UserInfoPresenter(this, this);
        userInfoPresenter.getUserInfo();//可能领取了森林币，刷新一遍
    }

    @Override
    protected void onResume() {
        super.onResume();
//        boolean isFirstLogin = getSharedPreferences(Constant.PREFERENCES_DB, Context.MODE_PRIVATE).getBoolean(Constant.FIRST_LOGIN, true);
//        if (isFirstLogin) {
//            SingleToast.showMsg("请先创建个人信息吧！");
//            Intent intent = new Intent(this, UserCenterActivity.class);
//            startActivity(intent);
//        } else
        initInfo();
        tasksPresenter.getCurrentDateTask();
        userInfoPresenter.getUserInfo();
    }

    private void initInfo() {
        if (!TextUtils.isEmpty(DataCenter.getInstance().getUser().getAvatarUrl())) {
            Glide.with(this).load(DataCenter.getInstance().getUser().getAvatarUrl()).into(niceIv0);
        }
        if (!TextUtils.isEmpty(DataCenter.getInstance().getUser().getNickname())) {
            tvName.setText(DataCenter.getInstance().getUser().getNickname());
        } else {
            tvName.setText(DataCenter.getInstance().getUserId());
        }
        tvZan.setText(DataCenter.getInstance().getUser().getLikesCount() + "");
        if (DataCenter.getInstance().getUser().getForestCoinCount_ls() != 0)
            tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "+" + DataCenter.getInstance().getUser().getForestCoinCount_ls());
        else
            tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "");
    }

    @OnClick({R.id.nice_iv0, R.id.iv_help_yy, R.id.iv_check_ta, R.id.iv_go_task_list})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.nice_iv0:
                Intent intent4 = new Intent(this, UserCenterActivity.class);
                startActivity(intent4);
                break;
            case R.id.iv_help_yy:
                Intent intent3 = new Intent(this, HelpYYActivity.class);
                startActivity(intent3);
                break;

            case R.id.iv_go_task_list:
                Intent intent = new Intent(this, TaskListActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_check_ta:
                Intent intent1 = new Intent(this, ClassTimetableActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onCurrentDateTask(CurrentDateTaskBean currentDateTaskBean) {
        if (currentDateTaskBean != null) {
            tAwaitConfirmCount = 0;
            yAwaitConfirmCount = 0;
            tvNowTime.setText(String.format("%s %s", currentDateTaskBean.getTodayTask().getCurrentDate(), Utils.getWeekString(currentDateTaskBean.getTodayTask().getCurrentDateWeek())));
            todayList = currentDateTaskBean.getTodayTask().getTaskList();
            if (todayList != null) {
                for (CurrentDateTaskBean.TaskBean.TaskListBean bean : todayList) {
                    if ("AWAITCONFIRM".equals(bean.getTaskFinishStatus())) {
                        tAwaitConfirmCount += 1;
                    }
                }
            }
            mTodayTaskAdapter.setData(todayList);
            if (tAwaitConfirmCount != 0) {
                tvToadyTaskHint.setVisibility(View.VISIBLE);
            } else tvToadyTaskHint.setVisibility(View.GONE);
            tvYsTime.setText(String.format("%s %s", currentDateTaskBean.getYesterdayTask().getCurrentDate(), Utils.getWeekString(currentDateTaskBean.getYesterdayTask().getCurrentDateWeek())));
            yesterdayList = currentDateTaskBean.getYesterdayTask().getTaskList();
            if (yesterdayList != null) {
                for (CurrentDateTaskBean.TaskBean.TaskListBean bean : yesterdayList) {
                    if ("AWAITCONFIRM".equals(bean.getTaskFinishStatus())) {
                        yAwaitConfirmCount += 1;
                    }
                }
            }
            mYesterdayTaskAdapter.setData(yesterdayList);
            if (yAwaitConfirmCount != 0) {
                tvYsTaskHint.setVisibility(View.VISIBLE);
            } else tvYsTaskHint.setVisibility(View.GONE);
            int acCount = tAwaitConfirmCount + yAwaitConfirmCount;
            if (acCount != 0) {
                DataCenter.getInstance().getUser().setForestCoinCount_ls(acCount);
                tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "+" + acCount);
            } else {
                DataCenter.getInstance().getUser().setForestCoinCount_ls(acCount);
                tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "");
            }
        }
    }

    Gson gson = new Gson();

    private String status;

    public void updateCurrentDateTask(int taskId, String status) {
        this.status = status;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("logTaskId", taskId);
        paramsMap.put("taskFinishStatus", status);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
        tasksPresenter.updateCurrentDateTask(body);
    }

    @Override
    public void onUpdateCurrentDateTask(HttpResult result) {
        if (result != null) {
            if ("0000".equals(result.getCode())) {
                if ("AWAITCONFIRM".equals(status))
                    SingleToast.showMsg("提交成功！");
                else SingleToast.showMsg("取消成功！");
                tasksPresenter.getCurrentDateTask();
            } else SingleToast.showMsg(result.getMsg());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tasksPresenter.onDestory();
    }

    @Override
    public void onUserInfo(UserInfoBean userInfoBean) {
        if (userInfoBean == null){
            return;
        }
        DataCenter.getInstance().getUser().setNickname(userInfoBean.getNickName());
        DataCenter.getInstance().getUser().setBirthday(userInfoBean.getBirthday());
        DataCenter.getInstance().getUser().setUserSex(userInfoBean.getUserSex());
        DataCenter.getInstance().getUser().setUserHobby(userInfoBean.getUserHobby());
        DataCenter.getInstance().getUser().setLikeGift(userInfoBean.getLikeGift());
        DataCenter.getInstance().getUser().setLikeCartoon(userInfoBean.getLikeCartoon());
        DataCenter.getInstance().getUser().setLikeIdol(userInfoBean.getLikeIdol());
        DataCenter.getInstance().getUser().setLikeGame(userInfoBean.getLikeGame());
        DataCenter.getInstance().getUser().setAvatarUrl(userInfoBean.getAvatarUrl());
        DataCenter.getInstance().getUser().setForestCoinCount(userInfoBean.getForestCoinCount());
        DataCenter.getInstance().getUser().setLikesCount(userInfoBean.getLikesCount());

        if (DataCenter.getInstance().getUser().getForestCoinCount_ls() != 0)
            tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "+" + DataCenter.getInstance().getUser().getForestCoinCount_ls());
        else
            tvAixin.setText(DataCenter.getInstance().getUser().getForestCoinCount() + "");
    }
}
