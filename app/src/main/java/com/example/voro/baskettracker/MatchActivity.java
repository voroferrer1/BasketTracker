package com.example.voro.baskettracker;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    long timeWhenStopped;
    boolean isPaused;
    boolean wasStarted;
    public SQLiteHelper clientBD;
    List<Person> personList;
    List<Team> teamList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        ButterKnife.bind(this);

        personList = new ArrayList<>();
        teamList = new ArrayList<>();

        clientBD = new SQLiteHelper(this);
        chronoTime.setCountDown(true);
        chronoTime.setBase(SystemClock.elapsedRealtime() + ((10 * 60) * 1000));
        timeWhenStopped=0;
        clientBD.open();
        teamList.addAll(clientBD.getTeams());
        personList.addAll(clientBD.getPersons());
        clientBD.close();
        isPaused = true;
        wasStarted = false;


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPaused) {
                    timeWhenStopped = chronoTime.getBase() - SystemClock.elapsedRealtime();
                    chronoTime.stop();
                    isPaused = !isPaused;
                }else{
                    if(wasStarted){
                    chronoTime.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    }else{
                        chronoTime.setBase(SystemClock.elapsedRealtime() + ((10 * 60) * 1000));
                    }
                    chronoTime.start();
                    isPaused = !isPaused;
                    wasStarted = true;
                }
            }
        });

    }
}
