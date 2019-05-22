package com.example.voro.baskettracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.voro.baskettracker.Utils.RequestCodes;
import com.example.voro.baskettracker.Utils.SQLiteHelper;
import com.example.voro.baskettracker.adapters.PlayerAdapter;
import com.example.voro.baskettracker.adapters.TeamAdapter;
import com.example.voro.baskettracker.entities.Person;
import com.example.voro.baskettracker.entities.Team;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.voro.baskettracker.Utils.RequestCodes.REQUEST_PLAYER_CONSTRUCTOR;
import static com.example.voro.baskettracker.Utils.RequestCodes.REQUEST_TEAM_CONSTRUCTOR;

public class TeamBuilderActivity extends AppCompatActivity {
    @BindView(R.id.text_top)
    TextView textTop;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.button_add)
    Button buttonAdd;

    @BindView(R.id.recycler_team)
    RecyclerView recyclerTeam;
    @BindView(R.id.recycler_player)
    RecyclerView recyclerPlayer;

    Context context;

    SQLiteHelper clientBD;
    List<Team> teamList;
    List<Person> personList;

    TeamAdapter teamAdapter;
    PlayerAdapter playerAdapter;
    Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_builder);
        ButterKnife.bind(this);
        context = this;

        setData();
        setLayout();
        setAdapters();
        setListeners();
    }



    private void setData() {
        clientBD = new SQLiteHelper(this);


        clientBD.open();

        teamList = new ArrayList<>();
        personList = new ArrayList<>();

        teamList.addAll(clientBD.getTeams());
        personList.addAll(clientBD.getPersons());

        clientBD.close();
    }

    private void setLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Equipos"));
        tabLayout.addTab(tabLayout.newTab().setText("Jugadores"));
        setAdapters();
    }

    private void setAdapters() {
        teamAdapter = new TeamAdapter(context, teamList);
        recyclerTeam.setAdapter(teamAdapter);
        playerAdapter = new PlayerAdapter(context,personList);
        recyclerPlayer.setAdapter(playerAdapter);

    }

    private void setListeners() {
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    recyclerTeam.setVisibility(View.VISIBLE);
                    recyclerPlayer.setVisibility(View.GONE);
                } else {
                    recyclerTeam.setVisibility(View.GONE);
                    recyclerPlayer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tabLayout.getSelectedTabPosition() == 0){
                    intent = new Intent(context,ConstructorActivity.class);
                    intent.putExtra("is_team", true);
                    startActivityForResult(intent, REQUEST_TEAM_CONSTRUCTOR);
                }else{
                    intent = new Intent(context,ConstructorActivity.class);
                    intent.putExtra("is_team", false);
                    startActivityForResult(intent, REQUEST_PLAYER_CONSTRUCTOR);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_TEAM_CONSTRUCTOR){
            if(resultCode==RESULT_OK){
                clientBD.open();
                teamList.clear();
                teamList.addAll(clientBD.getTeams());
                clientBD.close();
            }
            teamAdapter.notifyDataSetChanged();
        }else{
            if(resultCode==RESULT_OK){
                clientBD.open();
                personList.clear();
                personList.addAll(clientBD.getPersons());
                clientBD.close();
            }
            playerAdapter.notifyDataSetChanged();
            teamAdapter.notifyDataSetChanged();
        }
    }
}
