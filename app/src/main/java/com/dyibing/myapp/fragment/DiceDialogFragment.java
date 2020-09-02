package com.dyibing.myapp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.dyibing.myapp.R;

import java.util.Random;
import java.util.logging.Handler;

public class DiceDialogFragment extends BaseDialogFragment {
    public interface OnDiceStepListener {
        void setOnDiceStepListener(int stepCount);
    }

    private OnDiceStepListener onDiceStepListener;

    public void setOnDiceStepListener(OnDiceStepListener onDiceStepListener) {
        this.onDiceStepListener = onDiceStepListener;
    }

    @Override
    protected View getContentView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_fragment_dice, null);
        //按照比例布局 适配
        LinearLayout ll_dice = view.findViewById(R.id.ll_dice);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ll_dice.getLayoutParams();
        lp.width = (int) (ScreenUtils.getScreenWidth() * 0.6);
        lp.height = (int) (ScreenUtils.getScreenWidth() * 0.6 * 0.91);
        ll_dice.setLayoutParams(lp);

        ImageView iv_dice = view.findViewById(R.id.iv_dice);
        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) iv_dice.getLayoutParams();
        lp2.width = (int) (ScreenUtils.getScreenWidth() * 0.38);
        lp2.height = (int) (ScreenUtils.getScreenWidth() * 0.38 * 1.07);
        iv_dice.setLayoutParams(lp2);

        TextView tv_dice_tip = view.findViewById(R.id.tv_dice_tip);
        setDiceView(iv_dice, tv_dice_tip);

        return view;
    }

    private void setDiceView(ImageView iv_dice, TextView tv_dice_tip) {
        int random = new Random().nextInt(6);
        if (null != onDiceStepListener) {
            onDiceStepListener.setOnDiceStepListener(random + 1);
        }
        switch (random) {
            case 0:
                iv_dice.setImageResource(R.drawable.ic_dice_one);
                tv_dice_tip.setText(getString(R.string.dice_step, "1"));
                break;
            case 1:
                iv_dice.setImageResource(R.drawable.ic_dice_two);
                tv_dice_tip.setText(getString(R.string.dice_step, "2"));
                break;
            case 2:
                iv_dice.setImageResource(R.drawable.ic_dice_three);
                tv_dice_tip.setText(getString(R.string.dice_step, "3"));
                break;
            case 3:
                iv_dice.setImageResource(R.drawable.ic_dice_four);
                tv_dice_tip.setText(getString(R.string.dice_step, "4"));
                break;
            case 4:
                iv_dice.setImageResource(R.drawable.ic_dice_fif);
                tv_dice_tip.setText(getString(R.string.dice_step, "5"));
                break;
            case 5:
                iv_dice.setImageResource(R.drawable.ic_dice_six);
                tv_dice_tip.setText(getString(R.string.dice_step, "6"));
                break;
        }
    }
}
