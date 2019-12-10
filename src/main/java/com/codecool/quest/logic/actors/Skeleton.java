package com.codecool.quest.logic.actors;

import com.codecool.quest.Main;
import com.codecool.quest.logic.Cell;

public class Skeleton extends Actor implements Monster {

    public Skeleton(Cell cell) {
        super(cell);
        this.setDamage(2);


    }

    @Override
    void move(int dx, int dy) {
        try {
            if (isPassable(dx, dy) && this.getHealth() > 0) {
                Cell nextCell = cell.getNeighbor(dx, dy);
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;

            }
        } catch (NullPointerException ignored) {

        }
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }




}
