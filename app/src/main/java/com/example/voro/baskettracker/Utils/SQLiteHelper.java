package com.example.voro.baskettracker.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.voro.baskettracker.entities.Match;
import com.example.voro.baskettracker.entities.Person;
import com.example.voro.baskettracker.entities.Team;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper {
    private Context context;
    private static DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public SQLiteHelper(Context context) {
        this.context = context;
    }


    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(@Nullable Context context, @Nullable String name, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            createTables(sqLiteDatabase);
            fillTables(sqLiteDatabase);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            deleteTables(sqLiteDatabase);
            createTables(sqLiteDatabase);
            fillTables(sqLiteDatabase);
        }

        private void createTables(SQLiteDatabase database) {
            database.execSQL(DB.CREATE_TABLE_PERSON);
            database.execSQL(DB.CREATE_TABLE_TEAM);
            database.execSQL(DB.CREATE_TABLE_MATCH);
        }

        private void fillTables(SQLiteDatabase database) {
            database.execSQL(DB.FILL_TABLE_PERSON);
            database.execSQL(DB.FILL_TABLE_TEAM_LOCAL);
            database.execSQL(DB.FILL_TABLE_TEAM_VISITOR);
            database.execSQL(DB.FILL_TABLE_MATCH);
        }

        private void deleteTables(SQLiteDatabase database) {
            database.execSQL(DB.DROP_TABLE_PERSON);
            database.execSQL(DB.DROP_TABLE_TEAM);
            database.execSQL(DB.DROP_TABLE_MATCH);
        }

    }

    public SQLiteHelper open() {
        dataBaseHelper = new DataBaseHelper(context, DB.DATABASE_NAME, DB.DATABASE_VERSION);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public Cursor getData(String options,@Nullable String[] selection) {
        return database.rawQuery(options,selection);
    }

    public long insert(String table, String[][] data) {
        ContentValues contentValues = new ContentValues();
        for (String[] field : data) {
            contentValues.put(field[0], field[1]);
        }

        return database.insert(table, null, contentValues);
    }
    public List<Person> getPersons() {
        List<Person> personList = new ArrayList<>();
        Cursor cursor = getData("select * from Person", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Person person = new Person();
                person.setName(cursor.getString(cursor.getColumnIndex(DB.PERSON_NAME)));
                person.setDorsal(cursor.getInt(cursor.getColumnIndex(DB.PERSON_NUMBER)));
                person.setPoints(cursor.getInt(cursor.getColumnIndex(DB.PERSON_POINTS)));
                person.setFaults(cursor.getInt(cursor.getColumnIndex(DB.PERSON_FAULT)));
                person.setPosition(cursor.getString(cursor.getColumnIndex(DB.PERSON_POSITION)));
                person.setTeam(cursor.getInt(cursor.getColumnIndex(DB.PERSON_TEAM)));
                person.setId(cursor.getInt(cursor.getColumnIndex(DB.PERSON_ID)));
                personList.add(person);
                cursor.moveToNext();
            }
        }
        return personList;
    }


    public List<Team> getTeams() {
        List<Team> teamList = new ArrayList<>();
        Cursor cursor = getData("select * from Team", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Team team = new Team();
                team.setName(cursor.getString(cursor.getColumnIndex(DB.TEAM_NAME)));
                team.setId(cursor.getInt(cursor.getColumnIndex(DB.TEAM_ID)));

                teamList.add(team);
                cursor.moveToNext();
            }
        }
        return teamList;
    }
    public void insertPerson(Person person) {
        insert(DB.PERSON_TABLE_NAME,
                new String[][]{
                        {DB.PERSON_NAME, person.getName()},
                        {DB.PERSON_NUMBER, Integer.toString(person.getDorsal())},
                        {DB.PERSON_FAULT, Integer.toString(person.getFaults())},
                        {DB.PERSON_POINTS, Integer.toString(person.getPoints())},
                        {DB.PERSON_TEAM, Integer.toString(person.getTeam())},
                        {DB.PERSON_POSITION, person.getPosition()}
                }
        );
    }
    public void insertMatch(Match match){
        insert(DB.MATCH_TABLE_NAME,new String[][]{
                {DB.MATCH_FAULTS_LOCAL,Integer.toString(match.getLocal_faults())},
                {DB.MATCH_FAULTS_VISITOR,Integer.toString(match.getVisitor_faults())},
                {DB.MATCH_POINTS_LOCAL,Integer.toString(match.getLocal_points())},
                {DB.MATCH_POINTS_VISITOR,Integer.toString(match.getVisitor_points())},
                {DB.MATCH_TEAM_LOCAL,Integer.toString(match.getLocal_team())},
                {DB.MATCH_TEAM_VISITOR,Integer.toString(match.getVisiton_team())}
        });
    }
}
