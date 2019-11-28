package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Ghost extends Actor {

    public Ghost(Cell cell) {
        super(cell);
        this.setDamage(4);
    }

    @Override
    public String getTileName() {
        return "ghost";
    }




}

