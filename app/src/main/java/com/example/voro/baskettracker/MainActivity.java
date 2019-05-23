package com.example.voro.baskettracker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.voro.baskettracker.Utils.DB;
import com.example.voro.baskettracker.Utils.SQLiteHelper;
import com.example.voro.baskettracker.adapters.TeamSelectorAdapter;
import com.example.voro.baskettracker.entities.Person;
import com.example.voro.baskettracker.entities.Team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button_start)
    Button buttonStart;
    @BindView(R.id.button_teams)
    Button buttonTeams;


    SQLiteHelper clientBD;
    //C:\Users\Usuario\AppData\Local\Android\Sdk\platform-tools
    List<Person> personList;
    List<Team> teamList;
    List<Team> visitorTeamList;
    List<Team> localTeamList;
    Team localTeam;
    Team visitorTeam;
    Intent intent;
    Context context;
    TeamSelectorAdapter localAdapter;
    TeamSelectorAdapter visitorAdapter;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        clientBD = new SQLiteHelper(this);

        setData();
        setOnClicks();
    }

    private void setData() {
        personList = new ArrayList<>();
        teamList = new ArrayList<>();

        clientBD.open();
        personList.addAll(clientBD.getPersons());
        teamList.addAll(clientBD.getTeams());
        clientBD.close();
    }

    private void setOnClicks() {
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog();
            }
        });
        buttonTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context, TeamBuilderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setDialog() {
        localTeamList = new ArrayList<>();
        visitorTeamList = new ArrayList<>();
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_new_match);
        localTeamList.addAll(teamList);
        visitorTeamList.addAll(teamList);

        Button save = dialog.findViewById(R.id.button_save);
        Button cancel = dialog.findViewById(R.id.button_cancel);


        setTeamAdapters();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MatchActivity.class);
                if (localTeam != null && visitorTeam != null) {
                    intent.putExtra("local", localTeam);
                    intent.putExtra("visitor", visitorTeam);
                    dialog.dismiss();
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Debes seleccionar los equipos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        RecyclerView recyclerLocal = dialog.findViewById(R.id.recycler_team_local);
        recyclerLocal.setAdapter(localAdapter);

        RecyclerView recyclerVisitor = dialog.findViewById(R.id.recycler_team_vistor);
        recyclerVisitor.setAdapter(visitorAdapter);


        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 650);

        dialog.show();

    }

    private void setTeamAdapters() {
        final TextView visitor = dialog.findViewById(R.id.text_team_vistor);
        final TextView local = dialog.findViewById(R.id.text_team_local);
        visitorAdapter = new TeamSelectorAdapter(context, visitorTeamList, new TeamSelectorAdapter.OnClickListener() {
            @Override
            public void OnClick(int position, int team_id) {
                if(visitorTeam!=null){
                    visitorTeamList.add(visitorTeam);
                    localTeamList.add(visitorTeam);
                }
                boolean contained = false;
                for (Team team : visitorTeamList) {
                    if (team.getId() == team_id) {
                        visitorTeam = team;
                        contained = true;
                    }
                }
                if (contained) {
                    visitor.setText(visitorTeam.getName());

                    localTeamList.remove(visitorTeam);
                    visitorTeamList.remove(visitorTeam);

                    visitorAdapter.notifyDataSetChanged();
                    localAdapter.notifyDataSetChanged();
                }
//                if(!contained){
//                    localTeamList.add(visitorTeam);
//                    visitorAdapter.notifyDataSetChanged();
//                    localAdapter.notifyDataSetChanged();
//                }
            }
        });
        localAdapter = new TeamSelectorAdapter(context, localTeamList, new TeamSelectorAdapter.OnClickListener() {
            @Override
            public void OnClick(int position, int team_id) {
                if(localTeam != null){
                    visitorTeamList.add(localTeam);
                    localTeamList.add(localTeam);
                }
                boolean found = false;
                boolean contained = false;
                for (Team team : localTeamList) {
                    if (team.getId() == team_id) {
                        localTeam = team;
                        found = true;
                    }
                }
                if (found) {
                    local.setText(localTeam.getName());
                    localTeamList.remove(localTeam);
                    visitorTeamList.remove(localTeam);
                    visitorAdapter.notifyDataSetChanged();
                    localAdapter.notifyDataSetChanged();
                }
//                if(!contained){
//                    visitorTeamList.add(localTeam);
//                    visitorAdapter.notifyDataSetChanged();
//                    localAdapter.notifyDataSetChanged();
//                }
            }
        });
    }
}
