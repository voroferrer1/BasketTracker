package com.example.voro.baskettracker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.voro.baskettracker.Utils.DB;
import com.example.voro.baskettracker.Utils.SQLiteHelper;
import com.example.voro.baskettracker.Utils.Utils;
import com.example.voro.baskettracker.adapters.TeamSelectorAdapter;
import com.example.voro.baskettracker.entities.Team;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConstructorActivity extends AppCompatActivity {
    @BindView(R.id.text_top)
    TextView textTop;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_player_number)
    EditText editPlayerNumber;
    @BindView(R.id.layout_position)
    RelativeLayout layoutPosition;
    @BindView(R.id.layout_team)
    RelativeLayout layoutTeam;
    @BindView(R.id.text_position)
    TextView textPosition;
    @BindView(R.id.text_team)
    TextView textTeam;
    @BindView(R.id.button_save)
    Button buttonSave;
    @BindView(R.id.button_cancel)
    Button buttonCancel;



    private Dialog dialog;
    private boolean is_team;
    private Intent intent;
    private Context context;
    private SQLiteHelper database;
    private PositionAdapter positionAdapter;
    private TeamSelectorAdapter teamSelectorAdapter;
    private RecyclerView recyclerView;
    public int team_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constructor);
        ButterKnife.bind(this);

        context = this;
        intent = getIntent();
        database = new SQLiteHelper(context);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_selector);


        database.open();
        setLayout();
        setAdapter();
        setListeners();

    }

    private void setAdapter() {

        positionAdapter = new PositionAdapter(context, Utils.getPositions(), new PositionAdapter.OnClickListener() {
            @Override
            public void OnClick(int position) {
                textPosition.setText(Utils.getPositions().get(position).getName());
                dialog.dismiss();
            }
        });
        teamSelectorAdapter = new TeamSelectorAdapter(context, database.getTeams(), new TeamSelectorAdapter.OnClickListener() {
            @Override
            public void OnClick(int position,int team) {
                textTeam.setText(database.getTeams().get(position).getName());
                team_id = team;
                dialog.dismiss();
            }
        });
    }


    private void setLayout() {
        is_team = intent.getBooleanExtra("is_team", false);
        if (is_team) {
            editName.setHint(R.string.builder_team_name);
            editPlayerNumber.setVisibility(View.GONE);
            layoutPosition.setVisibility(View.GONE);
            layoutTeam.setVisibility(View.GONE);
        } else {
            editName.setHint(R.string.builder_player_name);
            editPlayerNumber.setVisibility(View.VISIBLE);
            layoutTeam.setVisibility(View.VISIBLE);
            layoutPosition.setVisibility(View.VISIBLE);
        }
    }
    private void setListeners() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_team){
                    String[][] data = {{DB.TEAM_NAME,editName.getText().toString().trim()},{DB.TEAM_POINTS,Integer.toString(0)}};
                    database.insert("Team",data);
                }else{
                    String[][] data = {{DB.PERSON_NAME,editName.getText().toString().trim()},{DB.PERSON_POSITION,textPosition.getText().toString()},{DB.PERSON_TEAM, Integer.toString(team_id)}};
                    database.insert("Person",data);
                }
                database.close();
                setResult(RESULT_OK);
                finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.close();
                finish();
            }
        });
        layoutPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView = dialog.findViewById(R.id.recycler_position);
                recyclerView.setAdapter(positionAdapter);
                dialog.show();
            }
        });
        layoutTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView = dialog.findViewById(R.id.recycler_position);
                recyclerView.setAdapter(teamSelectorAdapter);
                dialog.show();
            }
        });


    }

}
