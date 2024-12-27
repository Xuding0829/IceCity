package com.example.drink_order_system;

//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Msg> msgList;

    public MsgAdapter(List<Msg> msgList) {
        this.msgList = msgList;
    }

    // 左侧消息ViewHolder
    public static class LeftViewHolder extends RecyclerView.ViewHolder {
        TextView leftMsg;

        public LeftViewHolder(View itemView) {
            super(itemView);
            leftMsg = itemView.findViewById(R.id.leftMsg);
        }
    }

    // 右侧消息ViewHolder
    public static class RightViewHolder extends RecyclerView.ViewHolder {
        TextView rightMsg;

        public RightViewHolder(View itemView) {
            super(itemView);
            rightMsg = itemView.findViewById(R.id.rightMsg);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Msg msg = msgList.get(position);
        return msg.type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Msg.TYPE_RECEIVED) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_left_item, parent, false);
            return new LeftViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_right_item, parent, false);
            return new RightViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        if (holder instanceof LeftViewHolder) {
            ((LeftViewHolder) holder).leftMsg.setText(msg.content);
        } else if (holder instanceof RightViewHolder) {
            ((RightViewHolder) holder).rightMsg.setText(msg.content);
        } else {
            throw new IllegalArgumentException("Unknown ViewHolder type");
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
