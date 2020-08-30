package com.dyibing.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyibing.myapp.R;
import com.dyibing.myapp.listener.OnMultiClickListener;
import com.dyibing.myapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AnswerAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private List<String> answers = new ArrayList<>();
    private String sort = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void setOnItemClickListener(String answer);
    }

    public AnswerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_answer, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String answer = answers.get(position);
        RecyclerHolder recyclerHolder = (RecyclerHolder) holder;

        Utils.setText(sort.charAt(position) + " " + answer, recyclerHolder.tvAnswer);
        holder.itemView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (null != onItemClickListener) {
                    onItemClickListener.setOnItemClickListener(answer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public void setData(List<String> answers) {
        if (this.answers != null) {
            this.answers.clear();
            this.answers.addAll(answers);
        } else {
            this.answers.clear();
        }
        notifyDataSetChanged();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_answer)
        TextView tvAnswer;

        private RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
