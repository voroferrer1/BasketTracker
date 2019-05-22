package com.example.voro.baskettracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.voro.baskettracker.Utils.DB;
import com.example.voro.baskettracker.Utils.SQLiteHelper;
import com.example.voro.baskettracker.entities.Person;
import com.example.voro.baskettracker.entities.Team;

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
    Intent intent;
    Context context;



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
    private void setData(){
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
                intent = new Intent(context, MatchActivity.class);
                startActivity(intent);
            }
        });
        buttonTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context,TeamBuilderActivity.class);
                startActivity(intent);
            }
        });
    }
}
