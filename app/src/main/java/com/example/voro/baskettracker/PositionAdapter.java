package com.example.voro.baskettracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.ViewHolder> {


    private Context context;
    private List<Position> positionList;
    private Position position;
    private OnClickListener onClickListener;


    public PositionAdapter(Context context, List<Position> positionList, OnClickListener onClickListener) {
        this.context = context;
        this.positionList = positionList;
        this.onClickListener = onClickListener;
    }


    @NonNull

    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_selector, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final @NonNull ViewHolder holder, int i) {
        position = positionList.get(holder.getAdapterPosition());
        holder.textName.setText(position.getName());
        holder.layoutPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.OnClick(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return positionList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_name)
        TextView textName;
        @BindView(R.id.layout_position)
        LinearLayout layoutPosition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface OnClickListener {
        void OnClick(int position);
    }
}