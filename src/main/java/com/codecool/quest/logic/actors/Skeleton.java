package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;

import java.util.*;

public class Skeleton extends Actor {

    public Skeleton(Cell cell) {
        super(cell);
        this.setDamage(2);


    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    public void skeletonMove() {
            this.monsterMove();

    }
}
