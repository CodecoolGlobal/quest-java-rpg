package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Ghost extends Actor implements Monster {

    public Ghost(Cell cell) {
        super(cell);
        this.setDamage(4);
        this.setHealth(15);
    }

    @Override
    public String getTileName() {
        return "ghost";
    }


    public void monsterMove(int dx, int dy) {
        try {
            if (isPassable(dx, dy) && getHealth() > 0) {
                Cell nextCell = cell.getNeighbor(dx, dy);
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;

            }
        } catch (NullPointerException ignored) {

        }
    }

    @Override
    public void monsterMoveDirection() {
        int[] actualMove = movementCoordinates.get(rnd.nextInt(movementCoordinates.size()));
        int x = actualMove[0];
        int y = actualMove[1];
        if (getHealth() > 0) monsterMove(x, y);
            /*} catch (InterruptedException e) {
                e.printStackTrace();
            }*/
    }
}

