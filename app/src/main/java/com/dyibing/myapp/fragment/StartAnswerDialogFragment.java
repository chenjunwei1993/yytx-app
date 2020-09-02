package com.dyibing.myapp.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.dyibing.myapp.R;
import com.dyibing.myapp.listener.OnDismissListener;
import com.dyibing.myapp.listener.OnMultiClickListener;
import com.dyibing.myapp.utils.Utils;

public class StartAnswerDialogFragment extends BaseDialogFragment {
    public static final String FIRST_ERROR_TYPE = "1";
    public static final String SECOND_ERROR_TYPE = "2";
    public static final String SECOND_OK_TYPE = "3";
    public static final String END_ANSWER_TYPE = "4";
    private String type;
    private String rightAnswer;
    private String forestCount = "1";

    public void setType(String type) {
        this.type = type;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setForestCount(String forestCount) {
        this.forestCount = forestCount;
    }

    private OnDismissListener onDismissListener;

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    protected View getContentView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_fragment_start_answer, null);
        LinearLayout ll_answer_tip = view.findViewById(R.id.ll_answer_tip);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ll_answer_tip.getLayoutParams();
        lp.width = (int) (ScreenUtils.getScreenWidth() * 0.8);
        lp.height = (int) (ScreenUtils.getScreenWidth() * 0.8 * 0.8);
        ll_answer_tip.setLayoutParams(lp);

        TextView tv_answer_tip = view.findViewById(R.id.tv_answer_tip);
        LinearLayout ll_answer_forest_tip = view.findViewById(R.id.ll_answer_forest_tip);
        ImageView iv_answer_tip = view.findViewById(R.id.iv_answer_tip);
        if (TextUtils.equals(type, FIRST_ERROR_TYPE)) {
            Utils.setText(getString(R.string.answer_first_tip), tv_answer_tip);
            ll_answer_forest_tip.setVisibility(View.GONE);
            iv_answer_tip.setImageResource(R.drawable.ic_people4);
        } else if (TextUtils.equals(type, SECOND_ERROR_TYPE)) {
            Utils.setText(getString(R.string.answer_second_error_tip, rightAnswer), tv_answer_tip);
            ll_answer_forest_tip.setVisibility(View.GONE);
            iv_answer_tip.setImageResource(R.drawable.ic_people5);
        } else if (TextUtils.equals(type, SECOND_OK_TYPE)) {
            Utils.setText(getString(R.string.answer_second_ok_tip), tv_answer_tip);
            ll_answer_forest_tip.setVisibility(View.VISIBLE);
            iv_answer_tip.setImageResource(R.drawable.ic_people4);
            ll_answer_forest_tip.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View v) {
                    dismiss();
                    if (null != onDismissListener) {
                        onDismissListener.setOnDismissListener();
                    }
                }
            });
        } else if (TextUtils.equals(type, END_ANSWER_TYPE)) {
            Utils.setText(getString(R.string.end_answer_tip, forestCount), tv_answer_tip);
            iv_answer_tip.setImageResource(R.drawable.ic_people4);
            ll_answer_forest_tip.setVisibility(View.GONE);
        }

        view.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                dismiss();
                if (null != onDismissListener) {
                    onDismissListener.setOnDismissListener();
                }
            }
        });

        return view;
    }
}
