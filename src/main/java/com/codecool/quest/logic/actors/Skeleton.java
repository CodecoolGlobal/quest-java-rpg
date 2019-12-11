package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Skeleton extends Monster {

    public Skeleton(Cell cell) {
        super(cell);
        this.setDamage(2);
        this.setHealth(10);
    }


    @Override
    public String getTileName() {
        return "skeleton";
    }

}

