package com.example.voro.baskettracker.entities;

public class Match {
    int local_team;
    int visiton_team;
    int local_points;
    int visitor_points;
    int local_faults;
    int visitor_faults;

    public int getLocal_team() {
        return local_team;
    }

    public void setLocal_team(int local_team) {
        this.local_team = local_team;
    }

    public int getVisiton_team() {
        return visiton_team;
    }

    public void setVisiton_team(int visiton_team) {
        this.visiton_team = visiton_team;
    }

    public int getLocal_points() {
        return local_points;
    }

    public void setLocal_points(int local_points) {
        this.local_points = local_points;
    }

    public int getVisitor_points() {
        return visitor_points;
    }

    public void setVisitor_points(int visitor_points) {
        this.visitor_points = visitor_points;
    }

    public int getLocal_faults() {
        return local_faults;
    }

    public void setLocal_faults(int local_faults) {
        this.local_faults = local_faults;
    }

    public int getVisitor_faults() {
        return visitor_faults;
    }

    public void setVisitor_faults(int visitor_faults) {
        this.visitor_faults = visitor_faults;
    }
}
