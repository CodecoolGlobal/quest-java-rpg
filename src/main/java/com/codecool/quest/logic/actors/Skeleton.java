package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        this.setDamage(2);
        this.monsterMove();
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

}
