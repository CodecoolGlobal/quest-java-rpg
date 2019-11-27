package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Player extends Actor {
    private String tileName = "player";

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        setTileName();
        return tileName;
    }

    public void setTileName() {
        if (inventoryMap.containsKey("weapon")) {
            this.tileName = "player2";
        }
    }
}

