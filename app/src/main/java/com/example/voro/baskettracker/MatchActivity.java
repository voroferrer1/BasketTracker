package com.example.voro.baskettracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.voro.baskettracker.Utils.SQLiteHelper;
import com.example.voro.baskettracker.entities.Person;
import com.example.voro.baskettracker.entities.Team;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchActivity extends AppCompatActivity {
    @BindView(R.id.text_local)
    TextView textLocal;
    @BindView(R.id.chrono_time)
    Chronometer chronoTime;
    @BindView(R.id.text_visitor)
    TextView textVisitor;
    @BindView(R.id.button_start)
    Button buttonStart;
    @BindView(R.id.button_reset)
    Button buttonReset;
    @BindView(R.id.button_edit)
    Button buttonEdit;
    @BindView(R.id.text_points_local)
    TextView textPointsLocal;
    @BindView(R.id.text_points_visitor)
    TextView textPointsVisitor;
    @BindView(R.id.recycler_local_players)
    RecyclerView recyclerLocalPlayers;
    @BindView(R.id.recycler_visitor_players)
    RecyclerView recyclerVisitorPlayers;


    int pointsVisitor;
    int pointsLocal;
    long timeWhenStopped;
    boolean isPaused;
    boolean wasStarted;
    public SQLiteHelper clientBD;
    List<Person> playerList;
    List<Person> localPlayers;
    List<Person> visitorPlayers;
    Intent intent;
    List<Team> teamList;
    Team teamVisitor;
    Team teamLocal;
    Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        ButterKnife.bind(this);
        hideUI();

        playerList = new ArrayList<>();
        teamList = new ArrayList<>();
        context = this;

        pointsVisitor = 0;
        pointsLocal = 0;

        intent = getIntent();
        teamLocal = (Team) intent.getSerializableExtra("local");
        teamVisitor = (Team) intent.getSerializableExtra("visitor");

        clientBD = new SQLiteHelper(this);

        setData();
        setLayout();
        setListeners();
        setReyclers();

    }


    private void setListeners() {

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPaused) {
                    timeWhenStopped = chronoTime.getBase() - SystemClock.elapsedRealtime();
                    chronoTime.stop();
                    buttonStart.setText("Resume");
                    isPaused = !isPaused;
                } else {
                    if (wasStarted) {
                        chronoTime.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    } else {
                        chronoTime.setBase(SystemClock.elapsedRealtime() + ((10 * 60) * 1000));
                    }
                    buttonStart.setText("Pause");
                    chronoTime.start();
                    isPaused = !isPaused;
                    wasStarted = true;
                }
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronoTime.stop();
                setLayout();
                setData();
            }
        });
    }

    private void setData() {
        clientBD.open();
        teamList.addAll(clientBD.getTeams());
        playerList.addAll(clientBD.getPersons());
        setTeamPlayers();
        clientBD.close();
        chronoTime.setCountDown(true);
        chronoTime.setBase(SystemClock.elapsedRealtime() + ((10 * 60) * 1000));
        timeWhenStopped = 0;

        chronoTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (chronometer.getText().toString().equals("00:00")) {
                    chronometer.stop();
                    buttonStart.setText("START");
                    isPaused = true;
                    wasStarted = false;

                }
            }
        });


        isPaused = true;
        wasStarted = false;

        pointsLocal = 0;
        pointsVisitor = 0;
    }

    private void setTeamPlayers() {
        visitorPlayers = new ArrayList<>();
        localPlayers = new ArrayList<>();
        for (Person person : playerList) {
            if (person.getTeam() == teamVisitor.getId()) {
                visitorPlayers.add(person);
            }
            if (person.getTeam() == teamLocal.getId()) {
                localPlayers.add(person);
            }
        }
    }

    private void setLayout() {
        buttonStart.setText("Start");
        textLocal.setText(teamLocal.getName());
        textVisitor.setText(teamVisitor.getName());
        textPointsLocal.setText(Integer.toString(pointsLocal));
        textPointsVisitor.setText(Integer.toString(pointsVisitor));

    }

    private void hideUI() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void setReyclers() {
        AdapterCurrentPlayers adapterCurrentLocalPlayers = new AdapterCurrentPlayers(localPlayers, context, teamLocal, true, new AdapterCurrentPlayers.SelectedPlayer() {
            @Override
            public void change(Person person) {

            }

            @Override
            public void add() {
                if (pointsLocal >= 0) {
                    pointsLocal++;
                    textPointsLocal.setText(Integer.toString(pointsLocal));
                }
            }

            @Override
            public void remove() {
                if (pointsLocal >= 0) {
                    pointsLocal--;
                    textPointsLocal.setText(Integer.toString(pointsLocal));
                }
            }
        });
        AdapterCurrentPlayers adapterCurrentVisitorPlayers = new AdapterCurrentPlayers(visitorPlayers, context, teamVisitor, false, new AdapterCurrentPlayers.SelectedPlayer() {
            @Override
            public void change(Person person) {

            }

            @Override
            public void add() {
                if (pointsVisitor >= 0) {
                    pointsVisitor++;
                    textPointsVisitor.setText(Integer.toString(pointsVisitor));
                }
            }

            @Override
            public void remove() {
                if (pointsVisitor >= 0) {
                    pointsVisitor--;
                    textPointsVisitor.setText(Integer.toString(pointsVisitor));
                }
            }
        });
        recyclerLocalPlayers.setAdapter(adapterCurrentLocalPlayers);
        recyclerVisitorPlayers.setAdapter(adapterCurrentVisitorPlayers);

    }
}
