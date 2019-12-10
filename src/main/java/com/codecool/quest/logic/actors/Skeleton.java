package com.codecool.quest.logic.actors;

import com.codecool.quest.Main;
import com.codecool.quest.logic.Cell;

public class Skeleton extends Actor implements Monster {

    public Skeleton(Cell cell) {
        super(cell);
        this.setDamage(2);


    }


    @Override
    public String getTileName() {
        return "skeleton";
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

    private void monsterMove(int dx, int dy) {
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
}

