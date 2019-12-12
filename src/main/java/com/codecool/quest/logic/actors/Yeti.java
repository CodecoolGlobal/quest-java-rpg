package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Yeti extends Monster {

    public Yeti(Cell cell) {
        super(cell);
        this.setDamage(5);
        this.setHealth(22);
    }


    @Override
    public String getTileName() {
        return "yeti";
    }

}

