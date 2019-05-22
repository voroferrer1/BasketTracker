package com.example.voro.baskettracker.Utils;

public class DB {
    public static final String DATABASE_NAME = "BasketTracker.db";
    public static final int DATABASE_VERSION = 1;

    //Person table
    public static final String PERSON_TABLE_NAME = "Person";
    public static final String PERSON_ID = "person_id";
    public static final String PERSON_NAME = "name";
    public static final String PERSON_NUMBER = "dorsal";
    public static final String PERSON_FAULT = "faults";
    public static final String PERSON_POINTS = "points";
    public static final String PERSON_TEAM = "team";
    public static final String PERSON_POSITION = "position";

    //TEAM
    public static final String TEAM_TABLE_NAME = "Team";
    public static final String TEAM_ID = "team_id";
    public static final String TEAM_NAME = "name";
    public static final String TEAM_POINTS = "points";


    //MATCH
    public static final String MATCH_TABLE_NAME = "Match";
    public static final String MATCH_ID = "match_id";
    public static final String MATCH_TEAM_LOCAL = "local";
    public static final String MATCH_TEAM_VISITOR = "visitor";
    public static final String MATCH_POINTS_LOCAL = "local_points";
    public static final String MATCH_POINTS_VISITOR = "visitor_points";
    public static final String MATCH_FAULTS_LOCAL = "local_faults";
    public static final String MATCH_FAULTS_VISITOR = "visitor_faults";

    //CREATE TABLES
    public static final String CREATE_TABLE_PERSON = String.format(
            "CREATE TABLE IF NOT EXISTS %s("+ //Table name
            "%s INTEGER PRIMARY KEY AUTOINCREMENT,"+ // ID Person
            "%s TEXT NOT NULL,"+ // Name
            "%s INTEGER,"+ // Number
            "%s INTEGER,"+ // Faults
            "%s INTEGER,"+ // Points
            "%s INTEGER NOT NULL,"+ //Team
            "%s TEXT NOT NULL)", //Position
            PERSON_TABLE_NAME,
            PERSON_ID,
            PERSON_NAME,
            PERSON_NUMBER,
            PERSON_FAULT,
            PERSON_POINTS,
            PERSON_TEAM,
            PERSON_POSITION
    );

    public static final String CREATE_TABLE_TEAM = String.format(
            "CREATE TABLE IF NOT EXISTS %s("+ //Table name
            "%s INTEGER PRIMARY KEY AUTOINCREMENT,"+ //ID Team
            "%s TEXT NOT NULL,"+ //Name
            "%s INTEGER NOT NULL)", //Points
            TEAM_TABLE_NAME,
            TEAM_ID,
            TEAM_NAME,
            TEAM_POINTS
    );

    public static final String CREATE_TABLE_MATCH = String.format(
            "CREATE TABLE IF NOT EXISTS %s("+ //Table name
            "%s INTEGER PRIMARY KEY AUTOINCREMENT,"+ //ID Match
            "%s INTEGER NOT NULL,"+ //ID local team
            "%s INTEGER NOT NULL,"+ //ID visitor team
            "%s INTEGER NOT NULL,"+ //local points
            "%s INTEGER NOT NULL,"+ //visitor points
            "%s INTEGER NOT NULL,"+ //local faults
            "%s INTEGER NOT NULL)", //visitor faults
            MATCH_TABLE_NAME,
            MATCH_ID,
            MATCH_TEAM_LOCAL,
            MATCH_TEAM_VISITOR,
            MATCH_POINTS_LOCAL,
            MATCH_POINTS_VISITOR,
            MATCH_FAULTS_LOCAL,
            MATCH_FAULTS_VISITOR
    );

    //FILL TABLES DEFAULT
    public static final String FILL_TABLE_PERSON = String.format(
      "INSERT INTO %s(%s,%s,%s,%s,%s,%s) VALUES"+
              "('FIRST',0,0,0,1,'DEFAULT')",
            PERSON_TABLE_NAME,
            PERSON_NAME,
            PERSON_NUMBER,
            PERSON_FAULT,
            PERSON_POINTS,
            PERSON_TEAM,
            PERSON_POSITION
    );

    public static final String FILL_TABLE_TEAM_LOCAL = String.format(
            "INSERT INTO %s(%s,%s) VALUES"+
            "('TEST_TEAM_1',0)",
            TEAM_TABLE_NAME,
            TEAM_NAME,
            TEAM_POINTS
    );
    public static final String FILL_TABLE_TEAM_VISITOR = String.format(
            "INSERT INTO %s(%s,%s) VALUES"+
                    "('TEST_TEAM_2',0)",
            TEAM_TABLE_NAME,
            TEAM_NAME,
            TEAM_POINTS
    );

    public static final String FILL_TABLE_MATCH = String.format(
            "INSERT INTO %s(%s,%s,%s,%s,%s,%s) VALUES"+
                    "(0,1,0,0,0,0)",
            MATCH_TABLE_NAME,
            MATCH_TEAM_LOCAL,
            MATCH_TEAM_VISITOR,
            MATCH_POINTS_LOCAL,
            MATCH_POINTS_VISITOR,
            MATCH_FAULTS_LOCAL,
            MATCH_FAULTS_VISITOR
    );

    public static final String DROP_TABLE_PERSON = String.format("DROP TABLE IF EXISTS %s",PERSON_TABLE_NAME);
    public static final String DROP_TABLE_TEAM = String.format("DROP TABLE IF EXISTS %s",TEAM_TABLE_NAME);
    public static final String DROP_TABLE_MATCH = String.format("DROP TABLE IF EXISTS %s",MATCH_TABLE_NAME);





}
