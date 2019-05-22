package com.example.voro.baskettracker.Utils;

import com.example.voro.baskettracker.Position;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Position> getPositions() {
        List<Position> positionList = new ArrayList<>();
        Position position = new Position();
        position.setName("Base");
        positionList.add(position);
        position = new Position();
        position.setName("Alero");
        positionList.add(position);
        position = new Position();
        position.setName("Escolta");
        positionList.add(position);
        position = new Position();
        position.setName("Ala-Pívot");
        positionList.add(position);
        position = new Position();
        position.setName("Pívot");
        positionList.add(position);
        position = new Position();
        position.setName("Entrenador");
        positionList.add(position);
        return positionList;
    }
}
