package com.example.voro.baskettracker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.voro.baskettracker.Position;
import com.example.voro.baskettracker.R;
import com.example.voro.baskettracker.entities.Team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamSelectorAdapter extends RecyclerView.Adapter<TeamSelectorAdapter.ViewHolder>{


    private Context context;
    private List<Team> teamList;
    private Team team;
    private TeamSelectorAdapter.OnClickListener onClickListener;


    public TeamSelectorAdapter(Context context, List<Team> teamListList, TeamSelectorAdapter.OnClickListener onClickListener) {
        this.context = context;
        this.teamList = teamListList;
        this.onClickListener = onClickListener;
    }


    @NonNull

    @Override

    public TeamSelectorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_selector, viewGroup, false);
        return new TeamSelectorAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final @NonNull TeamSelectorAdapter.ViewHolder holder, int i) {
        team = teamList.get(holder.getAdapterPosition());
        holder.textName.setText(team.getName());
        holder.layoutPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.OnClick(holder.getAdapterPosition(),team.getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return teamList.size();
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
        void OnClick(int position,int team_id);
    }
}