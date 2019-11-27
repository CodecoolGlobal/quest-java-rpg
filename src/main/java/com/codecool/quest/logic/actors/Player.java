package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Player extends Actor {
    private String tileName = "player";

    public Player(Cell cell) {
        super(cell);
        this.setDamage(5);
    }

    public String getTileName() {
        setTileName();
        return tileName;
    }

    public void setTileName() {
        if (inventoryMap.containsKey("weapon") && !inventoryMap.containsKey("helmet")) {
            this.tileName = "player-w";
        } else if (inventoryMap.containsKey("weapon") && inventoryMap.containsKey("helmet")) {
            this.tileName = "player-w-h";
        } else if (!inventoryMap.containsKey("weapon") && inventoryMap.containsKey("helmet")) {
            this.tileName = "player-h";
        }
    }

}

