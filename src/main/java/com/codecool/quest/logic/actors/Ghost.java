package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Ghost extends Actor implements Monster {

    public Ghost(Cell cell) {
        super(cell);
        this.setDamage(4);
        this.setHealth(15);
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
        return "ghost";
    }




}

