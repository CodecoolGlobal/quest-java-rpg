package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public abstract class Monster extends Actor {

    public Monster(Cell cell) {
        super(cell);
    }

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
            boolean isBoss = this.getCell().getActor().getTileName().equals("boss");
            if (isPassable(dx, dy) && getHealth() > 0 && !isBoss) {
                System.out.println(isBoss);
                Cell nextCell = cell.getNeighbor(dx, dy);
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        } catch (NullPointerException ignored) {

        }
    }
}
