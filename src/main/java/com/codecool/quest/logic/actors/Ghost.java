package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Ghost extends Monster {

    public Ghost(Cell cell) {
        super(cell);
        this.setDamage(4);
        this.setHealth(15);
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

}

