package com.example.voro.baskettracker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.voro.baskettracker.R;
import com.example.voro.baskettracker.Utils.SQLiteHelper;
import com.example.voro.baskettracker.entities.Person;
import com.example.voro.baskettracker.entities.Team;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder>{



    private List<Person> personList;
    private Context context;
    private boolean isShown;
    private List<Team> teamList;
    private List<Team> temporalTeamList;

    private SQLiteHelper dbHelper;

    public PlayerAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList= personList;
    }

    @NonNull
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        dbHelper = new SQLiteHelper(context);
        teamList = new ArrayList<>();
        temporalTeamList = new ArrayList<>();
        dbHelper.open();
        isShown = false;
        temporalTeamList.addAll(dbHelper.getTeams());
        for (Team team : temporalTeamList) {
            if (team.getId() == (personList.get(position).getTeam())) {
                teamList.add(team);
            }
        }
        dbHelper.close();
        return new PlayerAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.iten_player, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerAdapter.ViewHolder viewHolder, int position) {
        viewHolder.textName.setText(personList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_player_name)
        TextView textName;
        @BindView(R.id.image_drop)
        ImageView imageDrop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
