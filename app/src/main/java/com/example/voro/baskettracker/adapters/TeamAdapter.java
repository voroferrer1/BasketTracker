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

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {



    private List<Team> teamList;
    private Context context;
    private boolean isShown;
    private List<Person> personList;
    private List<Person> temporalpersonList;

    private SQLiteHelper dbHelper;
    PlayerAdapter playerAdapter;

    public TeamAdapter(Context context, List<Team> teamList) {
        this.context = context;
        this.teamList = teamList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        dbHelper = new SQLiteHelper(context);
        personList = new ArrayList<>();
        temporalpersonList = new ArrayList<>();
        dbHelper.open();
        isShown = false;
        temporalpersonList.addAll(dbHelper.getPersons());
        dbHelper.close();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_team, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        personList = new ArrayList<>();
        for (Person person : temporalpersonList) {
            if (person.getTeam() == (teamList.get(position).getId())) {
                personList.add(person);
            }
        }
        viewHolder.textName.setText(teamList.get(position).getName());
        if(personList.size()!=0){
            playerAdapter = new PlayerAdapter(context,personList);
            viewHolder.recyclerTeamPlayers.setAdapter(playerAdapter);
        }
        viewHolder.imageDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShown){
                    viewHolder.layoutDrop.setVisibility(View.GONE);
                    isShown = false;
                }else{
                    viewHolder.layoutDrop.setVisibility(View.VISIBLE);
                    isShown = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_team_name)
        TextView textName;
        @BindView(R.id.image_drop)
        ImageView imageDrop;
        @BindView(R.id.recycler_team_players)
        RecyclerView recyclerTeamPlayers;
        @BindView(R.id.text_new_player)
        TextView textNewPlayer;
        @BindView(R.id.layout_drop)
        RelativeLayout layoutDrop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
